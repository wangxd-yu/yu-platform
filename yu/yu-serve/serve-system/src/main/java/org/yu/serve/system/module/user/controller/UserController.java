package org.yu.serve.system.module.user.controller;

import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.dto.UserFullDTO;
import org.yu.serve.system.module.user.dto.UserTableDTO;
import org.yu.serve.system.module.user.query.UserTableQuery;
import org.yu.serve.system.module.user.service.UserService;
import org.yu.common.core.context.YuContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxd
 * @date 2020-11-09 10:02
 */
@RestController
@RequestMapping("user")
public class UserController extends DslBaseApiController<UserService, UserDO, Long> {

    public UserController(UserService userService) {
        super(userService);
    }

    @GetMapping("info")
    public ResponseEntity<UserFullDTO> getUserInfo() {
        return new ResponseEntity<>(dslBaseService.getUserInfo(YuContextHolder.getYuContext().getClientUser().getId()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(UserTableQuery query, Pageable pageable) {
        return super.queryDTO(query, pageable, UserTableDTO.class);
    }
}
