package com.example.ToYokoNa.Validation;

import com.example.ToYokoNa.controller.form.NgWordForm;
import com.example.ToYokoNa.service.NgWordService;
import org.apache.commons.lang3.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NgWordValidator implements ConstraintValidator<NgWord, String> {

    @Autowired
    NgWordService ngWordService;

    @Override
    public void initialize(NgWord annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //valueには、アノテーションを付与したフィールドの項目値が設定される
        //valueが空白値か、全て全角文字の場合はfalse返すチェック処理を行う
        List<NgWordForm>ngWordForms = ngWordService.findAllNgWords();
        for (NgWordForm ngWordForm : ngWordForms) {
            String match = ".*" + ngWordForm.getNgWord() + ".*";
            if (value.matches(match)) {
                return false;
            }
        }
        return true;
    }
}