package org.yu.serve.file.enums;

/**
 * @author wangxd
 * @date 2022-01-05
 */
public enum TempFileEnum implements FilePathEnum {
    TEMP_FILE("temp/", FilePathType.DAY, null);

    String path;
    FilePathType type;
    FileSuffix fileSuffix;

    TempFileEnum(String path, FilePathType type, FileSuffix fileSuffix) {
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
