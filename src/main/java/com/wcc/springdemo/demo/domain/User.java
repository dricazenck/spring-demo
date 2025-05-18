package com.wcc.springdemo.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "users")
@Schema(description = "User entity representing a system user")
public class User {

  @Id
  @Schema(description = "Unique identifier for the user", example = "usr-123")
  private String id;

  @NotBlank(message = "Username is required")
  @Schema(
      description = "Username for login",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "mary.jane")
  private String username;

  @NotBlank(message = "First name is required")
  @Schema(
      description = "User's first name",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "Mary")
  private String firstName;

  @NotBlank(message = "Lastname is required")
  @Schema(
      description = "User's last name",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "Jane")
  private String lastName;

  @JsonIgnore
  @Transient
  @Schema(description = "User's full name (not stored in database)", hidden = true)
  private String fullName;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email is required")
  @Schema(
      description = "User's email address",
      requiredMode = Schema.RequiredMode.REQUIRED,
      example = "mary.jane@example.com",
      format = "email")
  private String email;

  // Default constructor required by JPA
  public User() {}

  public User(
      String id,
      String username,
      String firstName,
      String lastName,
      String fullName,
      String email) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = fullName;
    this.email = email;
  }

  @JsonProperty("userId")
  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(username, user.username)
        && Objects.equals(firstName, user.firstName)
        && Objects.equals(lastName, user.lastName)
        && Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, firstName, lastName, email);
  }

  @Override
  public String toString() {
    return "User{"
        + "id='"
        + id
        + '\''
        + ", username='"
        + username
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }
}
