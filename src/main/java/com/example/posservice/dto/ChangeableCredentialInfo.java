package com.example.posservice.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeableCredentialInfo {
    private String oldPassword;
    private String newPassword;
    private String email;
}
