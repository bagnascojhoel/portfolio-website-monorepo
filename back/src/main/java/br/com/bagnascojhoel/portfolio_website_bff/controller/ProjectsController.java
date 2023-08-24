package br.com.bagnascojhoel.portfolio_website_bff.controller;

import br.com.bagnascojhoel.portfolio_website_bff.model.GithubRepositoryDefinition;
import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.ProjectDescription;
import br.com.bagnascojhoel.portfolio_website_bff.model.dao.ProjectDao;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProjectsController {
    private final ProjectDao projectDao;

    @Scheduled(initialDelayString = "${project.scheduling.initial-delay.load-projects}",
            fixedDelayString = "${project.scheduling.fixed-delay.load-projects}")
    @Cacheable("projects")
    public Set<Project> getMyProjects() {
        Page<GithubRepositoryDefinition> githubRepositories = projectDao.getGithubRepositories(30);
        Flux<ProjectDescription> flux = projectDao.getProjectsDescription(githubRepositories.toSet());

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

    @Scheduled(fixedDelayString = "${project.scheduling.fixed-delay.evict-projects}")
    @CacheEvict(value = "projects", allEntries = true)
    protected void evictCache() {
    }
}
