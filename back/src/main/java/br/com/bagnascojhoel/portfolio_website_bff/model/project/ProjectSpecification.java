package br.com.bagnascojhoel.portfolio_website_bff.model.project;

import br.com.bagnascojhoel.portfolio_website_bff.model.extra_portfolio_description.ExtraPortfolioDescription;
import br.com.bagnascojhoel.portfolio_website_bff.model.github.github_repository.GithubRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor(staticName = "of")
@Builder
@Getter
public class ProjectSpecification {
    @NonNull
    private final ExtraPortfolioDescription extraPortfolioDescription;

    @NonNull
    private final GithubRepository githubRepository;
}
