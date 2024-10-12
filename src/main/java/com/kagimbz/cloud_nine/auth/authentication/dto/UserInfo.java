package com.kagimbz.cloud_nine.auth.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
