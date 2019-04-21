package com.example.userrest.data.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.userrest.data.util.RoleName;

@Entity
@Table(name = "Roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private RoleName name;

  @ManyToMany(mappedBy = "roles")
  private Set<Permission> permissions = new HashSet<>();

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public RoleName getName() {
    return this.name;
  }

  public void setName(RoleName name) {
    this.name = name;
  }

  public Set<Permission> getPermissions() {
    return this.permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }
}