package com.example.interfaces.rest;

import com.example.application.RegisterUserUseCase;
import com.example.domain.model.RoleType;
import com.example.domain.model.UserAccount;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("register")
public class RegisterResource {

    @Inject
    private RegisterUserUseCase registerUserUseCase;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest request) {
        RoleType roleType = request.role() == null || request.role().isBlank() ? RoleType.REST : RoleType.valueOf(request.role().toUpperCase());
        UserAccount user = registerUserUseCase.register(request.username(), request.password(), roleType);
        return Response.status(Response.Status.CREATED)
                .entity(new RegisterResponse(user.getUsername(), user.getRole().getRoleName()))
                .build();
    }
}
