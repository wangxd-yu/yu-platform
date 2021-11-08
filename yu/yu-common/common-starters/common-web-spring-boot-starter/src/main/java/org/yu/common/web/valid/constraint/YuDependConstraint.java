package org.yu.common.web.valid.constraint;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.web.util.QueryDslUtil;
import org.yu.common.web.valid.annotation.YuDependValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author wangxd
 * @date 2021-10-31 22:49
 */
public class YuDependConstraint implements ConstraintValidator<YuDependValid, Object> {

    private YuDependValid yuDependValid;

    @Override
    public void initialize(YuDependValid constraintAnnotation) {
        this.yuDependValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(object == null) {
            return true;
        }
        Class<?> domainClass = yuDependValid.domain();
        EntityPath<?> entityPath = YuQueryHelp.getEntityPath(domainClass);
        Predicate predicate = YuQueryHelp.getPredicate(entityPath, YuOperatorEnum.EQUAL, null, yuDependValid.prop(), object);
        long count = QueryDslUtil.getJPAQueryFactory().selectFrom(entityPath)
                .where(predicate)
                .fetchCount();
        return yuDependValid.exist() == count > 0;
    }
}
