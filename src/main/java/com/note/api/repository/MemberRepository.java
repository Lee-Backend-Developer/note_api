package com.note.api.repository;

import com.note.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustom {
    Optional<Member> findByLoginIdAndPassword(String loginId, String password);

    Optional<Member> findByLoginId(String loginId);
}
