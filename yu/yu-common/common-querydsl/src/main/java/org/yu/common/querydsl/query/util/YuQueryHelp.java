package org.yu.common.querydsl.query.util;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.yu.common.querydsl.exception.YuQueryException;
import org.yu.common.querydsl.query.annotation.*;
import org.yu.common.querydsl.query.enums.YuDateTimeEnum;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author wangxd
 * @date 2020-10-10
 */
@Slf4j
public class YuQueryHelp {

    private static final Map<String, EntityPath<?>> ENTITY_PATH_MAP = new HashMap<>();

    public static <C, T> JPAQuery<T> getJPAQuery(JPAQueryFactory jpaQueryFactory, C criteria) {
        JPAQuery<T> jpaQuery;
        YuQuery yuQuery = criteria.getClass().getAnnotation(YuQuery.class);
        //主实体类名称（主表）
        @SuppressWarnings("unchecked")
        EntityPath<T> masterDO = (EntityPath<T>) getEntityPath(yuQuery.domain());
        jpaQuery = jpaQueryFactory.selectFrom(masterDO);
        fromAndJoin(jpaQuery, criteria);
        where(jpaQuery, criteria);
        if (yuQuery.orders().length > 0) {
            jpaQuery = setOrder(jpaQuery, yuQuery);
        }
        return jpaQuery;
    }

