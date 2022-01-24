package org.yu.serve.file.query;

import lombok.Data;

import java.util.List;

@Data
public class FileRecordQuery {

    private Long id;

    private List<String> relationTypeList;

    private Long relationId;

    private Boolean delFlag;
}
