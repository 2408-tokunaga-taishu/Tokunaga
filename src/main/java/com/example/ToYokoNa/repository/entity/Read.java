package com.example.ToYokoNa.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="read")
@Entity
public class Read {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name= "user_id")
    private int userId;

    @NotNull
    @Column(name= "message_id")
    private int messageId;
}
