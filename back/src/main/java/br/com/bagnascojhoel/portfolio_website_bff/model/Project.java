package br.com.bagnascojhoel.portfolio_website_bff.model;

import java.util.List;

public class Project {
  private final String uniqueName;
  private final String title;
  private final String description;
  private final List<String> tags;
  private final String repositoryUrl;
  private final String websiteUrl;

  public Project(String uniqueName) {
    this.uniqueName = uniqueName;
    this.title = "a new title";
    this.description = "description";
    this.tags = List.of("tags");
    this.repositoryUrl = "repositoryUrl";
    this.websiteUrl = "websiteUrl";

  }

  public Project(String uniqueName, String title, String description, List<String> tags, String repositoryUrl, String websiteUrl) {
    this.uniqueName = uniqueName;
    this.title = title;
    this.description = description;
    this.tags = tags;
    this.repositoryUrl = repositoryUrl;
    this.websiteUrl = websiteUrl;
  }

  public String getUniqueName() {
    return uniqueName;
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
