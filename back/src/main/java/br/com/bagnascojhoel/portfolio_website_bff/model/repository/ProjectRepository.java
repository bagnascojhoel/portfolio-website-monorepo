package br.com.bagnascojhoel.portfolio_website_bff.model.repository;

import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubAuthentication;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubQueryParams;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubUriBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ProjectRepository {
  private static final String GITHUB_REPOSITORIES_PATH = "/users/{username}/repos";

  private final RestTemplate restTemplate;
  private final GithubUriBuilder githubUriBuilder;
  private final String githubProjectDescriptionFile;
  private final GithubAuthentication githubAuthentication;
  private final String githubUsername;

  public ProjectRepository(
      RestTemplate restTemplate,
      GithubAuthentication githubAuthentication,
      GithubUriBuilder githubUriBuilder,
      @Value("${project.github.project-description-file}") String githubProjectDescriptionFile,
      @Value("${project.github.username}") String githubUsername) {
    this.restTemplate = restTemplate;
    this.githubUriBuilder = githubUriBuilder;
    this.githubAuthentication = githubAuthentication;
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
    var url = githubUriBuilder.withPath(GITHUB_REPOSITORIES_PATH)
        .queryParam(GithubQueryParams.PER_PAGE, 20)
        .queryParam(GithubQueryParams.SORT, "pushed")
        .queryParam(GithubQueryParams.DIRECTION, "desc")
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
