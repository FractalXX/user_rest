package com.example.userrest.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
  @Produces(MediaType.APPLICATION_JSON)
  public User register(User user) {
    this.service.addUser(user);
    return user;
  }

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(LoginCredentials credentials) {
    User user = this.service.login(credentials.getUserName(), credentials.getPassword());
    if (user != null) {
      return Response.ok(user).build();
    }

    return Response.status(401).build();
  }
}