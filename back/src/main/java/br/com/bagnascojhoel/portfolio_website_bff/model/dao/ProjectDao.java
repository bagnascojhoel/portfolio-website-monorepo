package br.com.bagnascojhoel.portfolio_website_bff.model.dao;

import br.com.bagnascojhoel.portfolio_website_bff.model.GithubRepositoryDefinition;
import br.com.bagnascojhoel.portfolio_website_bff.model.ProjectDescription;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.github.GithubAuthentication;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.github.GithubQueryParams;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.github.GithubUriBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Repository
@Slf4j
public class ProjectDao {
    private static final String GITHUB_REPOSITORIES_PATH = "/users/{username}/repos";
    private static final String GITHUB_DESCRIPTION_FILE_PATH = "/repos/{username}/{repoName}/contents/{descriptionFileName}";

    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private final GithubUriBuilder githubUriBuilder;
    private final ObjectMapper objectMapper;
    private final String githubProjectDescriptionFile;
    private final GithubAuthentication githubAuthentication;
    private final String githubUsername;

    public ProjectDao(
            WebClient webClient,
            RestTemplate restTemplate,
            GithubAuthentication githubAuthentication,
            GithubUriBuilder githubUriBuilder,
            ObjectMapper objectMapper,
            @Value("${project.github.project-description-file}") String githubProjectDescriptionFile,
            @Value("${project.github.username}") String githubUsername) {
        this.webClient = webClient;
        this.restTemplate = restTemplate;
        this.githubUriBuilder = githubUriBuilder;
        this.githubAuthentication = githubAuthentication;
        this.objectMapper = objectMapper;
        this.githubProjectDescriptionFile = githubProjectDescriptionFile;
        this.githubUsername = githubUsername;
    }

    public Flux<ProjectDescription> getProjectsDescription(Set<GithubRepositoryDefinition> definitions) {
        return Flux.fromIterable(definitions)
                .flatMap(this::callGetProjectDescription)
                .log()
                .filter(res -> res.content != null)
                .cast(GithubRepositoryContentResponse.class)
                .map(this::mapContentResponse)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    public Page<GithubRepositoryDefinition> getGithubRepositories(Integer limit) {
        var url = githubUriBuilder.withPath(GITHUB_REPOSITORIES_PATH)
                .queryParam(GithubQueryParams.PER_PAGE, limit)
                .queryParam(GithubQueryParams.SORT, "pushed")
                .queryParam(GithubQueryParams.DIRECTION, "desc")
                .build(githubUsername);
        var installationToken = githubAuthentication.generateAuthenticationToken();
        var requestEntity = RequestEntity.get(url)
                .header(AUTHORIZATION, "Bearer " + installationToken)
                .build();

        var response = restTemplate.exchange(requestEntity, GithubRepositoryDefinition[].class);
        var definitions = Arrays.stream(Objects.requireNonNull(response.getBody()))
                .filter(repo -> repo.archived() == null || !repo.archived())
                .collect(Collectors.toList());
        return new PageImpl<>(definitions);
    }

    private Optional<ProjectDescription> mapContentResponse(GithubRepositoryContentResponse contentResponse) {
        try {
            var responseBody = Objects.requireNonNull(contentResponse);
            byte[] decodedDescription = Base64.getMimeDecoder().decode(responseBody.content);
            var partialProjectDescription = objectMapper.readValue(decodedDescription, ProjectDescription.class);
            var finalProjectDescription = partialProjectDescription.toBuilder()
                    .name(contentResponse.name)
                    .htmlUrl(contentResponse.htmlUrl)
                    .build();
            return Optional.of(finalProjectDescription);
        } catch (Exception exception) {
            log.error("error while reading repository description file", exception);
            return Optional.empty();
        }
    }

    private Mono<GithubRepositoryContentResponse> callGetProjectDescription(GithubRepositoryDefinition definition) {
        var url = githubUriBuilder.withPath(GITHUB_DESCRIPTION_FILE_PATH)
                .build(githubUsername, definition.name(), githubProjectDescriptionFile);
        var installationToken = githubAuthentication.generateAuthenticationToken();

        return webClient.get().uri(url)
                .header(AUTHORIZATION, "Bearer " + installationToken)
                .exchangeToMono(response -> response.bodyToMono(GithubRepositoryContentResponse.class))
                .map(responseBody -> {
                    responseBody.setName(definition.name());
                    responseBody.setHtmlUrl(definition.htmlUrl());
                    return responseBody;
                })
                .onErrorResume(WebClientResponseException.class, webClientResponseException -> {
                    if (webClientResponseException.getStatusCode().value() == 404) {
                        log.info(
                                "could not find project description file on repository, url={}",
                                Objects.requireNonNull(webClientResponseException.getRequest()).getURI());
                        return Mono.empty();
                    } else {
                        log.error("error while getting project description", webClientResponseException);
                        return Mono.error(webClientResponseException);
                    }
                });
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
//    @Accessors(chain = true)
    public static class GithubRepositoryContentResponse {
        private String content;
        private String name;
        private String htmlUrl;
    }
}
