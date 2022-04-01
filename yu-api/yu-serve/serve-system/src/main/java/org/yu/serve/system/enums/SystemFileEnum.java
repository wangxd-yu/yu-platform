package org.yu.serve.system.enums;

import org.yu.serve.file.enums.FilePathEnum;
import org.yu.serve.file.enums.FilePathType;
import org.yu.serve.file.enums.FileSuffix;

/**
 * @author wangxd
 * @date 2022-01-10 22:22
 */
public enum SystemFileEnum implements FilePathEnum {
    /**
     * 用户头像
     */
    USER_FILE("user/",FilePathType.NONE, FileSuffix.PNG);

    String path;
    FilePathType type;
    FileSuffix fileSuffix;

    SystemFileEnum(String path, FilePathType type, FileSuffix fileSuffix) {
        this.path = path;
        this.type = type;
        this.fileSuffix = fileSuffix;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public FileSuffix getSuffix() {
        return fileSuffix;
    }

    @Override
    public FilePathType getType() {
        return type;
    }
}
