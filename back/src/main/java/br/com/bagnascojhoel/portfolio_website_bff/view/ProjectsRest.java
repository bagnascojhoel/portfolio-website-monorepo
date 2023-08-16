package br.com.bagnascojhoel.portfolio_website_bff.view;

import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.ProjectRepository;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.github.GithubAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProjectsRest {

    private final List<Project> projectDtos = new ArrayList<>();
    private final ProjectRepository projectRepository;
    private final GithubAuthentication githubAuthentication;

    @GetMapping("/projects")
    public ProjectsResponseDto getProjects() {
        return new ProjectsResponseDto(projectRepository.getProjects());
    }

    @PostMapping("/projects")
    public void addProject() {
        projectDtos.add(new Project(UUID.randomUUID().toString()));
    }

}
