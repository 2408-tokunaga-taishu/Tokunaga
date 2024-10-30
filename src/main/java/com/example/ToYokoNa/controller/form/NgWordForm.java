package com.example.ToYokoNa.controller.form;

import com.example.ToYokoNa.Validation.CheckBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NgWordForm {
    private int id;

    @CheckBlank(message = "NGワードを入力してください")
    private String ngWord;
}
