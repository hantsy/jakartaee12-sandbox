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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
public class HelloIT {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class,
                new File("target/jakartaee12-faces-examples.war"));
    }

    @ArquillianResource
    private URL baseUrl;

    @Test
    @RunAsClient
    public void testHelloPage() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl.toExternalForm() + "hello.xhtml"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Page should return HTTP 200");
        assertTrue(response.body().contains("Say Hello to Faces 5.0"),
                "Page should render the Faces heading");
    }
}
