package com.example.userrest.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.userrest.data.model.User;
import com.example.userrest.rest.security.AuthenticatedUser;
import com.example.userrest.services.UserService;

@ApplicationScoped
@Path("/profile")
public class ProfileResource {

  @Inject
  @AuthenticatedUser
  User requestUser;

  @Inject
  private UserService service;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProfile() {
    return Response.ok(this.requestUser).build();
  }

  @PATCH
  @Consumes(MediaType.APPLICATION_JSON)
  public Response modifyProfile(User data) {
    if (this.service.modifyUser(this.requestUser.getId(), data)) {
      return Response.status(Status.OK).build();
    }
    return Response.status(Status.BAD_REQUEST).build();
  }
}