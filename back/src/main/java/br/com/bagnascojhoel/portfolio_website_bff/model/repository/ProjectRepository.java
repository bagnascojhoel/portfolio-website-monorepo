package br.com.bagnascojhoel.portfolio_website_bff.model.repository;

import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubAuthentication;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ProjectRepository {
    private static final UriBuilder GITHUB_REPOSITORIES_PATH = UriComponentsBuilder.fromPath("/users/{username}/repos");

    private final RestTemplate restTemplate;
    private final String githubProjectDescriptionFile;
    private final GithubAuthentication githubAuthentication;
    private final String githubUsername;
    private final String githubHost;

    public ProjectRepository(
            RestTemplate restTemplate,
            GithubAuthentication githubAuthentication,
            @Value("${project.github.host}") String githubHost,
            @Value("${project.github.project-description-file}") String githubProjectDescriptionFile,
            @Value("${project.github.username}") String githubUsername) {
        this.restTemplate = restTemplate;
        this.githubAuthentication = githubAuthentication;
        this.githubHost = githubHost;
        this.githubProjectDescriptionFile = githubProjectDescriptionFile;
        this.githubUsername = githubUsername;
    }

    public Set<Project> getProjects() {
        List<GithubRepositoryDto> githubRepositories = this.getGitHubRepositories();

        return githubRepositories.stream()
                .map(gh -> new Project(gh.name))
                .collect(Collectors.toSet());
    }

    private List<GithubRepositoryDto> getGitHubRepositories() {
        var url = GITHUB_REPOSITORIES_PATH
                .scheme("https")
                .host(githubHost)
                .queryParam("per_page", 20)
                .queryParam("sort", "pushed")
                .queryParam("direction", "desc")
                .build(githubUsername);
        var installationToken = githubAuthentication.generateAuthenticationToken();
        var requestEntity = RequestEntity.get(url)
                .header("authorization", "Bearer " + installationToken)
                .build();

        var response = restTemplate.exchange(requestEntity, GithubRepositoryDto[].class);
        return Arrays.asList(response.getBody());
    }

    private record GithubRepositoryDto(String name, @JsonProperty("html_url") String htmlUrl) {
    }
}
