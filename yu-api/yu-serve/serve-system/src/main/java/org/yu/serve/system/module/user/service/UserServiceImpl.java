package org.yu.serve.system.module.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.yu.common.core.exception.ServiceException;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.common.web.exception.EntityExistException;
import org.yu.serve.file.util.FileUtil;
import org.yu.serve.system.enums.SystemFileEnum;
import org.yu.serve.system.module.dept.domain.QDeptDO;
import org.yu.serve.system.module.role.domain.QRoleDO;
import org.yu.serve.system.module.user.domain.QUserDO;
import org.yu.serve.system.module.user.domain.QUserRoleDO;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.domain.UserRoleDO;
import org.yu.serve.system.module.user.dto.UserBaseInfo;
import org.yu.serve.system.module.user.dto.UserDTO;
import org.yu.serve.system.module.user.dto.UserFullDTO;
import org.yu.serve.system.module.user.repository.UserRepository;
import org.yu.serve.system.module.user.repository.UserRoleRepository;
import org.yu.serve.system.util.PasswordUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    //TODO 默认密码，后续放到全局配置表中
    private static final String DEFAULT_PASS = "123456";

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
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
        if (userRepository.findByUsername(domain.getUsername()) != null) {
            throw new EntityExistException(UserDO.class, "username", domain.getUsername());
        }

        if (StringUtils.hasText(domain.getPortraitBase64())) {
            domain.setPortraitUrl(FileUtil.base64ToFileSave(domain.getPortraitBase64(), SystemFileEnum.USER_FILE));
        }

        domain.setPassword(PasswordUtil.encodedPassword(DEFAULT_PASS));
        userRepository.save(domain);
        saveUserRole(domain);
        return domain;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserDO domain) {
        if (domain.getId() == null) {
            throw new ServiceException("参数错误：id不存在！");
        }
        UserDO dbUser = baseRepository.findByUsername(domain.getUsername());
        if (dbUser != null && !dbUser.getId().equals(domain.getId())) {
            throw new EntityExistException(UserDO.class, "username", domain.getUsername());
        }
        //修改用户名
        if (dbUser == null) {
            dbUser = baseRepository.findById(domain.getId()).get();
        }
        assert dbUser != null;
        if (StringUtils.hasText(domain.getPortraitBase64())) {
            String fileName = domain.getUsername() + "_" + System.currentTimeMillis() + ".jpeg";
            domain.setPortraitUrl(FileUtil.base64ToFileSave(domain.getPortraitBase64(), SystemFileEnum.USER_FILE, fileName));
        }
        domain.setPassword(dbUser.getPassword());
        baseRepository.save(domain);
        saveUserRole(domain);
    }

    @Override
    public UserDO getById(String id) {
        UserDO userDO = baseRepository.findById(id).orElseThrow(() -> new ServiceException("数据不存在！"));
        Set<String> roleIds = new HashSet<>(getJPAQueryFactory()
                .select(qUserRoleDO.roleId)
                .from(qUserRoleDO)
                .where(qUserRoleDO.userId.eq(id))
                .fetch());
        userDO.setRoleIds(roleIds);
        return userDO;
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
                        qUserDO.deptId.eq(qDeptDO.id)
                ).fetchOne();
        assert userFullDTO != null;
        userFullDTO.setRoles(getRoleCodesByUserId(userId));
        return userFullDTO;
    }

    @Override
    public Set<String> getRoleCodesByUserId(String userId) {
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        return new HashSet<>(jpaQueryFactory.select(qRoleDO.code).distinct()
                .from(qRoleDO)
                .join(qUserRoleDO).on(qUserRoleDO.userId.eq(userId).and(qUserRoleDO.roleId.eq(qRoleDO.id)))
                .fetch());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeEnabled(String id, boolean enabled) {
        getJPAQueryFactory().update(qUserDO)
                .set(qUserDO.enabled, enabled)
                .where(qUserDO.id.eq(id))
                .execute();
    }

    @Override
    public void updateBaseInfo(UserBaseInfo userBaseInfo) {
        UserDO userDO = userRepository.findByUsername(userBaseInfo.getUsername());
        if (userDO == null) {
            throw new ServiceException("数据异常！");
        }
        if (StringUtils.hasText(userBaseInfo.getPortraitBase64())) {
            String fileName = userBaseInfo.getUsername() + "_" + System.currentTimeMillis() + ".jpeg";
            userDO.setPortraitUrl(FileUtil.base64ToFileSave(userBaseInfo.getPortraitBase64(), SystemFileEnum.USER_FILE, fileName));
        }
        userDO.setName(userBaseInfo.getName());
        userDO.setEmail(userBaseInfo.getEmail());
        userRepository.save(userDO);
    }
}
