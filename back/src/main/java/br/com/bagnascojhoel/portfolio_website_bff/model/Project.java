package br.com.bagnascojhoel.portfolio_website_bff.model;

import java.util.List;

public class Project {
    private final String projectId;
    private final String title;
    private final String description;
    private final List<String> tags;
    private final String repositoryUrl;
    private final String websiteUrl;

    public Project(String projectId) {
        this.projectId = projectId;
        this.title = "a new title";
        this.description = "description";
        this.tags = List.of("tags");
        this.repositoryUrl = "repositoryUrl";
        this.websiteUrl = "websiteUrl";

    }

    public Project(String projectId, String title, String description, List<String> tags, String repositoryUrl, String websiteUrl) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.repositoryUrl = repositoryUrl;
        this.websiteUrl = websiteUrl;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }
}
