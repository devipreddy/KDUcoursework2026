package com.example.hospitaljpa.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    private String name;
    private String role;
}
