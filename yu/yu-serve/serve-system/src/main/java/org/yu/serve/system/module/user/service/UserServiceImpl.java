package org.yu.serve.system.module.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.core.exception.BadRequestException;
import org.yu.common.core.exception.EntityExistException;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dept.domain.QDeptDO;
import org.yu.serve.system.module.role.domain.QRoleDO;
import org.yu.serve.system.module.user.domain.QUserDO;
import org.yu.serve.system.module.user.domain.QUserRoleDO;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.domain.UserRoleDO;
import org.yu.serve.system.module.user.dto.UserDTO;
import org.yu.serve.system.module.user.dto.UserFullDTO;
import org.yu.serve.system.module.user.repository.UserRepository;
import org.yu.serve.system.module.user.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-09 09:59
 */
@Service
public class UserServiceImpl extends DslBaseServiceImpl<UserRepository, UserDO, String> implements UserService {

    QUserDO qUserDO = QUserDO.userDO;
    QRoleDO qRoleDO = QRoleDO.roleDO;
    QUserRoleDO qUserRoleDO = QUserRoleDO.userRoleDO;
    QDeptDO qDeptDO = QDeptDO.deptDO;

    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    private void saveUserRole(UserDO domain) {
        getJPAQueryFactory().delete(qUserRoleDO).where(qUserRoleDO.userId.eq(domain.getId())).execute();
        List<UserRoleDO> userRoleDOS = new ArrayList<>(domain.getRoleIds().size());
        for (String menuId : domain.getRoleIds()) {
            userRoleDOS.add(new UserRoleDO(domain.getId(), menuId));
        }
        userRoleRepository.saveAll(userRoleDOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDO save(UserDO domain) {
        if (baseRepository.findByUsername(domain.getUsername()) != null) {
            throw new EntityExistException(UserDO.class, "username", domain.getUsername());
        }
        baseRepository.save(domain);
        saveUserRole(domain);
        return domain;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserDO domain) {
        if(domain.getId() == null) {
            throw new BadRequestException("参数错误：id不存在！");
        }
        UserDO dbUser = baseRepository.findByUsername(domain.getUsername());
        if (dbUser != null && !dbUser.getId().equals(domain.getId())) {
            throw new EntityExistException(UserDO.class, "username", domain.getUsername());
        }
        baseRepository.save(domain);
        saveUserRole(domain);
    }

    @Override
    public UserDTO findDtoById(String userId) {
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        UserDTO userDTO = jpaQueryFactory.select(YuQueryHelp.getJpaDTOSelect(UserDTO.class))
                .from(qUserDO)
                .where(qUserDO.id.eq(userId))
                .fetchOne();

        QUserRoleDO qUserRoleDO = QUserRoleDO.userRoleDO;
        List<String> roleCodes = jpaQueryFactory.select(qRoleDO.code)
                .from(qRoleDO, qUserRoleDO)
                .where(
                        qUserRoleDO.userId.eq(userId),
                        qRoleDO.id.eq(qUserRoleDO.roleId)
                ).fetch();
        assert userDTO != null;
        userDTO.setRoles(roleCodes);
        return userDTO;
    }

    @Override
    public UserFullDTO getUserInfo(String userId) {
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        UserFullDTO userFullDTO = jpaQueryFactory.select(YuQueryHelp.getJpaDTOSelect(UserFullDTO.class))
                .from(qUserDO, qDeptDO)
                .where(
                        qUserDO.id.eq(userId),
                        qUserDO.deptNo.eq(qDeptDO.no)
                ).fetchOne();
        assert userFullDTO != null;
        userFullDTO.setRoles(getRoleCodeByUserId(userId));
        return userFullDTO;
    }

    private List<String> getRoleCodeByUserId(String userId) {
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        return jpaQueryFactory.select(qRoleDO.code)
                .from(qRoleDO)
                .leftJoin(qUserRoleDO).on(qUserRoleDO.userId.eq(userId).and(qUserRoleDO.roleId.eq(qRoleDO.id)))
                .fetch();
    }
}
