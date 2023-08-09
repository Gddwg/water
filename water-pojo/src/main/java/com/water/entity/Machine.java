package com.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("machine")
public class Machine {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String productId;
    private String address;
    private Date createDate;
}
