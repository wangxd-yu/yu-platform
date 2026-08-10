package org.yu.admin.archive.module.archive.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 胶济铁路档案
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "tb_data_jjtlda")
public class DataJjtldaDO extends BaseArchiveDO implements Serializable {

    @ExcelProperty("資料庫名稱")
    private String databaseName;

    @ExcelProperty("文獻系列名稱*")
    private String seriesName;

    @ExcelProperty("文獻名稱*")
    private String documentName;

    @ExcelProperty("文檔序號")
    private String fileNumber;

    @ExcelProperty("文檔題名*")
    private String title;

    @ExcelProperty("文檔時間*")
    private String fileTime;

    @ExcelProperty("起始文檔序號")
    private String startFileNumber;

    @ExcelProperty("終止文檔序號")
    private String endFileNumber;

    @ExcelProperty("關鍵字*")
    private String keywords;

    @ExcelProperty("人物*")
    private String person;

    @ExcelProperty("地名*")
    private String locationName;

    @ExcelProperty("機構*")
    private String organization;

    @ExcelProperty("備註*")
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