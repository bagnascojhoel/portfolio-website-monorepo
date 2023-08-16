package br.com.bagnascojhoel.portfolio_website_bff.code;

import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;

@TestComponent
public record GithubMockServer(
    @Value("${project.github.host}") String githubHost,
    @Value("${project.github.username}") String githubUsername
) {
  private static final String MY_INSTALLATION_ID = "6125";

  public void mockAll(MockServerClient mockServer) {
    mockOkayUserInstallationAccessTokens(mockServer);
    mockOkayUserInstallation(mockServer);
    mockOkayUserRepos(mockServer);
  }

  public void mockOkayUserInstallation(MockServerClient mockServerClient) {
    mockServerClient
        .when(
            request()
                .withMethod("GET")
                .withPath("/users/{username}/installation")
                .withPathParameter(param("username", githubUsername))
        )
        .respond(
            response()
                .withStatusCode(200)
                .withBody(json("""
                    {
                      "id": {installationId}
                    }
                    """.replace("{installationId}", MY_INSTALLATION_ID))
                )
        );

  }

  public void mockOkayUserRepos(MockServerClient mockRestServiceServer) {
    mockRestServiceServer
        .when(
            request()
                .withMethod("GET")
                .withPath("/users/{username}/repos")
                .withPathParameter(param("username", githubUsername))
        )
        .respond(
            response()
                .withStatusCode(200)
                .withBody(json("""
                    [
                        {
                            "name": "repository-1",
                            "html_url": "https://github.com/{username}/repository-1"
                        },
                        {
                            "name": "repository-2",
                            "html_url": "https://github.com/{username}/repository-2"
                        }
                    ]
                    """.replace("{username}", githubUsername))
                )
        );
  }

  public void mockOkayUserInstallationAccessTokens(MockServerClient mockServerClient) {

    var expiresAt = Instant.now().plus(10, ChronoUnit.MINUTES).toString();

    mockServerClient
        .when(
            request()
                .withMethod("POST")
                .withPath("/app/installations/{installationId}/access_tokens")
                .withPathParameter(param("installationId", MY_INSTALLATION_ID))
        ).respond(
            response()
                .withStatusCode(200)
                .withBody(json("""
                    {
                        "token": "my-token",
                        "expires_at": "{expiresAt}"
                    }
                    """.replace("{expiresAt}", expiresAt))
                )
        );
  }


}
