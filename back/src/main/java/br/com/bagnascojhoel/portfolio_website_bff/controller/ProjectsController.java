package br.com.bagnascojhoel.portfolio_website_bff.controller;

import br.com.bagnascojhoel.portfolio_website_bff.model.GithubRepositoryDefinition;
import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.ProjectDescription;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.ProjectDao;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProjectsController {

    private static final int NUMBER_OF_REPOSITORIES_TO_FETCH = 30;

    private final ProjectDao projectDao;

    @Cacheable("projects")
    public Set<Project> getMyProjects() {
        List<GithubRepositoryDefinition> githubRepositories = projectDao.getGithubRepositories(NUMBER_OF_REPOSITORIES_TO_FETCH);
        Flux<ProjectDescription> flux = projectDao.getProjectsDescription(githubRepositories);

        return flux.map(projectDescription -> Project.builder()
                        .uniqueName(projectDescription.getName())
                        .description(projectDescription.getDescription())
                        .repositoryUrl(projectDescription.getHtmlUrl())
                        .tags(projectDescription.getTags())
                        .title(projectDescription.getTitle())
                        .websiteUrl(projectDescription.getWebsiteUrl())
                        .build())
                .collect(Collectors.toSet())
                .block();
    }

}
