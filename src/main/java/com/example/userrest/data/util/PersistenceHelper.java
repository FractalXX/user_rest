package com.example.userrest.data.util;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class PersistenceHelper {

  @PersistenceContext
  private EntityManager entityManager;

  public EntityManager getEntityManager() {
    return this.entityManager;
  }
}