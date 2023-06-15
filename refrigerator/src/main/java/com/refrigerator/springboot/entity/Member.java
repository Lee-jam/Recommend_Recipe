package com.refrigerator.springboot.entity;

import com.refrigerator.springboot.constant.Role;
import com.refrigerator.springboot.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Member")
@DynamicInsert
@Getter
@Setter
@RequiredArgsConstructor
public class Member {
    @Id
    @Column(name = "mem_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mem_name")
    private String name;

    @Column(name = "mem_pw")
    private String pw;

    @Column(name = "mem_email")
    private String email;

    @Column(name = "mem_nick")
    private String nickname;

    @Column(name = "mem_banned")
    @ColumnDefault("'N'")
    private String banned;

    @Column(name = "mem_follow")
    @ColumnDefault("'N'")
    private String follow;

    @Enumerated(EnumType.STRING)
    @Column(name = "mem_role")
    private Role role;
    private LocalDateTime Banndate;
    @Builder
    public Member(Long id, String name, String email, String pw, String nickname, String banned, String follow, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
        this.banned = banned;
        this.follow = follow;
        this.role = role;
    }

    public static Member createMember(MemberDto dto, PasswordEncoder encoder) {
        Member member = new Member(); // 객체 생성
        //받을 정보
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setNickname(dto.getNickname());
        String password = encoder.encode(dto.getPassword());
        member.setPw(password);
        member.setRole(Role.USER);

        return member;
    }

    public Member update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}






























