package com.example;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/greeting")
public class GreetingServlet extends HttpServlet {

    @Inject
    private GreetingService greetingService;

    @Inject
    private EagerBean eagerBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write(greetingService.greet("CDI 5.0") + " (started at " + eagerBean.getStartedAt() + ")");
    }
}
