package com.example.ToYokoNa.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCommentForm {
    private int id;

    private String text;

    private int userId;

    private int messageId;

    private String userName;

    private String userAccount;

    private Date createdDate;

}
