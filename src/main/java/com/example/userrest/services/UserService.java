package com.example.userrest.services;

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
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }
}