package br.com.bagnascojhoel.portfolio_website_bff.controller;

import br.com.bagnascojhoel.portfolio_website_bff.model.Project;
import br.com.bagnascojhoel.portfolio_website_bff.model.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ProjectsController {
  private final ProjectRepository projectRepository;

  public Set<Project> getMyProjects() {
    return projectRepository.getProjects();
  }
}
