package br.com.bagnascojhoel.portfolio_website_bff.view;

import br.com.bagnascojhoel.portfolio_website_bff.model.Project;

import java.util.Set;

public record ProjectsResponseDto(
        Set<Project> projects
) {
}
