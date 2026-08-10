package org.yu.admin.archive.module.archive.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.MappedSuperclass;

/**
 * @author wangxd
 * @date 2024-12-23 10:26
 */
@Getter
@Setter
@MappedSuperclass
public class BaseArchiveDO extends DslBaseDO<String> {

    @ExcelProperty("文件题名*")
    private String title;

    @ExcelProperty("文件序号")
    private String fileNumber;

    private String html;

    private String htmlNoTag;

    private String word;

    private String pdfs;

}
