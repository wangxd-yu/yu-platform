package org.yu.serve.file.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.yu.serve.file.domain.FileRecordDO;
import org.yu.serve.file.enums.FilePathEnum;
import org.yu.serve.file.service.FileService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangxd
 * @date 2022-01-05
 */
@Component
public class FileUtil {
    private static FileUtil fileUtil;
    private final FileService fileService;

    public FileUtil(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        fileUtil = this;
    }

    public static List<FileRecordDO> queryList(FilePathEnum filePathEnum, Long relationId) {
        return fileUtil.fileService.queryList(filePathEnum, relationId);
    }

    public static String base64ToFileSave(String base64, FilePathEnum filePathEnum) {
        if (StrUtil.isBlank(base64)) {
            return null;
        } else {
            return fileUtil.fileService.base64ToFileSave(base64, filePathEnum);
        }
    }

    public static String base64ToFileSave(String base64, FilePathEnum filePathEnum, LocalDateTime localDateTime) {
        if (StrUtil.isBlank(base64)) {
            return null;
        } else {
            return fileUtil.fileService.base64ToFileSave(base64, filePathEnum, localDateTime);
        }
    }

    public static String base64ToFileSave(String base64, FilePathEnum filePathEnum, String fileName) {
        if (StrUtil.isBlank(base64)) {
            return null;
        } else {
            return fileUtil.fileService.base64ToFileSave(base64, filePathEnum, fileName);
        }
    }

    public static String base64ToFileSave(String base64, FilePathEnum filePathEnum, String fileName, LocalDateTime localDateTime) {
        if (StrUtil.isBlank(base64)) {
            return null;
        } else {
            return fileUtil.fileService.base64ToFileSave(base64, filePathEnum, fileName, localDateTime);
        }
    }

    public static String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum) {
        return fileUtil.fileService.fileSaveAndRecord(file, filePathEnum);
    }

    public static String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum, Long relationId) {
        return fileUtil.fileService.fileSaveAndRecord(file, filePathEnum, relationId);
    }
}