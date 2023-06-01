package com.destiny.cat.entity;

import com.destiny.cat.basic.BaseEntity;
import com.destiny.cat.config.plugins.Sensitive;
import com.destiny.cat.config.plugins.SensitiveRule;
import lombok.Data;

import java.io.Serializable;


@Data
public class User extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = -7505678942156357962L;

    private Long id ;

    @Sensitive(value = SensitiveRule.USER_NAME)
    private String username;

    @Sensitive(value = SensitiveRule.ID_CARD)
    private String idCard;

    @Sensitive(value = SensitiveRule.PHONE)
    private String cellphone;


    private String password;

}
