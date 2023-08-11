package com.water.dto;

import lombok.Data;

import java.util.List;

@Data
public class MoveDTO {
    private String address;
    private Integer count;
    private List<String> markers;
}
