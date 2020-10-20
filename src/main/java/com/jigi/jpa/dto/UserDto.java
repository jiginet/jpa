package com.jigi.jpa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private String username;
    private Long age;

    public UserDto() {
    }

    public UserDto(String username, Long age) {
        this.username = username;
        this.age = age;
    }
}
