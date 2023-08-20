package br.com.bagnascojhoel.portfolio_website_bff.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
public class ProjectDescription {
    private final String title;
    private final String description;
    private final Set<String> tags;
    private final String websiteUrl;
    private final String name;
    private final String htmlUrl;
}
