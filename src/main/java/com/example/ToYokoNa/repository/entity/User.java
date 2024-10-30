package com.example.ToYokoNa.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="branch_id")
    private Integer branchId;

    @Column(name="department_id")
    private Integer departmentId;

    @Column(name="is_stopped")
    private Integer isStopped;

    @Column(name="created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name="updated_date", insertable = false)
    private Date updatedDate;

    @Column(name="login_time", nullable = true)
    private Date loginTime;

    @Column(name = "message_count")
    private int messageCount;

    @Column(name = "comment_count")
    private int commentCount;

    @OneToMany(mappedBy = "user")
    private List<Message> message;
  
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;
}