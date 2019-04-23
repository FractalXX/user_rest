package com.example.userrest.rest;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.userrest.data.model.User;
import com.example.userrest.rest.util.LoginCredentials;
import com.example.userrest.services.UserService;

@ApplicationScoped
@Path("/auth")
public class AuthenticationResource {

  @Inject
  private UserService service;

  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @PermitAll
  public Response register(User user) {
    this.service.addUser(user);
    return Response.status(Status.OK).build();
  }

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @PermitAll
  public Response login(LoginCredentials credentials) {
    User user = this.service.login(credentials.getUserName(), credentials.getPassword());
    if (user != null) {
      return Response.ok(user).build();
    }

    return Response.status(401).build();
  }
}