package com.jars.HospitalManagement.dto;

import com.jars.HospitalManagement.entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    private String username;
    private String password;
    private String name;

    //not recommended in production
    private Set<RoleType> roles=new HashSet<>();
}
