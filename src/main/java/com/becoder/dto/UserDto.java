package com.becoder.dto;

import com.becoder.entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNo;
    private String password;
    private List<RoleDto> roles;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RoleDto{
        private Integer id;
        private String name;

      }
}
