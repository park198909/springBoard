package com.study.models.member;

import com.study.commons.constants.Role;
import com.study.controllers.members.JoinForm;
import com.study.entities.Member;
import com.study.repositories.MemberRepository;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *  회원정보 추가, 수정
 *  - 수정시 비밀번호는 값이 있을때만 수정
 */
@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(JoinForm joinForm) {

        Member member = new ModelMapper().map(joinForm, Member.class);
        member.setRoles(Role.USER);

        member.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));

        memberRepository.saveAndFlush(member);
    }

}
