package org.yu.tenant.service.api.datasource.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDataSourceDO is a Querydsl query type for DataSourceDO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDataSourceDO extends EntityPathBase<DataSourceDO> {

    private static final long serialVersionUID = -1489031174L;

    public static final QDataSourceDO dataSourceDO = new QDataSourceDO("dataSourceDO");

    public final org.yu.common.querydsl.domain.QDslBaseDO _super = new org.yu.common.querydsl.domain.QDslBaseDO(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final StringPath url = createString("url");

    public final StringPath username = createString("username");

    public QDataSourceDO(String variable) {
        super(DataSourceDO.class, forVariable(variable));
    }

    public QDataSourceDO(Path<? extends DataSourceDO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataSourceDO(PathMetadata metadata) {
        super(DataSourceDO.class, metadata);
    }

}

