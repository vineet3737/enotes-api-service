package com.becoder.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;
}
