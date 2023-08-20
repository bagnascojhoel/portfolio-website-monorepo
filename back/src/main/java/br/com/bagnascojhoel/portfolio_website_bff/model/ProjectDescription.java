package br.com.bagnascojhoel.portfolio_website_bff.model;

import jakarta.annotation.Nullable;

import java.util.Set;

public record ProjectDescription(String title, String description, Set<String> tags, @Nullable String websiteUrl) {
    @Override
    public String title() {
        return title;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Set<String> tags() {
        return tags;
    }

    @Override
    @Nullable
    public String websiteUrl() {
        return websiteUrl;
    }
}
