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
            //新增时，如果tuple不为空，直接报错
            if (isAdd(idFields, object)) {
                retFlag = false;
            }
            //更新时，需要判断是否 为当前数据
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
            //定义正则表达式
            String reg = "\\$\\{(.*?)}";
            //编译正则表达式
            Pattern patten = Pattern.compile(reg);
            // 指定要匹配的字符串
            Matcher matcher = patten.matcher(constraintValidatorContext.getDefaultConstraintMessageTemplate());

            boolean isMatch = false;
            StringBuffer sb = new StringBuffer();
            //此处find（）每次被调用后，会偏移到下一个匹配
            while (matcher.find()) {
                isMatch = true;
                String key = matcher.group(1);
                //TODO 默认都可以转为 String
                String value = fieldMaps.get(key).toString();
                matcher.appendReplacement(sb, value == null ? "" : value);
            }
            if (isMatch) {
                matcher.appendTail(sb);
                //禁用默认的message的值
                constraintValidatorContext.disableDefaultConstraintViolation();
                //重新添加错误提示语句
                constraintValidatorContext.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();
            }
        }
        return retFlag;
    }


    /**
     * 是否新增
     * 主键为 null 则为新增（多个主键有一个为 null 则判为 新增）
     *
     * @return true：新增；false：更新
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
     * 获取验证的字段
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
