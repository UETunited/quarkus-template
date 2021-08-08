package com.uu.orm.panache;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Cacheable
public class User extends PanacheEntity {
    @Column(length = 40, unique = true)
    public String name;
}
