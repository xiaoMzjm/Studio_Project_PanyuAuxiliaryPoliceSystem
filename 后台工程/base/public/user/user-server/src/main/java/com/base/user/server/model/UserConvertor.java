package com.base.user.server.model;

import com.base.user.client.model.UserVO;
import org.springframework.beans.BeanUtils;

/**
 * @author:小M
 * @date:2019/2/21 1:13 AM
 */
public class UserConvertor {

    public static UserDTO do2DTO(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);

        return userDTO;
    }

    public static UserVO dto2VO(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDTO, userVO);
        return userVO;
    }
}
