package com.becoder.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetReq {

    private Integer uid;
    private String newPassword;
}
