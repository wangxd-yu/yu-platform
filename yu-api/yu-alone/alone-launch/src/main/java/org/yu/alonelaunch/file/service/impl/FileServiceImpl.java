package org.yu.alonelaunch.file.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.MD5;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yu.alonelaunch.file.repository.FileRecordRepository;
import org.yu.common.core.exception.ServiceException;
import org.yu.common.core.util.SecurityUtil;
import org.yu.serve.file.domain.FileRecordDO;
import org.yu.serve.file.domain.QFileRecordDO;
import org.yu.serve.file.enums.FilePathEnum;
import org.yu.serve.file.query.FileRecordQuery;
import org.yu.serve.file.service.FileService;
import org.yu.serve.file.util.FilePathUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxd
 * @create 2020-04-23 14:25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileServiceImpl implements FileService {
    @PersistenceContext
    private EntityManager entityManager;
    private final QFileRecordDO qFileRecordDO = QFileRecordDO.fileRecordDO;

    private final FileRecordRepository fileRecordRepository;

    public FileServiceImpl(FileRecordRepository fileRecordRepository) {
        this.fileRecordRepository = fileRecordRepository;
    }

    @Override
    public List<FileRecordDO> queryList(FilePathEnum filePathEnum, Long relationId) {
        QFileRecordDO qFileRecordDO = QFileRecordDO.fileRecordDO;
        return (List<FileRecordDO>) fileRecordRepository.findAll(
                qFileRecordDO.relationType.eq(filePathEnum.getName())
                        .and(qFileRecordDO.relationId.eq(relationId)));
    }

    @Override
    public List<FileRecordDO> queryList(FileRecordQuery query) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        BooleanBuilder builder = new BooleanBuilder();
        if (query.getRelationId() != null) {
            builder.and(qFileRecordDO.relationId.eq(query.getRelationId()));
        }
        if (query.getRelationTypeList() != null) {
            builder.and(qFileRecordDO.relationType.in(query.getRelationTypeList()));
        }
        return jpaQueryFactory
                .selectFrom(qFileRecordDO)
                .where(builder)
                .fetch();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRelationId(FilePathEnum filePathEnum, Long oldRelationId, Long newRelationId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory.update(qFileRecordDO)
                .set(qFileRecordDO.relationId, newRelationId)
                .where(
                        qFileRecordDO.relationType.eq(filePathEnum.getName()),
                        qFileRecordDO.relationId.eq(oldRelationId)
                ).execute();
    }

    @Override
    public String base64ToFileSave(String base64, FilePathEnum filePathEnum) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return base64ToFileSave(base64, filePathEnum, localDateTime);
    }

    @Override
    public String base64ToFileSave(String base64, FilePathEnum filePathEnum, LocalDateTime localDateTime) {
        String fileName = MD5.create().digestHex(base64) + filePathEnum.getSuffix().getSuffix();
        //Base64.decodeToFile(base64, FilePathUtil2.getAbsFile(filePathEnum, fileName, localDateTime));
        asyncBase64ToFileSave(base64, filePathEnum, localDateTime, fileName);
        return FilePathUtil.getStaticFilePath(filePathEnum, fileName, localDateTime);
    }

    @Async
    public void asyncBase64ToFileSave(String base64, FilePathEnum filePathEnum, LocalDateTime localDateTime, String fileName) {
        Base64.decodeToFile(base64, FilePathUtil.getAbsFile(filePathEnum, fileName, localDateTime));
    }

    @Override
    public String base64ToFileSave(String base64, FilePathEnum filePathEnum, String fileName) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return base64ToFileSave(base64, filePathEnum, fileName, localDateTime);
    }

    @Override
    public String base64ToFileSave(String base64, FilePathEnum filePathEnum, String fileName, LocalDateTime localDateTime) {
        Base64.decodeToFile(base64, FilePathUtil.getAbsFile(filePathEnum, fileName, localDateTime));
        return FilePathUtil.getStaticFilePath(filePathEnum, fileName, localDateTime);
    }

    @Override
    public void base64ToFileSaveAndRecord(List<String> base64List, FilePathEnum filePathEnum, String relationUUID) {

    }

    @Override
    public String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum) {
        return uploadFile(file, filePathEnum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum, Long relationId) {
        String path = uploadFile(file, filePathEnum);
        this.saveFileRecord(file, filePathEnum.getName(), path, relationId);
        return path;
    }

    @Override
    public String fileSaveAndRecord(MultipartFile file, FilePathEnum filePathEnum, String relationUUID) {
        return null;
    }

    @Override
    public void fileSaveAndRecord(List<MultipartFile> files, FilePathEnum filePathEnum, Long relationId) {

    }

    @Override
    public void fileSaveAndRecord(List<MultipartFile> files, FilePathEnum filePathEnum, String relationUUID) {

    }

    @Override
    public String tempFile(InputStream inputStream) {
        return null;
    }

    /**
     * 上传文件到服务器目录
     *
     * @param file         文件
     * @param filePathEnum 上传路径信息
     * @return 文件映射地址（外部访问|数据库记录存储）
     */
    private String uploadFile(MultipartFile file, FilePathEnum filePathEnum) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return uploadFile(file, filePathEnum, localDateTime);
    }

    private String uploadFile(MultipartFile file, FilePathEnum filePathEnum, LocalDateTime localDateTime) {
        String absFolderPath = FilePathUtil.getAbsFolderPath(filePathEnum, localDateTime);
        String fileName = file.getOriginalFilename();  //文件名字
        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //文件唯一名称
        String fileOnlyName = UUID.randomUUID().toString().toUpperCase().replace("-", "") + "." + suffix;
        //文件存放路径
        File dir = new File(absFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(absFolderPath + fileOnlyName);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new ServiceException("上传失败");
        }
        return FilePathUtil.getStaticFilePath(filePathEnum, fileOnlyName, localDateTime);
    }

    /**
     * 保存文件记录
     *
     * @param file 文件
     * @param path 文件映射地址|外部访问地址
     * @return 文件记录
     */
    private FileRecordDO saveFileRecord(MultipartFile file, String relationType, String path, Long relationId) {
        FileRecordDO fileRecordDO = new FileRecordDO();
        fileRecordDO.setRelationType(relationType);
        fileRecordDO.setRelationId(relationId);
        fileRecordDO.setUserId(SecurityUtil.getUserId());
        fileRecordDO.setName(file.getOriginalFilename());
        fileRecordDO.setType(file.getContentType());
        fileRecordDO.setPath(path);
        return fileRecordRepository.save(fileRecordDO);
    }
}
