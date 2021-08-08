package com.uu.user;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Cacheable
@Data
public class User extends PanacheEntity {
    @Column(name = "full_name" )
    private String fullName;
    @Column(name = "username", length = 30, unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "organization_id")
    private String organizationId;
    @Column(name = "profile_picture")
    private String profilePicture;
}
