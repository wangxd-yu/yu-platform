package org.yu.common.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * @author wangxd
 * @date 2020-11-09
 * MappedSuperclass 用在父类上面。当这个类肯定是父类时，加此标注。如果改成@Entity，则继承后，多个类继承，只会生成一个表，而不是多个继承，生成多个表
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class JpaBaseDO {
    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "yu_id", strategy = "com.yu.common.jpa.util.YuIdGenerator")
    @GeneratedValue(generator = "yu_id")
    private Long id;
    /**
     * 创建时间
     */
    private LocalDate createTime;
    /**
     * 更新时间
     */
    private LocalDate updateTime;
    /**
     * 部门no
     */
    private String deptNo;
    /**
     * 租户id
     */
    private Integer tenantId;
}
