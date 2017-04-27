package com.fict.entities;

import javax.persistence.*;

/**
 * Created by stevo on 4/27/17.
 */
@Entity
@Table(name = "person")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
