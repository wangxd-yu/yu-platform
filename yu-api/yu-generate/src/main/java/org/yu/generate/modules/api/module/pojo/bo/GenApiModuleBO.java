package org.yu.generate.modules.api.module.pojo.bo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangxd
 * @date 2023-04-18 23:42
 */
@Data
public class GenApiModuleBO {

    private String version;

    private String author;

    private Timestamp datetime;

    private String moduleName;

    private String moduleComment;

    private Long queryId;

    private String packagePath;

}
