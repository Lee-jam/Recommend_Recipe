package com.refrigerator.springboot.dto;

import com.refrigerator.springboot.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String nickname;

    public MemberDto(Member user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPw();
        this.nickname = user.getNickname();
    }
}
