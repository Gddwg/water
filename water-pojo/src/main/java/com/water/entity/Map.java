package com.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("map")
public class Map {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String mapName;
    private Integer floor;
    private Double resolution;
    private Integer width;
    private Integer height;
    private Double originX;
    private Double originY;
}
