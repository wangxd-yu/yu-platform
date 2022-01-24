package org.yu.serve.file.util;

import org.springframework.stereotype.Component;
import org.yu.common.core.context.YuContextHolder;
import org.yu.serve.file.config.FilePathConfig;
import org.yu.serve.file.config.FilePathModelConfig;
import org.yu.serve.file.dto.FilePathDTO;
import org.yu.serve.file.enums.FilePathEnum;
import org.yu.serve.file.enums.FilePathType;
import org.yu.serve.file.enums.FileSuffix;
import org.yu.serve.file.enums.TempFileEnum;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author wangxd
 * @date 2022-01-05 18:21
 */
@Component
public class FilePathUtil {
    private String staticAccessPath;
    private String uploadFolder;
    private String uploadPath;

    private static FilePathUtil filePathUtil;
    private final FilePathConfig filePathConfig;

    public FilePathUtil(FilePathConfig filePathConfig) {
        this.filePathConfig = filePathConfig;
    }

    @PostConstruct
    private void init() {
        filePathUtil = this;
        staticAccessPath = filePathConfig.getStaticAccessPath();
        uploadPath = filePathConfig.getUploadPath();
        uploadFolder = filePathConfig.getUploadFolder();

        //创建上传文件根目录
        File tmpFile = new File(uploadPath);
        if (!tmpFile.exists()) {
            boolean wasSuccessful = tmpFile.mkdirs();
            if (!wasSuccessful) {
                throw new RuntimeException("文件目录创建失败，请检查jar包所在目录是否有写入权限！");
            }
        }
    }

    public static String getStaticAccessPath() {
        return filePathUtil.staticAccessPath;
    }

    public static String getUploadFolder() {
        return filePathUtil.uploadFolder;
    }

    public static String getUploadPath() {
        return filePathUtil.uploadPath;
    }

    public static String getModelPath(FilePathEnum filePathEnum) {
        FilePathModelConfig filePathModelConfig = filePathUtil.filePathConfig.getPathMap() != null ?
                filePathUtil.filePathConfig.getPathMap().get(filePathEnum.getName()) : null;
        if (filePathModelConfig != null) {
            return filePathModelConfig.getPath();
        } else {
            return filePathEnum.getPath();
        }
    }

    public static String getTimePath(FilePathType filePathType, LocalDateTime localDateTime) {
        FilePathModelConfig filePathModelConfig = filePathUtil.filePathConfig.getPathMap() != null ?
                filePathUtil.filePathConfig.getPathMap().get(filePathType.name()) : null;
        String pattern;
        if (filePathModelConfig != null) {
            pattern = filePathType.getPattern(filePathModelConfig.getType()).getPattern();
        } else {
            pattern = filePathType.getPattern();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(localDateTime);
    }

    public static File getAbsFile(FilePathEnum filePathEnum, String fileName, LocalDateTime localDateTime) {
        String absFolder = getAbsFolderPath(filePathEnum, localDateTime);
        return new File(absFolder + fileName.toString());
    }

    public static String getAbsFolderPath(FilePathEnum filePathEnum, LocalDateTime localDateTime) {
        localDateTime = localDateTime == null ? LocalDateTime.now() : localDateTime;
        StringBuilder absFolder = new StringBuilder();
        absFolder.append(getUploadPath())
                .append(getTenantPath())
                .append(getModelPath(filePathEnum))
                .append(getTimePath(filePathEnum.getType(), localDateTime));
        //创建上传文件根目录
        File tmpFile = new File(absFolder.toString());
        if (!tmpFile.exists()) {
            boolean wasSuccessful = tmpFile.mkdirs();
            if (!wasSuccessful) {
                throw new RuntimeException("文件目录创建失败，请检查jar包所在目录是否有写入权限！");
            }
        }
        return absFolder.toString();
    }

    public static String getAbsFilePath(FilePathEnum filePathEnum, String fileName, LocalDateTime localDateTime) {
        return getAbsFolderPath(filePathEnum, localDateTime) + fileName;
    }

    public static String getTenantPath() {
        String tenantPath = "";
        if (YuContextHolder.getTenantId() != null) {
            tenantPath = YuContextHolder.getTenantId() + "/";
        }
        return tenantPath;
    }

    public static String getStaticFolderPath(FilePathEnum filePathEnum, LocalDateTime localDateTime) {
        localDateTime = localDateTime == null ? LocalDateTime.now() : localDateTime;

        return getStaticAccessPath().substring(1).replace("**", "") +
                getTenantPath() +
                getModelPath(filePathEnum) +
                getTimePath(filePathEnum.getType(), localDateTime);
    }

    public static String getStaticFilePath(FilePathEnum filePathEnum, String fileName, LocalDateTime localDateTime) {
        return getStaticFolderPath(filePathEnum, localDateTime) + fileName;
    }

    public static FilePathDTO getTempFilePath(String fileName) {
        String absFilePath = getAbsFilePath(TempFileEnum.TEMP_FILE, fileName, null);
        String staticFilePath = getStaticFilePath(TempFileEnum.TEMP_FILE, fileName, null);
        FilePathDTO filePathDTO = new FilePathDTO(absFilePath, staticFilePath);
        return filePathDTO;
    }

    public static FilePathDTO getTempFilePath(FileSuffix fileSuffix) {
        return getTempFilePath(UUID.randomUUID().toString() + fileSuffix.getSuffix());
    }

    public static File getFileByStaticPath(String path) {
        /**
         * File.separator在windows中为"\\"
         */
        int idx = path.indexOf("/");
        if (idx < 0) {
            return null;
        }
        // 文件上传目录 + 文件子目录和文件名
        return new File(getUploadPath() + path.substring(idx + 1));
    }
}
