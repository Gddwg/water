package com.water.entity;

import lombok.Data;

@Data
public class TokenBind {
    private Long id;
    private String token;
    private String username;
}
