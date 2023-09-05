package br.com.bagnascojhoel.portfolio_website_bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public final class GithubRepositoryDefinition {
    private String name;
    @JsonProperty("html_url")
    private String htmlUrl;
    private Boolean archived;
}
