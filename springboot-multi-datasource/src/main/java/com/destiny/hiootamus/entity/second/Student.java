package com.destiny.hiootamus.entity.second;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-25 8:01 PM
 */

@Data
@TableName(value = "tb_student")
public class Student implements Serializable {



    @TableId(type= IdType.ASSIGN_ID, value = "id")
    private Long id;

    @TableField(value = "username")
    private String username;




}
