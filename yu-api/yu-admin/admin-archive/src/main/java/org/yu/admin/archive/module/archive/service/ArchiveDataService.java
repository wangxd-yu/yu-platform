package org.yu.admin.archive.module.archive.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.yu.common.querydsl.api.MultiDataResult;

import java.util.List;

/**
 * @author wangxd
 * @date 2024-12-21 16:01
 */
public interface ArchiveDataService {

    MultiDataResult<Object> query(String databaseType, Object query, Pageable pageable);

    String uploadZip(String databaseType, MultipartFile multipartFile);

    Object getById(String databaseType, String id);

    void updateData(String databaseType, Object domain);

    void deleteById(String databaseType, String id);

    void create(String databaseType, String domain, MultipartFile wordFile, List<MultipartFile> pdfFiles);

    void updateHtml(String databaseType, String id, String html);
}
