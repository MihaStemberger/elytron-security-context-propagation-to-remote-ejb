package com.mihas.security.service.one;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class ElytronServlet extends HttpServlet {

    @Inject
    ElytronCaller elytronCaller;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final PrintWriter writer = resp.getWriter();
        writer.println("HTTP principal: " + getPrincipal(req));
        writer.println("Local EJB principal: " + elytronCaller.getLocalPrincipal());
        writer.println("Remote EJB principal: " + elytronCaller.getRemotePrincipal());
    }

    private String getPrincipal(final HttpServletRequest req) {
        final Principal principal = req.getUserPrincipal();
        return String.format("Name: %s, Type: %s",
                             principal != null ? principal.getName() : null,
                             principal != null ? principal.getClass().getCanonicalName() : null);
    }
}
