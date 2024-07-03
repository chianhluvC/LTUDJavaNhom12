package com.team12.DASpring.Validator;

import com.team12.DASpring.Validator.annotation.ValidUserId;
import com.team12.DASpring.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context){
        if(user==null)
            return true;
        return user.getId() != null;
    }

}
