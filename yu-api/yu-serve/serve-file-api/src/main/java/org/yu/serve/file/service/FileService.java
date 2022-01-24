package org.yu.serve.file.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import org.yu.serve.file.domain.FileRecordDO;
import org.yu.serve.file.enums.FilePathEnum;
import org.yu.serve.file.query.FileRecordQuery;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangxd
 * @create 2020-04-23 13:24
 */
public interface FileService {
    List<FileRecordDO> queryList(FilePathEnum filePathEnum, Long relationId);

    List<FileRecordDO> queryList(FileRecordQuery query);

    void updateRelationId(FilePathEnum filePathEnum, Long oldRelationId, Long newRelationId);

    /**
     * 【同步】base64字符串转文件并保存到对应目录
     *
     * @param base64       base64字符串（不带前缀）
     * @param filePathEnum 文件夹信息
     * @return 文件路径
     */
    String base64ToFileSave(String base64, FilePathEnum filePathEnum);

    /**
     * 【同步】base64字符串转文件并保存到对应目录
     *
     * @param base64        base64字符串（不带前缀）
     * @param filePathEnum  文件夹信息
     * @param localDateTime 文件时间
     * @return 文件路径
     */
    String base64ToFileSave(String base64, FilePathEnum filePathEnum, LocalDateTime localDateTime);

    /**
     * 【同步】base64字符串转文件并保存到对应目录
     *
     * @param base64       base64字符串（不带前缀）
     * @param filePathEnum 文件夹信息
     * @param fileName     生成的文件名
     * @return 文件路径
     */
    String base64ToFileSave(String base64, FilePathEnum filePathEnum, String fileName);

    /**
     * 【同步】base64字符串转文件并保存到对应目录
     *
     * @param base64        base64字符串（不带前缀）
     * @param filePathEnum  文件夹信息
     * @param fileName      生成的文件名
     * @param localDateTime 文件时间
     * @return 文件路径
     */
    String base64ToFileSave(String base64, FilePathEnum filePathEnum, String fileName, LocalDateTime localDateTime);

    /**
     * 【同步】base64字符串转文件并保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param base64List   base64字符串列表（不带前缀）
     * @param filePathEnum 文件夹信息
     * @param relationUUID 业务表关联字段（使用UUID关联）
     */
    void base64ToFileSaveAndRecord(List<String> base64List, FilePathEnum filePathEnum, String relationUUID);

    /**
     * 【异步】base64字符串转文件并保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param base64List   base64字符串列表（不带前缀）
     * @param filePathEnum 文件夹信息
     * @param relationUUID 业务表关联字段（使用UUID关联）
     */
    @Async
    default void base64ToFileSaveAndRecordAsync(List<String> base64List, FilePathEnum filePathEnum, String relationUUID) {
        base64ToFileSaveAndRecord(base64List, filePathEnum, relationUUID);
    }

    /**
     * 【同步】文件保存到对应目录
     *
     * @param file         文件
     * @param filePathEnum 文件夹信息
     * @return 文件路径
     */
    String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum);

    /**
     * 【同步】文件保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param file         文件
     * @param filePathEnum 文件夹信息
     * @param relationId   业务表关联字段
     */
    String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum, Long relationId);

    /**
     * 【异步】文件保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param file         文件
     * @param filePathEnum 文件夹信息
     * @param relationId   业务表关联字段
     */
    @Async
    default void unifiedSaveAndRecordAsync(MultipartFile file, FilePathEnum filePathEnum, Long relationId) {
        fileSaveAndRecord(file, filePathEnum, relationId);
    }

    /**
     * 【同步】文件保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param file         文件
     * @param filePathEnum 文件夹信息
     * @param relationUUID 业务表临时关联字段（使用UUID关联），业务表保存后需要使用 主键ID 进行更新
     */
    String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum, String relationUUID);

    /**
     * 【异步】文件保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param file         文件
     * @param filePathEnum 文件夹信息
     * @param relationUUID 业务表临时关联字段（使用UUID关联），业务表保存后需要使用 主键ID 进行更新
     */
    @Async
    default String unifiedSaveAndRecordAsync(MultipartFile file, FilePathEnum filePathEnum, String relationUUID) {
        return this.unifiedSaveAndRecordAsync(file, filePathEnum, relationUUID);
    }

    /**
     * 【同步】文件保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param files        文件列表
     * @param filePathEnum 文件夹信息
     * @param relationId   业务表关联字段
     */
    void fileSaveAndRecord(List<MultipartFile> files, FilePathEnum filePathEnum, Long relationId);

    /**
     * 【异步】文件保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param files        文件列表
     * @param filePathEnum 文件夹信息
     * @param relationId   业务表关联字段
     */
    @Async
    default void unifiedSaveAndRecordAsync(List<MultipartFile> files, FilePathEnum filePathEnum, Long relationId) {
        this.fileSaveAndRecord(files, filePathEnum, relationId);
    }

    /**
     * 【同步】文件保存到对应目录，将记录信息保存到 公共文件表
     *
     * @param files        文件列表
     * @param filePathEnum 文件夹信息
     * @param relationUUID 业务表临时关联字段（使用UUID关联），业务表保存后需要使用 主键ID 进行更新
     */
    void fileSaveAndRecord(List<MultipartFile> files, FilePathEnum filePathEnum, String relationUUID);

    /**
     * 生成临时文件，并返回 存储路径
     *
     * @param inputStream
     * @return
     */
    String tempFile(InputStream inputStream);
}
