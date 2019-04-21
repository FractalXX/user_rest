package com.example.userrest.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.example.userrest.data.model.User;
import com.example.userrest.services.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Transactional
@Path("/users")
public class UserResource {

	@Inject
	private UserService service;

	@GET
	@Produces("application/json")
	public User[] getAll() {
		return this.service.getAllUsers();
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public User getOne(@PathParam("id") long id) {
		return this.service.getUserById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User register(User user) {
		return this.service.addUser(user.getUserName(), user.getPassword(), user.getEmail());
	}
}
