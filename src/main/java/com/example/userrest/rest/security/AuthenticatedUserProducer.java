package com.example.userrest.rest.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.example.userrest.data.model.User;
import com.example.userrest.services.UserService;

@RequestScoped
public class AuthenticatedUserProducer {

  @Produces
  @RequestScoped
  @AuthenticatedUser
  private User authenticatedUser;

  @Inject
  private UserService userService;

  public void handleAuthenticationEvent(@Observes @AuthenticatedUser String token) {
    this.authenticatedUser = this.userService.getUserByToken(token);
  }
}