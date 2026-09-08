package com.jafp.bankapi.security.entity;

import com.jafp.bankapi.client.entity.Client;
import com.jafp.bankapi.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class AppUser extends BaseEntity {
  @Column(name = "username", nullable = false, unique = true)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;
  @Column(name = "enabled", nullable = false)
  private boolean enabled;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  public AppUser() {
  }

  public AppUser(String username, String password, Role role, boolean enabled, Client client) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.enabled = enabled;
    this.client = client;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
