package com.example.ToYokoNa.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserMessageForm {

    private int id;

    private String title;

    private String text;

    private String category;

    private int userId;

    private Date createdDate;

    private String userName;

    private Integer departmentId;

    private Integer branchId;

    private String durationTime;

}
