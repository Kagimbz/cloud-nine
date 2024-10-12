package com.kagimbz.cloud_nine.auth.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String jwt;
    private int statusCode;
    private String message;
    private UserInfo userInfo;
}
