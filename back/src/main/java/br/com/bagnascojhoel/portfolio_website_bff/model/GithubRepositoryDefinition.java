package br.com.bagnascojhoel.portfolio_website_bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRepositoryDefinition(String name, @JsonProperty("html_url") String htmlUrl) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public String htmlUrl() {
        return htmlUrl;
    }
}
