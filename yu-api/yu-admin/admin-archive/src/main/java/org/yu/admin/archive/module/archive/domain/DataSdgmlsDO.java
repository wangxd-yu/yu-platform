package org.yu.admin.archive.module.archive.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 山东革命历史档案数据库
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "tb_data_sdgmls")
public class DataSdgmlsDO extends BaseArchiveDO implements Serializable {

    @ExcelProperty("数据库名称")
    private String databaseName;

    @ExcelProperty("文献系列名称")
    private String seriesName;

    @ExcelProperty("文献名称")
    private String documentName;

    @ExcelProperty("文件序号")
    private String fileNumber;

    @ExcelProperty("文件时间")
    private String fileTime;

    @ExcelProperty("起始文件序号")
    private String startFileNumber;

    @ExcelProperty("终止文件序号")
    private String endFileNumber;

    @ExcelProperty("关键词")
    private String keywords;

    @ExcelProperty("人物")
    private String person;

    @ExcelProperty("地名")
    private String locationName;

    @ExcelProperty("机构")
    private String organization;

    @ExcelProperty("备注")
    private String notes;

    @ExcelProperty("其他1")
    private String other1;

    @ExcelProperty("其他2")
    private String other2;

    @ExcelProperty("其他3")
    private String other3;

    @ExcelProperty("其他4")
    private String other4;

    @ExcelProperty("其他5")
    private String other5;

    @Transient
    @ExcelProperty("HTML内容")
    private String html;

    @Transient
    @ExcelProperty("HTML无标签")
    private String htmlNoTag;

    @Transient
    @ExcelProperty("Word路径")
    private String word;

    @Transient
    @ExcelProperty("PDFs标签")
    private String pdfs;
}
