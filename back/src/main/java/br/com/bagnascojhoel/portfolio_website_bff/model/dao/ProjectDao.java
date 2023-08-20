package br.com.bagnascojhoel.portfolio_website_bff.model.dao;

import br.com.bagnascojhoel.portfolio_website_bff.model.GithubRepositoryId;
import br.com.bagnascojhoel.portfolio_website_bff.model.ProjectDescription;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.github.GithubAuthentication;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.github.GithubQueryParams;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.github.GithubUriBuilder;
import br.com.bagnascojhoel.portfolio_website_bff.model.exception.UnrecoverableError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Repository
@Slf4j
public class ProjectDao {
    private static final String GITHUB_REPOSITORIES_PATH = "/users/{username}/repos";
    private static final String GITHUB_DESCRIPTION_FILE_PATH = "/repos/{username}/{repoName}/contents/{descriptionFileName}";

    private final RestTemplate restTemplate;
    private final GithubUriBuilder githubUriBuilder;
    private final ObjectMapper objectMapper;
    private final String githubProjectDescriptionFile;
    private final GithubAuthentication githubAuthentication;
    private final String githubUsername;

    public ProjectDao(
            RestTemplate restTemplate,
            GithubAuthentication githubAuthentication,
            GithubUriBuilder githubUriBuilder,
            ObjectMapper objectMapper,
            @Value("${project.github.project-description-file}") String githubProjectDescriptionFile,
            @Value("${project.github.username}") String githubUsername) {
        this.restTemplate = restTemplate;
        this.githubUriBuilder = githubUriBuilder;
        this.githubAuthentication = githubAuthentication;
        this.objectMapper = objectMapper;
        this.githubProjectDescriptionFile = githubProjectDescriptionFile;
        this.githubUsername = githubUsername;
    }

    public ProjectDescription getProjectDescription(String repositoryName) {
        var url = githubUriBuilder.withPath(GITHUB_DESCRIPTION_FILE_PATH)
                .build(githubUsername, repositoryName, githubProjectDescriptionFile);
        var installationToken = githubAuthentication.generateAuthenticationToken();
        var requestEntity = RequestEntity.get(url)
                .header("authorization", "Bearer " + installationToken)
                .build();
        ResponseEntity<GithubRepositoryContentResponse> response = restTemplate.exchange(requestEntity, GithubRepositoryContentResponse.class);
        try {
            var responseBody = Objects.requireNonNull(response.getBody());
            byte[] decodedDescription = Base64.getMimeDecoder().decode(responseBody.content);
            return objectMapper.readValue(decodedDescription, ProjectDescription.class);
        } catch (IOException ioException) {
            log.error("error while reading repository description file");
            throw new UnrecoverableError(ioException);
        }
    }

    public Page<GithubRepositoryId> getGitHubRepositories(Integer limit) {
        var url = githubUriBuilder.withPath(GITHUB_REPOSITORIES_PATH)
                .queryParam(GithubQueryParams.PER_PAGE, limit)
                .queryParam(GithubQueryParams.SORT, "pushed")
                .queryParam(GithubQueryParams.DIRECTION, "desc")
                .build(githubUsername);
        var installationToken = githubAuthentication.generateAuthenticationToken();
        var requestEntity = RequestEntity.get(url)
                .header("authorization", "Bearer " + installationToken)
                .build();

        var response = restTemplate.exchange(requestEntity, GithubRepositoryId[].class);
        var repositoryIds = Arrays.asList(Objects.requireNonNull(response.getBody()));
        return new PageImpl<>(repositoryIds);
    }

    private record GithubRepositoryContentResponse(String content) {
    }
}
