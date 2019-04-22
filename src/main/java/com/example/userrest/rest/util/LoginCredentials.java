package com.example.userrest.rest.util;

import java.io.Serializable;

public class LoginCredentials implements Serializable {
  private String userName;
  private String password;

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}