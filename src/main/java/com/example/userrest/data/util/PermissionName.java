package com.example.userrest.data.util;

public enum PermissionName {
  LIST_USERS("LIST_USERS"), ADD_USER("ADD_USER"), DELETE_USER("DELETE_USER");

  private String name;

  private PermissionName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public static PermissionName fromName(String name) {
    for (PermissionName permissionName : PermissionName.values()) {
      if (name.equalsIgnoreCase(permissionName.getName())) {
        return permissionName;
      }
    }

    throw new IllegalArgumentException("PermissionName representation of " + name + " was not found.");
  }
}