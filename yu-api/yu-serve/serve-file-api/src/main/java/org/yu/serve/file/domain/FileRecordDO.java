package org.yu.serve.file.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "com_file")
@EntityListeners(AuditingEntityListener.class)
public class FileRecordDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 文件的md5值
	 */
	private String md5;

	/**
	 * 关联文件类型 参照配置文件的 pathMap 值
	 */
	@Column(length = 16)
	private String relationType;
	/**
	 * 关联id
	 */
	@Column(length = 20)
	private Long relationId;
	/**
	 * 排序（关联id相同时的排序）
	 */
	private int sort;
	/**
	 * 上传人id
	 */
	private String userId;
	/**
	 * 文件名
	 */
	@Column(length = 128)
	private String name;
	/**
	 * 文件路径
	 */
	@Column(length = 128)
	private String path;
	/**
	 * 文件类型
	 */
	@Column(length = 32)
	private String type;
	/**
	 * 创建时间
	 */
	@CreatedDate
	private Date createTime;
}
