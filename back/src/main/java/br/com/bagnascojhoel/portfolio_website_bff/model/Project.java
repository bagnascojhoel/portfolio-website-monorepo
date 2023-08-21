package br.com.bagnascojhoel.portfolio_website_bff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
public class Project {
    private final String uniqueName;
    private final String title;
    private final String description;
    private final Set<String> tags;
    private final String repositoryUrl;
    private final String websiteUrl;
}
