package br.com.bagnascojhoel.portfolio_website_bff.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ProjectsRest {

    private final List<ProjectDto> projectDtos = new ArrayList<>();

    @GetMapping("/projects")
    public List<ProjectDto> getProjects() {
        return projectDtos;
    }

    @PostMapping("/projects")
    public void addProject() {
        projectDtos.add(new ProjectDto(UUID.randomUUID().toString()));
    }

}
