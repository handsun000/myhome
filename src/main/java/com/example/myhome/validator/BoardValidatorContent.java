package com.example.myhome.validator;

import com.example.myhome.model.Board;
import com.example.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidatorContent implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if (StringUtils.isEmpty(b.getContent())) {
            errors.rejectValue("content", "key", "내용을 입력하세요");
        }
    }
}
