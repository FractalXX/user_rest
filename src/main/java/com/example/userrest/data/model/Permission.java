package com.example.userrest.data.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.userrest.data.util.PermissionName;

@Entity
@Table(name = "Permissions")
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private PermissionName name;

  @ManyToMany(cascade = { CascadeType.ALL })
  @JoinTable(name = "Permission_Role", joinColumns = { @JoinColumn(name = "permission_id") }, inverseJoinColumns = {
      @JoinColumn(name = "role_id") })
  private Set<Role> roles = new HashSet<>();

  public long getId() {
    return this.id;
  }

  public PermissionName getName() {
    return this.name;
  }

  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}