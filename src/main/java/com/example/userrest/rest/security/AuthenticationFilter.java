package com.example.userrest.rest.security;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.example.userrest.data.model.User;
import com.example.userrest.data.util.PermissionName;
import com.example.userrest.services.UserService;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

  @Inject
  private UserService userService;

  @Inject
  @AuthenticatedUser
  private Event<String> userAuthenticatedEvent;

  @Context
  private ResourceInfo resourceInfo;

  private static final String AUTHORIZATION_PROPERTY = "Authorization";
  private static final String AUTHORIZATION_SCHEME = "Bearer";

  @Override
  public void filter(ContainerRequestContext requestContext) {
    Method method = this.resourceInfo.getResourceMethod();

    // If @PermitAll is present, there is no need for validation
    if (!method.isAnnotationPresent(PermitAll.class)) {

      // If @DenyAll is present, abort
      if (method.isAnnotationPresent(DenyAll.class)) {
        requestContext
            .abortWith(Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users.").build());

        return;
      }

      // Get request headers
      final MultivaluedMap<String, String> headers = requestContext.getHeaders();

      // Get authorization header
      final List<String> authorizationHeader = headers.get(AUTHORIZATION_PROPERTY);

      // Check if authorization header exists
      if (authorizationHeader == null || authorizationHeader.isEmpty()) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource: No authorization header present.").build());

        return;
      }

      // Extract token from headers and find the user associated with it
      final String token = authorizationHeader.get(0).replaceFirst(AUTHORIZATION_SCHEME + " ", "");
      User user = this.validateToken(token);

      // If there's no match, abort
      if (user == null) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource: Token is invalid.").build());
        return;
      }

      // Check if method needs any specific permissions
      if (method.isAnnotationPresent(PermissionsAllowed.class)) {

        // Get annotation and permissions
        PermissionsAllowed permissionsAnnotation = method.getAnnotation(PermissionsAllowed.class);
        Set<PermissionName> permissions = new HashSet<>(Arrays.asList(permissionsAnnotation.value()));

        // If there are no specified permissions, anyone with successful authentication
        // can proceed
        if (!permissions.isEmpty()) {
          // Assume that the user has no permission
          boolean allowed = false;
          for (PermissionName permission : permissions) {
            // Check if the user has a role that has appropriate permissions
            if (user.getRoles().stream()
                .anyMatch((x) -> x.getPermissions().stream().anyMatch((p) -> p.getName() == permission))) {
              allowed = true;
              break;
            }
          }

          // Abort if the user does not have permission
          if (!allowed) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("You cannot access this resource: You do not have permission.").build());
            return;
          }
        }
      }

      // If authentication succeeds, fire the authenticated event
      this.userAuthenticatedEvent.fire(token);
    }
  }

  /**
   * Gets a User associated with a given token.
   * 
   * @param token The token to search for.
   * @return A User instance if the token is valid, null otherwise.
   */
  private User validateToken(String token) {
    return this.userService.getUserByToken(token);
  }
}