package br.com.bagnascojhoel.portfolio_website_bff.model.dao.github;

import br.com.bagnascojhoel.portfolio_website_bff.model.exception.UnrecoverableError;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
@Slf4j
public class GithubAuthentication {
    private static final String MY_APP_INSTALLATION_PATH = "/users/{username}/installation";
    private static final String INSTALLATION_ACCESS_TOKEN_PATH = "/app/installations/{installationId}/access_tokens";

    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final GithubUriBuilder githubUriBuilder;
    private final String privateKeyResourcePath;
    private final String githubAppId;
    private final String githubUsername;

    private String myInstallationId;

    public GithubAuthentication(
            RestTemplate restTemplate,
            ResourceLoader resourceLoader,
            GithubUriBuilder githubUriBuilder,
            @Value("${project.github.app-id}") String githubAppId,
            @Value("${project.github.username}") String username,
            @Value("${project.github.private-key-resource-path}") String privateKeyResourcePath) {
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
        this.githubUriBuilder = githubUriBuilder;
        this.githubAppId = githubAppId;
        this.githubUsername = username;
        this.privateKeyResourcePath = privateKeyResourcePath;
    }

    public String generateAuthenticationToken() {
        loadMyInstallationId();
        var url = githubUriBuilder.withPath(INSTALLATION_ACCESS_TOKEN_PATH).build(this.myInstallationId);
        var requestEntity = RequestEntity.post(url)
                .header("authorization", "Bearer " + generateJwt())
                .accept(GithubMediaTypes.GITHUB_JSON)
                .build();
        var response = restTemplate.exchange(requestEntity, InstallationAccessToken.class);
        var accessToken = Objects.requireNonNull(response.getBody());
        return accessToken.token;
    }

    private void loadMyInstallationId() {
        if (myInstallationId != null) {
            return;
        }

        var url = githubUriBuilder.withPath(MY_APP_INSTALLATION_PATH).build(githubUsername);
        RequestEntity<Void> requestEntity = RequestEntity.get(url)
                .header("authorization", "Bearer " + generateJwt())
                .accept(GithubMediaTypes.GITHUB_JSON)
                .build();
        var response = restTemplate.exchange(requestEntity, UserInstallation.class);
        var responseBody = Objects.requireNonNull(response.getBody());
        this.myInstallationId = responseBody.id;
    }

    private String generateJwt() {
        return JWT.create()
                .withIssuer(githubAppId)
                .withIssuedAt(Instant.now().minusSeconds(30))
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.MINUTES))
                .sign(Algorithm.RSA256(this.getRSA256PrivateKey()));
    }

    private RSAPrivateKey getRSA256PrivateKey() {
        var keyFactory = getRSAFactory();
        var privateKeySpecContent = getPrivateKeyFileContent();
        var keySpec = new PKCS8EncodedKeySpec(privateKeySpecContent);

        RSAPrivateKey privateKey;
        try {
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            log.error("key specification could not be used to generate github's private key");
            throw new UnrecoverableError(invalidKeySpecException);
        }
        return privateKey;
    }

    private byte[] getPrivateKeyFileContent() {
        try {
            return resourceLoader.getResource(privateKeyResourcePath).getContentAsByteArray();
        } catch (IOException ioException) {
            log.error("could not read github private key", ioException);
            throw new UnrecoverableError(ioException);
        }

    }

    private KeyFactory getRSAFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            log.error("could not get key factory instance for GitHub token");
            throw new UnrecoverableError(noSuchAlgorithmException);
        }
    }

    private record UserInstallation(String id) {
    }

    private record InstallationAccessToken(String token, @JsonProperty("expires_at") String expiresAt) {
    }
}