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
@Table(name = "tb_intro_snapshot")
public class IntroSnapshotDO extends DslBaseDO<String> implements Serializable {

    /**
     * 数据库ID
     */
    private String databaseId;

    /**
     * 数据库简介
     */
    private String snapshot;

}
