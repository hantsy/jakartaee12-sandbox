package com.example;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

@WebServlet("/test-servlet")
@ServletSecurity(@HttpConstraint(rolesAllowed = "web"))
public class TestServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String webName = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;

        PrintWriter writer = response.getWriter();
        writer.write("""
                <html>
                <body>
                <h1>This is a servlet </h1>
                <br><br>
                web username: %s<br><br>
                web user has role "web": %s<br>
                web user has role "rest": %s<br>
                </body>
                </html>
                """
                .formatted(
                        webName,
                        request.isUserInRole("web"),
                        request.isUserInRole("rest")
                )
        );
        writer.flush();
    }
}
