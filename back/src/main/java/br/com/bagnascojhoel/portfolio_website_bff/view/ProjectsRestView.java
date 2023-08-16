package br.com.bagnascojhoel.portfolio_website_bff.view;

import br.com.bagnascojhoel.portfolio_website_bff.controller.ProjectsController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectsRestView {
  private final ProjectsController projectsController;

  @GetMapping("/projects")
  public ProjectsResponseDto getProjects() {
    return new ProjectsResponseDto(projectsController.getMyProjects());
  }

}
