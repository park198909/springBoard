package com.study.models.member;

import com.study.entities.Member;
import com.study.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUserId(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        return MemberInfo.builder()
                .userNo(member.getUserNo())
                .userNm(member.getUserNm())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .email(member.getEmail())
                .mobile(member.getMobile())
                .build();
    }
}
