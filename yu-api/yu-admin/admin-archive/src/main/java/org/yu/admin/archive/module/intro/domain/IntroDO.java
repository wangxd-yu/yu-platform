package org.yu.admin.archive.module.intro.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wangxd
 * @date 2024-12-21 11:17
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "tb_intro")
public class IntroDO extends DslBaseDO<String> implements Serializable {
    /**
     * 数据库ID
     */
    private String databaseId;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 资料来源
     */
    private String materialSource;

    /**
     * 出版者
     */
    private String publisher;

    /**
     * 出版时间
     */
    private String publicationTime;

    /**
     * 出版地点
     */
    private String publicationLocation;

    /**
     * 资料描述
     */
    private String materialDescription;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 资料类型
     */
    private String materialType;

    /**
     * 资料时间范围
     */
    private String materialTimeframe;

    /**
     * 作者
     */
    private String author;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 其他字段
     */
    private String other1;
    private String other2;
    private String other3;
}
