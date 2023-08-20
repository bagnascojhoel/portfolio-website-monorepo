package br.com.bagnascojhoel.portfolio_website_bff.controller;

import br.com.bagnascojhoel.portfolio_website_bff.model.GithubRepositoryId;
import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.ProjectDescription;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.ProjectDao;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ProjectsController {
    private final ProjectDao projectDao;

    @Cacheable("projects")
    public Set<Project> getMyProjects() {
        Page<GithubRepositoryId> githubRepositories = projectDao.getGitHubRepositories(30);

        Set<Project> projectsWithDescription = new HashSet<>();
        for (var repo : githubRepositories) {
            ProjectDescription projectDescription;
            try {
                projectDescription = projectDao.getProjectDescription(repo.name());
            } catch (HttpClientErrorException httpClientErrorException) {
                if (HttpStatus.NOT_FOUND.equals(httpClientErrorException.getStatusCode())) {
                    continue;
                } else {
                    throw httpClientErrorException;
                }
            }
            projectsWithDescription.add(Project.builder()
                    .uniqueName(repo.name())
                    .description(projectDescription.description())
                    .repositoryUrl(repo.htmlUrl())
                    .tags(projectDescription.tags())
                    .title(projectDescription.title())
                    .websiteUrl(projectDescription.websiteUrl())
                    .build());
        }

        return projectsWithDescription;
    }
}
