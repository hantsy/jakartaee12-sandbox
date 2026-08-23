package com.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class SecurityIT {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(MultipleHttpAuthenticationMechanismHandler.class.getPackage())
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsWebInfResource("test-web.xml", "web.xml")
                .addAsResource("META-INF/microprofile-config.properties", "META-INF/microprofile-config.properties")
                .addAsResource("publickey.pem", "publickey.pem")
                .addAsWebResource(new File("src/main/webapp", "login.html"));
    }

    @ArquillianResource
    private URL baseUrl;

    @Test
    @RunAsClient
    public void testServletPathWithoutAuthInfo() throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl.toExternalForm() + "test-servlet"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(302, response.statusCode(), "unauthenticated /test-servlet should redirect to the login page");
    }

    @Test
    @RunAsClient
    public void testRestApiWithoutToken() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl.toExternalForm() + "api/hello"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(401, response.statusCode(), "unauthenticated /api/hello should be unauthorized");
    }
}
