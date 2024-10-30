package com.example.ToYokoNa.controller.form;

import com.example.ToYokoNa.Validation.CheckBlank;
import com.example.ToYokoNa.Validation.NgWord;
import com.example.ToYokoNa.repository.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageForm {
    private int id;
    @NgWord(message = "タイトルにNGワードが含まれています")
    @CheckBlank(message = "件名を入力してください")
    @Size(max = 30, message = "件名は30文字以内で入力してください")
    private String title;

    @NgWord(message = "本文にNGワードが含まれています")
    @CheckBlank(message = "本文を入力してください")
    @Size(max = 1000, message = "本文は1000文字以内で入力してください")
    private String text;

    @NgWord(message = "カテゴリにNGワードが含まれています")
    @CheckBlank(message = "カテゴリを入力してください")
    @Size(max = 10, message = "カテゴリは10文字以内で入力してください")
    private String category;

    private  int userId;

    private Date createdDate;

    private Date updatedDate;

    private UserForm user;
}
