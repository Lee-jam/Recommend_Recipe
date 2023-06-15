package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
