package com.example.ToYokoNa.controller.form;

import com.example.ToYokoNa.Validation.CheckBlank;
import com.example.ToYokoNa.Validation.NgWord;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentForm {
    private int id;

    @NgWord(message = "コメント内容にNGワードが含まれています")
    @CheckBlank(message = "メッセージを入力してください")
    @Size(max = 500, message = "500文字以内で入力してください")
    private String text;

    private int userId;

    private int messageId;

    private Date createdDate;

    private Date updatedDate;

}
