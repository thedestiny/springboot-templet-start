package com.destiny.dog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.destiny.dog.basic.BaseEntity;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName(value = "tb_user")
public class User extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = -7505678942156357962L;
    private Long id ;
    private String username;
    private String password;
    private String salt;
    private String nickname;
    


}
