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

  public Project(String uniqueName) {
    this.uniqueName = uniqueName;
    this.title = "a new title";
    this.description = "description";
    this.tags = Set.of("tags");
    this.repositoryUrl = "repositoryUrl";
    this.websiteUrl = "websiteUrl";

  }
}
