package org.ist.ur.org.user;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  protected User() {
  }

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

}
