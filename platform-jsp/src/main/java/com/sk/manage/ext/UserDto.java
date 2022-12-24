package com.sk.manage.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2022-12-23 9:24 PM
 */

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 5868241102271916592L;

    private String role;

    private String username;

    private String password;

    private String status;

    private Integer id;


}
