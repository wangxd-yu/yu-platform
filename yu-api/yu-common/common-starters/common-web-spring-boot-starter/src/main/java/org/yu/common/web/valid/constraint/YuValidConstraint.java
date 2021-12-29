package org.yu.common.web.valid.constraint;

import org.yu.common.web.valid.annotation.YuValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义参数校验类
 * @author wangxd
 * @date 2021-10-21 23:43
 */
public class YuValidConstraint implements ConstraintValidator<YuValid, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
