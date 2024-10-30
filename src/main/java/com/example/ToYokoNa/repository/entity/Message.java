package com.example.ToYokoNa.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.ToYokoNa.repository.entity.User;

@Entity
@Table(name="messages")
@Getter
@Setter
public class Message implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String category;

    @Column(name="user_id")
    private  int userId;

    @Column(name = "created_date",insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false, updatable = false)
    private User user;



}
