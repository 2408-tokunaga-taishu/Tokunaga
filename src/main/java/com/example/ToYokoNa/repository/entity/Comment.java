package com.example.ToYokoNa.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="text")
    private String text;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "message_id")
    private int messageId;

    @Column(name = "created_date",insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_Id", insertable = false, updatable = false)
    private User user;
}
