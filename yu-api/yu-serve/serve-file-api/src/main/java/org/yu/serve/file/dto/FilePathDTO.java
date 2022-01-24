package org.yu.serve.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangxd
 * @date 2022-01-05
 */
@Getter
@Setter
@AllArgsConstructor
public class FilePathDTO {
    /**
     * 绝对路径
     */
    private String absPath;
    /**
     * 相对路径
     */
    private String staticPath;
}