    public static <T, C> void where(JPAQuery<T> jpaQuery, C criteria) {
        Predicate predicate = null;
        try {
            predicate = getPredicateAll(criteria);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (predicate != null) {
            jpaQuery.where(predicate);
        }
    }

    private static <T> JPAQuery<T> setOrder(JPAQuery<T> jpaQuery, YuQuery yuQuery) {
        OrderSpecifier<?>[] orderPredicates = getOrderPredicate(yuQuery);
        if (orderPredicates.length > 0) {
            jpaQuery = jpaQuery.orderBy(orderPredicates);
        }
        return jpaQuery;
    }

    private static OrderSpecifier<?>[] getOrderPredicate(YuQuery criteria) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>(10);
        EntityPath<?> mainDomain = getEntityPath(criteria.domain());
        EntityPath<?> domain;
        for (YuOrderColumn yuOrderColumn : criteria.orders()) {
            if (yuOrderColumn.domain() == Void.class) {
                domain = mainDomain;
            } else {
                domain = getEntityPath(yuOrderColumn.domain());
            }

            orderSpecifiers.add(ReflectUtils.invoke(getFieldValue(domain, yuOrderColumn.column()), yuOrderColumn.type().name().toLowerCase()));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    /**
     * 返回DSL查询语句
     */
    public static <C, T> JPAQuery<T> getJPAQuery(JPAQueryFactory jpaQueryFactory, C criteria, Class<T> dtoClass) {
        JPAQuery<T> jpaQuery = null;
        try {
            YuQuery yuQuery = criteria.getClass().getAnnotation(YuQuery.class);
            jpaQuery = jpaQueryFactory.select(getJpaDTOSelect(dtoClass));
            fromAndJoin(jpaQuery, criteria);
            Predicate predicate = getPredicateAll(criteria);
            if (predicate != null) {
                jpaQuery = jpaQuery.where(predicate);
            }
            if (yuQuery.orders().length > 0) {
                jpaQuery = setOrder(jpaQuery, yuQuery);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jpaQuery;
    }

    public static <C> void fromAndJoin(JPAQuery<?> jpaQuery, C criteria) {
        try {
            YuQuery yuQuery = criteria.getClass().getAnnotation(YuQuery.class);
            //主实体类名称（主表）
            EntityPath<?> masterDO = getEntityPath(yuQuery.domain());
            jpaQuery.from(masterDO);
            //关联实体类（关联表）
            YuJoin[] yuJoins = yuQuery.joins();
            //存在关联表
            if (yuJoins.length > 0) {
                //TODO ,处理关联操作
                for (YuJoin yuJoin : yuJoins) {
                    EntityPath<?> joinDomain = getEntityPath(yuJoin.domain());
                    ReflectUtils.invoke(jpaQuery, yuJoin.type().getLabel(), joinDomain);
                    List<Predicate> predicates = new ArrayList<>();
                    if (yuJoin.columns().length <= 0) {
                        throw new IllegalArgumentException("关联表中不存在关联字段");
                    }
                    Method columnMethod;
                    Expression<?> columnField;
                    Expression<?> columnParam;
                    for (YuJoinColumn column : yuJoin.columns()) {
                        EntityPath<?> relationDomain;
                        if (column.relationDomain() == Void.class) {
                            relationDomain = masterDO;
                        } else {
                            relationDomain = getEntityPath(column.relationDomain());
                        }
                        Predicate predicate = null;
                        Field field;
                        YuDateTimeEnum dateTimeEnum = null;
                        String fieldName;
                        switch (column.relationType()) {
                            case COLUMN:
                                columnField = getFieldValue(joinDomain, column.column());
                                columnParam = getFieldValue(relationDomain, column.relationColumn());
                                columnMethod = columnField.getClass().getMethod(column.operator().getName(), Expression.class);
                                predicate = (Predicate) columnMethod.invoke(columnField, columnParam);
                                //ReflectUtil.invoke(columnField, columnMethod, columnParam);
                                break;
                            case FIELD:
                                fieldName = "".equals(column.fieldName()) ? column.column() : column.fieldName();
                                field = ReflectionUtils.findField(criteria.getClass(), fieldName);
                                assert field != null;
                                field.setAccessible(true);
                                if (field.isAnnotationPresent(YuDateTimeFormat.class)) {
                                    dateTimeEnum = field.getDeclaredAnnotation(YuDateTimeFormat.class).value();
                                }
                                predicate = getPredicate(joinDomain, column.operator(), dateTimeEnum, column.column(), field.get(criteria));
                                field.setAccessible(false);
                                break;
                            case CONST:
                                predicate = getPredicate(masterDO, column.operator(), null, column.column(), column.constant());
                                break;
                            case RELATION_FIELD:
                                fieldName = "".equals(column.fieldName()) ? column.relationColumn() : column.fieldName();
                                field = ReflectionUtils.findField(criteria.getClass(), fieldName);
                                assert field != null;
                                field.setAccessible(true);
                                if (field.isAnnotationPresent(YuDateTimeFormat.class)) {
                                    dateTimeEnum = field.getDeclaredAnnotation(YuDateTimeFormat.class).value();
                                }
                                predicate = getPredicate(relationDomain, column.operator(), dateTimeEnum, column.relationColumn(), field.get(criteria));
                                field.setAccessible(false);
                                break;
                            case RELATION_CONST:
                                predicate = getPredicate(relationDomain, column.operator(), null, column.relationColumn(), column.constant());
                                break;
                            default:
                                break;
                        }
                        predicates.add(predicate);
                    }
                    jpaQuery.on(ExpressionUtils.allOf(predicates));
                }
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T> Expression<T> getJpaDTOSelect(Class<T> dtoClass) {
        YuDTO yuDto = dtoClass.getAnnotation(YuDTO.class);
        EntityPath<?> masterDO = getEntityPath(yuDto.domain());
        // getFieldMap 去重（子类继承父类后，子类父类都有的字段，使用子类中的字段进行 DSL映射）
        // Field[] fields = getDistinctFields(dtoClass);
        Collection<Field> fields = ReflectUtils.getDistinctFields(dtoClass);
        List<Expression<?>> expressions = new ArrayList<>();
        for (Field field : fields) {
            //1、存在 SSDTOTransient 注解，直接忽略
            if (field.isAnnotationPresent(YuDTOTransient.class) || "serialVersionUID".equals(field.getName())) {
                continue;
            }
            //2、不存在 SSDTOField 注解：默认按字段名映射，domain使用 DSLDTO中的domain
            if (!field.isAnnotationPresent(YuDTOField.class)) {
                expressions.add(getFieldValue(masterDO, field.getName()));
            }
            //3、存在 SSDTOField 注解
            else {
                EntityPath<?> domain;
                YuDTOField SSDTOField = field.getDeclaredAnnotation(YuDTOField.class);
                Class<?> cla = SSDTOField.domain();
                if (cla == Void.class) {
                    domain = masterDO;
                } else {
                    domain = getEntityPath(cla);
                }
                String propName = SSDTOField.propName();
                String template = SSDTOField.template();
                // 包含模板
                if (!StringUtils.isEmpty(template)) {
                    propName = StringUtils.isEmpty(propName) ? field.getName() : propName;
                    if (field.getType().equals(String.class)) {
                        expressions.add(ReflectUtils.invoke(Expressions.stringTemplate(template, getFieldValue(domain, propName)), "as", field.getName()));
                    } else if (Arrays.asList(Integer.class, Long.class).contains(field.getType())) {
                        if (field.getType().equals(Integer.class)) {
                            expressions.add(ReflectUtils.invoke(Expressions.numberTemplate(Integer.class, template, getFieldValue(domain, propName)), "as", field.getName()));
                        }
                    }
                    // TODO 暂不支持 String之外的类型
                } else {
                    if (StringUtils.isEmpty(propName)) {
                        expressions.add(getFieldValue(domain, field.getName()));
                    } else {
                        expressions.add(ReflectUtils.invoke(getFieldValue(domain, propName), "as", field.getName()));
                    }
                }
            }
        }
        return Projections.bean(dtoClass, expressions.toArray(new Expression[0]));
    }

    /**
     * 根据 查询条件对象 返回DSL查询条件
     */
    public static <C> Predicate getPredicateAll(C criteria) throws IllegalAccessException {
        YuQuery yuQuery = criteria.getClass().getAnnotation(YuQuery.class);
        //主实体类名称（主表）
        EntityPath<?> masterDO = getEntityPath(yuQuery.domain());
        List<Predicate> predicates = new ArrayList<>();
        Collection<Field> fields = ReflectUtils.getDistinctFields(criteria.getClass());
        AccessibleObject.setAccessible(fields.toArray(new AccessibleObject[0]), true);
        for (Field field : fields) {
            if (!field.isAnnotationPresent(YuQueryColumn.class)) {
                continue;
            }
            YuQueryColumn q = field.getAnnotation(YuQueryColumn.class);
            EntityPath<?> domain = masterDO;
            if (q.domain() != Void.class) {
                domain = getEntityPath(q.domain());
            }

            Predicate predicate = getPredicateByField(domain, field, criteria);
            if (predicate != null) {
                predicates.add(predicate);
            }
        }
        AccessibleObject.setAccessible(fields.toArray(new AccessibleObject[0]), false);
        if (predicates.isEmpty()) {
            return null;
        } else {
            return ExpressionUtils.allOf(predicates);
        }
    }

    private static <C> Predicate getPredicateByField(EntityPath<?> domain, Field field, C criteria) throws IllegalAccessException {
        YuQueryColumn query = field.getAnnotation(YuQueryColumn.class);
        if (query != null) {
            String propName = query.propName();
            String attributeName = StringUtils.isEmpty(propName) ? field.getName() : propName;
            boolean accessFlag = true;
            if (!field.isAccessible()) {
                accessFlag = false;
                field.setAccessible(true);
            }

            Object val = field.get(criteria);
            if (Objects.isNull(val)) {
                return null;
            } else if (val instanceof String && StringUtils.isEmpty(val)) {
                return null;
            }
            if (!accessFlag) {
                field.setAccessible(false);
            }
            YuDateTimeEnum dateTimeEnum = null;
            if (field.isAnnotationPresent(YuDateTimeFormat.class)) {
                dateTimeEnum = field.getDeclaredAnnotation(YuDateTimeFormat.class).value();
            }
            return getPredicate(domain, query.operator(), dateTimeEnum, attributeName, val);
        }
        return null;
    }

    private static <T> Predicate getPredicate(EntityPath<T> domain, YuOperatorEnum operatorEnum, YuDateTimeEnum dateTimeEnum, String attributeName, Object val) {
        if (val == null) {
            return null;
        }
        Predicate predicate;
        // in 操作符时为 列表，特殊处理
        if (operatorEnum.equals(YuOperatorEnum.IN)) {
            if (val instanceof Iterable) {
                predicate = ReflectUtils.invoke(getFieldValue(domain, attributeName), operatorEnum.getName(), val);
            } else {
                throw new YuQueryException("类型异常！");
            }
        }
        //日期类型时也需要特殊处理，调用 mysql的 DATE_FORMAT 函数
        else if (dateTimeEnum != null) {
            if (val instanceof LocalDate) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeEnum.getFormat());
                predicate = ReflectUtils.invoke(Expressions.stringTemplate(dateTimeEnum.getTemplate(), getFieldValue(domain, attributeName)), operatorEnum.getName(), ((LocalDate) val).format(formatter));
            } else if (val instanceof LocalDateTime) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeEnum.getFormat());
                predicate = ReflectUtils.invoke(Expressions.stringTemplate(dateTimeEnum.getTemplate(), getFieldValue(domain, attributeName)), operatorEnum.getName(), ((LocalDateTime) val).format(formatter));
            } else {
                throw new YuQueryException("日期类型只支持：'LocalDate'、'LocalDateTime'");
            }
        } else {
            if (operatorEnum.getFormat() != null) {
                val = MessageFormat.format(operatorEnum.getFormat(), val);
            }
            predicate = ReflectUtils.invoke(getFieldValue(domain, attributeName), operatorEnum.getName(), val);
        }
        return predicate;
    }

    private static Expression<?> getFieldValue(EntityPath<?> domain, String fieldName) {
        if (!fieldName.contains(".")) {
            return (Expression<?>) ReflectUtils.getFieldValue(domain, fieldName);
        }
        // 指定分割字符， . 号需要转义
        String[] fields = fieldName.split("\\.");
        Expression<?> result = domain;
        for (String field : fields) {
            result = (Expression<?>) ReflectUtils.getFieldValue(result, field);
        }
        return result;
    }

    /**
     * 获取 queryDSL生成的 实体类对象
     */
    private static EntityPath<?> getEntityPath(Class<?> cla) {
        //从 静态变量中获取 EntityPath
        /*//TODO 系统运行一段时间后，map中的 Class对象可能会被 回收，若确认会回收此处就 不能使用 map，直接 Class.forName获取
        EntityPath entityPath = null;
        try {
            String qClassName = cla.getName().replace(cla.getSimpleName(), "Q" + cla.getSimpleName());
            Class qCla = Class.forName(qClassName);
            entityPath = (EntityPath) qCla.getField(StrUtil.lowerFirst(cla.getSimpleName())).get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entityPath;*/

        EntityPath<?> entityPath = ENTITY_PATH_MAP.get(cla.getName());
        try {
            if (entityPath == null) {
                String qClassName = cla.getName().replace(cla.getSimpleName(), "Q" + cla.getSimpleName());
                Class<?> qCla = Class.forName(qClassName);
                entityPath = (EntityPath<?>) qCla.getField(StringUtils.uncapitalize(cla.getSimpleName())).get(null);
                ENTITY_PATH_MAP.put(cla.getName(), entityPath);
            }
            return entityPath;
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
          /*//不是 QueryDSL生成的类，抛出异常 TODO 重新定义一个异常类型
            if(!cla.getSimpleName().startsWith("Q")) {
                throw new BadRequestException("参数类错误！");
            }*/
    }
}
