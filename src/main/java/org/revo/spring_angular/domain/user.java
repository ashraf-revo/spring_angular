package org.revo.spring_angular.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.hibernate.validator.constraints.Range;

/**
 * Created by ashraf on 6/3/15.
 */
@Entity
public class user implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Email
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 60)
    @JsonIgnore
    private String password;
    @Column(length = 50)
    private String imagePath;

    @Column(length = 1)
    @Range(max = 2, min = -1)
    private int role = -1;

    public user() {
    }

    public Long getId() {
        return id;
    }

    public user setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public user setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public user setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public user setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public user setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public user setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public List<String> getRoles() {

        if (role == 0) {
            return Arrays.asList("ROLE_USER");
        }
        if (role == 1) {
            return Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        }
        return Arrays.asList();
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
