package com.mihas.security.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;


public class ServletTest {

    public static final String TOKEN_ENDPOINT_URL =
            "http://localhost:8180/realms/elitron/protocol/openid-connect/token";
    public static final String KEYCLOAK_ADAPTER_WILDFLY_SERVLET_LOCATION = "http://localhost:8081/service-one/";
    public static final String OIDC_WILDFLY_SERVLET_LOCATION = "http://localhost:8080/service-one/";

    @Test
    public void test_keycloak_adapter_principal() {
        final Form loginForm = new Form()
                .param("username", "johndoe")
                .param("password", "johndoe")
                .param("client_id", "elitron")
                .param("grant_type", "password");
        final KeycloakResponse keycloakResponse = ClientBuilder.newClient()
                .target(TOKEN_ENDPOINT_URL)
                .request("application/x-www-form-urlencoded")
                .post(Entity.form(loginForm))
                .readEntity(KeycloakResponse.class);


        final Response response = ClientBuilder.newClient()
                .target(KEYCLOAK_ADAPTER_WILDFLY_SERVLET_LOCATION)
                .request()
                .header("Authorization", "Bearer " + keycloakResponse.getAccessToken())
                .get();

        final String responseBody = response.readEntity(String.class);

        System.out.printf("HTTP Status: %s\n%s\n",
                          response.getStatus(),
                          responseBody);

        assertFalse(responseBody.toLowerCase().contains("anonymous"));
    }

    @Test
    public void test_OIDC_principal() {
        final Form loginForm = new Form()
                .param("username", "johndoe")
                .param("password", "johndoe")
                .param("client_id", "elitron")
                .param("grant_type", "password");
        final KeycloakResponse keycloakResponse = ClientBuilder.newClient()
                .target(TOKEN_ENDPOINT_URL)
                .request("application/x-www-form-urlencoded")
                .post(Entity.form(loginForm))
                .readEntity(KeycloakResponse.class);


        final Response response = ClientBuilder.newClient()
                .target(OIDC_WILDFLY_SERVLET_LOCATION)
                .request()
                .header("Authorization", "Bearer " + keycloakResponse.getAccessToken())
                .get();

        final String responseBody = response.readEntity(String.class);

        System.out.printf("HTTP Status: %s\n%s\n",
                          response.getStatus(),
                          responseBody);

        assertFalse(responseBody.toLowerCase().contains("anonymous"));
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class KeycloakResponse {

        public KeycloakResponse() {
        }

        public KeycloakResponse(final String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        @JsonProperty("access_token")
        private String accessToken;

    }

}
