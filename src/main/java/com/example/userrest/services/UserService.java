package com.example.userrest.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.example.userrest.data.model.User;
import com.example.userrest.data.util.PersistenceHelper;

@ApplicationScoped
@Transactional
public class UserService {
  
  @Inject
  private PersistenceHelper helper;

  public User[] getAllUsers() {
    return this.helper.getEntityManager().createNamedQuery("User.findAll", User.class).getResultList()
        .toArray(new User[0]);
  }

  public User getUserById(long id) {
    return this.helper.getEntityManager().find(User.class, id);
  }

  public User addUser(String userName, String password, String email) {
    User user = new User();
    user.setUserName(userName);
    user.setPassword(password);
    user.setEmail(email);

    this.helper.getEntityManager().persist(user);
    return user;
  }
}