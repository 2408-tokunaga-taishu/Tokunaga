package com.example.ToYokoNa.Validation;

import com.example.ToYokoNa.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(Unique annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //valueには、アノテーションを付与したフィールドの項目値が設定される
        //value = account　のこと
        //Repositoryでaccountを引数にDBを検索し、データが見つからなければ一意とする
        if (userRepository.findByAccount(value) == null) {
            return true;
        }
        return false;
    }
}
