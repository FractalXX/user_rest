package com.example.userrest.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.example.userrest.data.model.User;
import com.example.userrest.data.util.PermissionName;
import com.example.userrest.rest.security.PermissionsAllowed;
import com.example.userrest.services.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/users")
public class UserResource {

  @Inject
  private UserService service;

  @GET
  @Produces("application/json")
  @PermissionsAllowed({ PermissionName.LIST_USERS })
  public User[] getAll() {
    return this.service.getAllUsers();
  }

  @GET
  @Path("{id}")
  @Produces("application/json")
  @PermissionsAllowed({ PermissionName.LIST_USERS })
  public User getUser(@PathParam("id") long id) {
    return this.service.getUserById(id);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @PermissionsAllowed({ PermissionName.ADD_USER })
  public Response addUser(User user) {
    this.service.addUser(user);
    return Response.ok().build();
  }

  @DELETE
  @Path("{id}")
  @PermissionsAllowed({ PermissionName.DELETE_USER })
  public Response deleteUser(@PathParam("id") long id) {
    this.service.removeById(id);
    return Response.ok().build();
  }
}
