package br.com.bagnascojhoel.portfolio_website_bff.integration;

import br.com.bagnascojhoel.portfolio_website_bff.PortfolioWebsiteBffApplication;
import br.com.bagnascojhoel.portfolio_website_bff.code.GithubMockServer;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.get;

@SpringBootTest(
    classes = {PortfolioWebsiteBffApplication.class, GithubMockServer.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"project.github.host=localhost", "project.github.scheme=http", "project.github.port=7777"}
)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = 7777)
public class ProjectsIntegrationTest {

  @LocalServerPort
  String port;
  @Autowired
  GithubMockServer githubMockServer;
  @Value("${project.github.username}")
  String githubUsername;

  @BeforeEach
  void beforeEach(MockServerClient mockServerClient) {
    githubMockServer.mockAll(mockServerClient);
    RestAssured.baseURI = "http://localhost:" + port + "/api";
  }

  @Test
  void getProjectsHappyPath() throws JSONException {
    var responseBody = get("/projects")
        .then()
        .assertThat()
        .extract()
        .body()
        .asString();

    JSONAssert.assertEquals("""
                {
                    "projects": [
                        {
                            "uniqueName": "repository-1",
                            "title": "Portfolio Website",
                            "description": "This is my portfolio website.",
                            "tags": ["frontend", "backend", "java", "svelte"],
                            "repositoryUrl": "https://github.com/{username}/repository-1",
                            "websiteUrl": "https://my-website.com"
                        },
                        {
                            "uniqueName": "repository-2",
                            "title": "A Project",
                            "description": "Checkout-less ecommerce.",
                            "tags": ["backend", "docker"],
                            "repositoryUrl": "https://github.com/{username}/repository-2"
                        }
                    ]
                }
            """.replace("{username}", githubUsername),
        responseBody,
        false
    );
  }

}
