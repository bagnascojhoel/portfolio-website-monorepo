package br.com.bagnascojhoel.portfolio_website_bff.model.repository;

import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.exception.UnrecoverableError;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubAuthentication;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubQueryParams;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubUriBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Repository
@Slf4j
public class ProjectRepository {
  private static final String GITHUB_REPOSITORIES_PATH = "/users/{username}/repos";
  private static final String GITHUB_DESCRIPTION_FILE_PATH = "/repos/{username}/{repoName}/contents/{descriptionFileName}";

  private final RestTemplate restTemplate;
  private final GithubUriBuilder githubUriBuilder;
  private final ObjectMapper objectMapper;

  private final String githubProjectDescriptionFile;
  private final GithubAuthentication githubAuthentication;
  private final String githubUsername;

  public ProjectRepository(
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

  public Set<Project> getProjects() {
    List<GithubRepositoryResponse> githubRepositories = this.getGitHubRepositories();

    Set<Project> projectsWithDescription = new HashSet<>();
    githubRepositories.forEach(repo -> {
      GithubDescriptionDto projectDescription = getProjectDescription(repo.name);
      projectsWithDescription.add(Project.builder()
          .uniqueName(repo.name)
          .description(projectDescription.description)
          .repositoryUrl(repo.htmlUrl)
          .tags(projectDescription.tags)
          .title(projectDescription.title)
          .websiteUrl(projectDescription.websiteUrl)
          .build());
    });

    return projectsWithDescription;
  }

  private GithubDescriptionDto getProjectDescription(String repositoryName) {
    var url = githubUriBuilder.withPath(GITHUB_DESCRIPTION_FILE_PATH)
        .build(githubUsername, repositoryName, githubProjectDescriptionFile);
    var installationToken = githubAuthentication.generateAuthenticationToken();
    var requestEntity = RequestEntity.get(url)
        .header("authorization", "Bearer " + installationToken)
        .build();
    ResponseEntity<GithubRepositoryContentResponse> response = restTemplate.exchange(requestEntity, GithubRepositoryContentResponse.class);
    byte[] decodedDescription = Base64.getDecoder().decode(Objects.requireNonNull(response.getBody().content));
    try {
      return objectMapper.readValue(decodedDescription, GithubDescriptionDto.class);
    } catch (IOException ioException) {
      log.error("error while reading repository description file");
      throw new UnrecoverableError(ioException);
    }
  }

  private List<GithubRepositoryResponse> getGitHubRepositories() {
    var url = githubUriBuilder.withPath(GITHUB_REPOSITORIES_PATH)
        .queryParam(GithubQueryParams.PER_PAGE, 20)
        .queryParam(GithubQueryParams.SORT, "pushed")
        .queryParam(GithubQueryParams.DIRECTION, "desc")
        .build(githubUsername);
    var installationToken = githubAuthentication.generateAuthenticationToken();
    var requestEntity = RequestEntity.get(url)
        .header("authorization", "Bearer " + installationToken)
        .build();

    var response = restTemplate.exchange(requestEntity, GithubRepositoryResponse[].class);
    return Arrays.asList(Objects.requireNonNull(response.getBody()));
  }

  private record GithubRepositoryResponse(String name, @JsonProperty("html_url") String htmlUrl) {
  }

  private record GithubRepositoryContentResponse(String content) {
  }

  private record GithubDescriptionDto(String title, String description, Set<String> tags, @Nullable String websiteUrl) {
  }
}
