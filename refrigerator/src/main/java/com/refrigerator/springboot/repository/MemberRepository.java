package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.constant.LoginType;
import com.refrigerator.springboot.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByEmail(String email);
    Member findByNickname(String nickname);

    Member findByEmailAndLoginType(String email, LoginType loginType);
//    @Query("select m from Member m where m.mem_banned ='Y'")
//    List<Member> bannedMember();

}
