package com.base.user.model;

/**
 * @author:小M
 * @date:2019/2/21 1:13 AM
 */
public class UserConvertor {

    public static UserDTO do2DTO(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setOpenId(userDO.getOpenId());
        userDTO.setNickName(userDO.getNickName());
        userDTO.setGender(userDO.getGender());
        userDTO.setLanguage(userDO.getLanguage());
        userDTO.setCity(userDO.getCity());
        userDTO.setProvince(userDO.getProvince());
        userDTO.setCountry(userDO.getCountry());
        userDTO.setAvatarUrl(userDO.getAvatarUrl());
        userDTO.setId(userDO.getId());
        userDTO.setToken(userDO.getToken());
        userDTO.setAccount(userDO.getAccount());
        userDTO.setPassword(userDO.getPassword());
        userDTO.setGmtCreate(userDO.getGmtCreate());
        userDTO.setGmtModified(userDO.getGmtModified());

        return userDTO;
    }

    public static UserDO dto2DO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setOpenId(userDTO.getOpenId());
        userDO.setNickName(userDTO.getNickName());
        userDO.setGender(userDTO.getGender());
        userDO.setLanguage(userDTO.getLanguage());
        userDO.setCity(userDTO.getCity());
        userDO.setProvince(userDTO.getProvince());
        userDO.setCountry(userDTO.getCountry());
        userDO.setAvatarUrl(userDTO.getAvatarUrl());
        userDO.setId(userDTO.getId());
        userDO.setToken(userDTO.getToken());
        userDO.setAccount(userDTO.getAccount());
        userDO.setPassword(userDTO.getPassword());
        return userDO;
    }

    public static UserVO dto2VO(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        userVO.setAccount(userDTO.getAccount());
        userVO.setToken(userDTO.getToken());
        return userVO;
    }
}
