package com.example.userrest.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
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

  public void removeById(long id) {
    this.helper.getEntityManager().remove(this.helper.getEntityManager().find(User.class, id));
  }

  public void addUser(User user) {
    this.helper.getEntityManager().persist(user);
  }

  public User login(String userName, String password) {
    TypedQuery<User> query = this.helper.getEntityManager().createNamedQuery("User.findByNameAndPassword", User.class);
    query.setParameter("userName", userName);
    query.setParameter("password", password);

    try {
      User user = query.getSingleResult();
      user.setToken(this.generateToken());
      this.helper.getEntityManager().merge(user);
      return user;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean modifyUser(long id, User data) {
    User user = this.helper.getEntityManager().find(User.class, id);
    try {
      user.setEmail(data.getEmail());
      user.setPassword(data.getPassword());
      user.setRoles(data.getRoles());

      this.helper.getEntityManager().merge(user);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public User getUserByToken(String token) {
    TypedQuery<User> query = this.helper.getEntityManager().createNamedQuery("User.findByToken", User.class);
    query.setParameter("token", token);

    try {
      return query.getSingleResult();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String generateToken() {
    Random random = new SecureRandom();
    return new BigInteger(130, random).toString(32);
  }
}