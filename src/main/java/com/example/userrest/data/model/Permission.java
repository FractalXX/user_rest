package com.example.userrest.data.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.userrest.data.util.PermissionName;

@Entity
@Table(name = "Permissions")
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private PermissionName name;

  public long getId() {
    return this.id;
  }

  public PermissionName getName() {
    return this.name;
  }
}