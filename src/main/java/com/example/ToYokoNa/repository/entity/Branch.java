package com.example.ToYokoNa.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class Branch {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name="updated_date", insertable = false)
    private Date updatedDate;

    @Column(name = "message_count")
    private int messageCount;

    @Column(name = "comment_count")
    private int commentCount;

    @OneToMany(mappedBy = "branch")
    private List<User> user;
}