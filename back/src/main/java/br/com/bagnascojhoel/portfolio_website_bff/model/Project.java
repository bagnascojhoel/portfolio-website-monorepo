package br.com.bagnascojhoel.portfolio_website_bff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Data
public class Project {
    private String uniqueName;
    private String title;
    private String description;
    private Set<String> tags;
    private String repositoryUrl;
    private String websiteUrl;
}
