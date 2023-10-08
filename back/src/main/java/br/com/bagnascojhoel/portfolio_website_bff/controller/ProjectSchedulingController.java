package br.com.bagnascojhoel.portfolio_website_bff.controller;

import br.com.bagnascojhoel.portfolio_website_bff.model.scheduling.SchedulingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProjectSchedulingController {

    private final ProjectsController projectsController;
    private final SchedulingManager schedulingManager;

    @Scheduled(initialDelayString = "${project.scheduling.initial-delay.load-projects}",
            fixedDelayString = "${project.scheduling.fixed-delay.load-projects}")
    public void setupProjectCache() {
        if (schedulingManager.canSetupProjectCache()) {
            this.projectsController.getMyProjects();
        }
    }


    @Scheduled(fixedDelayString = "${project.scheduling.fixed-delay.evict-projects}")
    @CacheEvict(value = "projects", allEntries = true)
    protected void evictCache() {
    }
}
