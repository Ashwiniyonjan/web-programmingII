package com.example.dto;

/**
 * UserDTO (Data Transfer Object) — a plain data container passed between
 * the Service layer and the DAO (database) layer.
 *
 * Just like EmployeeDTO, this keeps validation concerns (which live in User.java)
 * separate from the raw data the database needs.
 */
public class UserDTO {

    private Long   id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    public UserDTO() {}

    /** Used when creating a NEW user (id is not yet known). */
    public UserDTO(String firstName, String lastName, String email, String password, String phone) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
        this.phone     = phone;
    }

    /** Used when reading an EXISTING user from the database. */
    public UserDTO(Long id, String firstName, String lastName, String email, String password, String phone) {
        this.id        = id;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
        this.phone     = phone;
    }

    // Getters and Setters

    public Long   getId()        { return id; }
    public void   setId(Long id) { this.id = id; }

    public String getFirstName()              { return firstName; }
    public void   setFirstName(String v)      { this.firstName = v; }

    public String getLastName()               { return lastName; }
    public void   setLastName(String v)       { this.lastName = v; }

    public String getEmail()                  { return email; }
    public void   setEmail(String v)          { this.email = v; }

    public String getPassword()               { return password; }
    public void   setPassword(String v)       { this.password = v; }

    public String getPhone()                  { return phone; }
    public void   setPhone(String v)          { this.phone = v; }

    @Override
    public String toString() {
        return "UserDTO{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
               "', email='" + email + "', phone='" + phone + "'}";
    }
}
