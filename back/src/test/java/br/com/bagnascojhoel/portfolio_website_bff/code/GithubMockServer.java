package br.com.bagnascojhoel.portfolio_website_bff.code;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.MediaType;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;

public final class GithubMockServer {
    private static final String MY_INSTALLATION_ID = "6125";

    private final String githubUsername;

    private final String githubFileName;

    private final MockServerClient mockServerClient;

    public GithubMockServer(
            String githubUsername,
            String githubFileName,
            MockServerClient mockServerClient
    ) {
        this.githubUsername = githubUsername;
        this.githubFileName = githubFileName;
        this.mockServerClient = mockServerClient;
    }

    public void mockOkayUserInstallation() {
        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/users/{username}/installation")
                                .withPathParameter(param("username", githubUsername))
                )
                .withId("user_installation-200")
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.JSON_UTF_8)
                                .withBody(json("""
                                        {
                                          "id": {installationId}
                                        }
                                        """.replace("{installationId}", MY_INSTALLATION_ID))
                                )
                );

    }

    public void mockOkayUserRepos() {
        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/users/{username}/repos")
                                .withPathParameter(param("username", githubUsername))
                )
                .withId("user_repos_200")
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.JSON_UTF_8)
                                .withBody(json("""
                                        [
                                            {
                                                "name": "repository-1",
                                                "html_url": "https://github.com/{username}/repository-1",
                                                "archived": false,
                                                "topics": [],
                                                "description": null,
                                                "pushed_at": "2023-10-12T10:10:00Z"
                                            },
                                            {
                                                "name": "repository-2",
                                                "html_url": "https://github.com/{username}/repository-2",
                                                "archived": false,
                                                "topics": ["java"],
                                                "description": "This is my default description",
                                                "pushed_at": "2023-10-10T11:11:59Z"
                                            },
                                            {
                                                "name": "repository-3",
                                                "html_url": "https://github.com/{username}/repository-3",
                                                "archived": true,
                                                "topics": [],
                                                "description": "",
                                                "pushed_at": "2023-10-21T02:28:06Z"
                                            }
                                        ]
                                        """.replace("{username}", githubUsername))
                                )
                );
    }

    public void mockOkayUserInstallationAccessTokens() {

        var expiresAt = Instant.now().plus(10, ChronoUnit.MINUTES).toString();

        mockServerClient
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/app/installations/{installationId}/access_tokens")
                                .withPathParameter(param("installationId", MY_INSTALLATION_ID))
                )
                .withId("installation_access_token-200")
                .respond(
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


    public void mockOkayProjectDescriptionFileRepository1() {
        var encodedContent = Base64.getEncoder().encodeToString("""
                {
                  "title": "Portfolio Website",
                  "tags": ["frontend", "backend", "java", "svelte"],
                  "description": "This is my portfolio website.",
                  "websiteUrl": "https://my-website.com",
                  "complexity": "HIGH",
                  "startsOpen": true
                }""".getBytes(StandardCharsets.UTF_8));

        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/repos/{username}/{repoName}/contents/{fileName}")
                        .withPathParameters(
                                param("username", githubUsername),
                                param("repoName", "repository-1"),
                                param("fileName", githubFileName)
                        )
                )
                .withId("project_description_file_repository_1-200")
                .respond(response()
                        .withStatusCode(200)
                        .withDelay(Delay.delay(TimeUnit.SECONDS, 1))
                        .withBody(json("""
                                    {
                                      "content": "{encodedContent}",
                                    }
                                """.replace("{encodedContent}", encodedContent))
                        )
                );
    }

    public void mockOkayProjectDescriptionFileRepository2() {
        var encodedContent = Base64.getEncoder().encodeToString("""
                {
                  "title": "A Project",
                  "tags": ["backend",  "docker"]
                }
                """.getBytes(StandardCharsets.UTF_8));

        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/repos/{username}/{repoName}/contents/{fileName}")
                        .withPathParameters(
                                param("username", githubUsername),
                                param("repoName", "repository-2"),
                                param("fileName", githubFileName)
                        )
                )
                .withId("project_description_file_repository_2-200")
                .respond(response()
                        .withDelay(Delay.delay(TimeUnit.SECONDS, 1))
                        .withStatusCode(200)
                        .withBody(json("""
                                    {
                                      "content": "{encodedContent}",
                                    }
                                """.replace("{encodedContent}", encodedContent))
                        )
                );
    }

    public void mockNotFoundProjectDescriptionFileRepository2() {
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/repos/{username}/{repoName}/contents/{fileName}")
                        .withPathParameters(
                                param("username", githubUsername),
                                param("repoName", "repository-2"),
                                param("fileName", githubFileName)
                        )
                )
                .withId("project_description_file_repository_2-404")
                .respond(response()
                        .withDelay(Delay.delay(TimeUnit.SECONDS, 1))
                        .withStatusCode(404)
                        .withBody(json("""
                                    {
                                      "message": "Not Found",
                                    }
                                """
                        ))
                );
    }
}
