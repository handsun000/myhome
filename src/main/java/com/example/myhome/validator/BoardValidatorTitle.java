package com.example.myhome.validator;

import com.example.myhome.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidatorTitle implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if (StringUtils.isEmpty(b.getTitle()) || b.getTitle().length() < 2 || b.getTitle().length() > 30) {
            errors.rejectValue("title", "key", "제목은 2자이상 30자 이하입니다.");
        }
    }
}
