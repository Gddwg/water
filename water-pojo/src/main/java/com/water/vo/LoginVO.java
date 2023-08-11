package com.water.vo;

import lombok.Data;

@Data
public class LoginVO {
    private Long id;
    private String token;
    private String name;
    private String avatar;
}
