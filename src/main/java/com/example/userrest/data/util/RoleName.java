package com.example.userrest.data.util;

public enum RoleName {
  DEFAULT("DEFAULT"), ADMIN("ADMIN");

  private String name;

  private RoleName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public static RoleName fromName(String name) {
    for (RoleName roleName : RoleName.values()) {
      if (name.equalsIgnoreCase(roleName.getName())) {
        return roleName;
      }
    }

    throw new IllegalArgumentException("RoleName representation of " + name + " was not found.");
  }

}