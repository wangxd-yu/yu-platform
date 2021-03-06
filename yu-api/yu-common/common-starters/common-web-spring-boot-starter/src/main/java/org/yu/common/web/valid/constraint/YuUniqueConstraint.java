package org.yu.common.web.valid.constraint;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.util.ReflectionUtils;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.common.querydsl.query.util.ReflectUtils;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.web.util.QueryDslUtil;
import org.yu.common.web.valid.annotation.YuUniqueValid;

import javax.persistence.Id;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangxd
 * @date 2021-10-23 23:29
 */
public class YuUniqueConstraint implements ConstraintValidator<YuUniqueValid, Object> {

    private YuUniqueValid yuUniqueValid;

    @Override
    public void initialize(YuUniqueValid constraintAnnotation) {
        yuUniqueValid = constraintAnnotation;
        //ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        Class domainClass = object.getClass();

        List<Field> idFields = ReflectUtils.getFieldsByAnnotation(domainClass, Id.class);

        EntityPath<?> entityPath = YuQueryHelp.getEntityPath(domainClass);
        //String basePath = getProperties(constraintValidatorContext);

        List<Expression<?>> expressions = new ArrayList<>();
        for (Field idField : idFields) {
            expressions.add(YuQueryHelp.getFieldValue(entityPath, idField.getName()));
        }

        Map<String, Object> fieldMaps = new HashMap<>(2);
        String[] props = yuUniqueValid.props();

        boolean retFlag = false;
        Tuple tuple = QueryDslUtil.getJPAQueryFactory()
                .select(expressions.toArray(new Expression[0]))
                .from(entityPath)
                .where(getPredicateAll(entityPath, object, props, fieldMaps))
                .fetchFirst();
        if (tuple == null) {
            retFlag = true;
        } else {
            //??????????????????tuple????????????????????????
            if (isAdd(idFields, object)) {
                retFlag = false;
            }
            //?????????????????????????????? ???????????????
            else {
                try {
                    for (int i = 0; i < idFields.size(); i++) {
                        boolean accessFlag = true;
                        if (!idFields.get(i).isAccessible()) {
                            accessFlag = false;
                            idFields.get(i).setAccessible(true);
                        }
                        if (!idFields.get(i).get(object).equals(tuple.get(expressions.get(i)))) {
                            retFlag = false;
                            break;
                        }
                        if (!accessFlag) {
                            idFields.get(i).setAccessible(false);
                        }
                    }
                    retFlag = true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!retFlag) {
            //?????????????????????
            String reg = "\\$\\{(.*?)}";
            //?????????????????????
            Pattern patten = Pattern.compile(reg);
            // ???????????????????????????
            Matcher matcher = patten.matcher(constraintValidatorContext.getDefaultConstraintMessageTemplate());

            boolean isMatch = false;
            StringBuffer sb = new StringBuffer();
            //??????find??????????????????????????????????????????????????????
            while (matcher.find()) {
                isMatch = true;
                String key = matcher.group(1);
                //TODO ????????????????????? String
                String value = fieldMaps.get(key).toString();
                matcher.appendReplacement(sb, value == null ? "" : value);
            }
            if (isMatch) {
                matcher.appendTail(sb);
                //???????????????message??????
                constraintValidatorContext.disableDefaultConstraintViolation();
                //??????????????????????????????
                constraintValidatorContext.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();
            }
        }
        return retFlag;
    }


    /**
     * ????????????
     * ????????? null ??????????????????????????????????????? null ????????? ?????????
     *
     * @return true????????????false?????????
     */
    private boolean isAdd(List<Field> idFields, Object object) {
        for (Field idField : idFields) {
            boolean accessFlag = true;
            if (!idField.isAccessible()) {
                accessFlag = false;
                idField.setAccessible(true);
            }
            try {
                if (idField.get(object) == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!accessFlag) {
                idField.setAccessible(false);
            }
        }
        return false;
    }

    public static <C> Predicate getPredicateAll(EntityPath<?> entityPath, Object object, String[] keys, Map<String, Object> fieldMaps) {
        List<Predicate> predicates = new ArrayList<>();
        Collection<Field> fields = ReflectUtils.getDistinctFields(object.getClass());
        AccessibleObject.setAccessible(fields.toArray(new AccessibleObject[0]), true);
        for (Field field : fields) {
            if (Arrays.asList(keys).contains(field.getName())) {
                try {
                    fieldMaps.put(field.getName(), field.get(object));
                    Predicate predicate = YuQueryHelp.getPredicate(entityPath, YuOperatorEnum.EQUAL, null, field.getName(), field.get(object));
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        AccessibleObject.setAccessible(fields.toArray(new AccessibleObject[0]), false);
        if (predicates.isEmpty()) {
            return null;
        } else {
            return ExpressionUtils.allOf(predicates);
        }
    }

    /**
     * ?????????????????????
     *
     * @param constraintValidatorContext
     * @return
     */
    private String getProperties(ConstraintValidatorContext constraintValidatorContext) {
        try {
            final String BASE_PATH_FIELD = "basePath";
            Field field = ReflectionUtils.findField(ConstraintValidatorContextImpl.class,
                    BASE_PATH_FIELD);
            field.setAccessible(true);
            PathImpl path = (PathImpl) field.get(constraintValidatorContext);
            return path.getLeafNode().asString();
        } catch (Exception e) {
            return "";
        }
    }
}
