package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;
}
