package com.study.repositories;

import com.study.entities.Member;
import com.study.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    // 아이디로 조회
    Member findByUserId(String userId);

    // 아이디로 회원 존재 유무 체크
    default boolean exists(String userId) {
        QMember member = QMember.member;
        return exists(member.userId.eq(userId));
    }
}