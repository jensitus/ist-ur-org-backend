package org.ist.ur.org.auth.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.ist.ur.org.posting.model.Posting;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "slug", length = 16, unique = true, nullable = false)
  private UUID slug;

  @Column(name = "username")
  @NotBlank
  private String username;

  @Column(name = "email")
  @NaturalId
  @Email
  private String email;

  @Column(name = "password")
  @NotNull
  @JsonIgnore
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private List<Posting> postings;

//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(name = "followership", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "followed_id"))
  // private List<User> follower;

//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(name = "followership", joinColumns = @JoinColumn(name = "followed_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
  // private List<User> followed;

  protected User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getSlug() {
    return slug;
  }

  public void setSlug(UUID slug) {
    this.slug = slug;
  }

  public List<Posting> getPostings() {
    return postings;
  }

  public void setPostings(List<Posting> postings) {
    this.postings = postings;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
