package com.festnode.festnode.dto;

import com.festnode.festnode.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private AppUser user;
    private String token;
}
