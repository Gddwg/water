package com.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("marker")
public class Marker {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer floor;
    private String markerName;
    private Integer k;
    private Double theta;
    private Double x;
    private Double y;
    private Double z;
    private String mapName;
}
