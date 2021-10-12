package com.thitiwas.recruit.recruit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterM {
    private String email;
    private String password;
    private String confirmPassword;
}
