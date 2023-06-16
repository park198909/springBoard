package com.study.commons;

import com.study.commons.constants.Role;
import com.study.entities.Member;
import com.study.models.member.MemberInfo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberUtil {

    @Autowired
    private HttpSession session;

    /**
     * 로그인 여부
     * @return
     */
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * 관리자 여부
     * @return
     */
    public boolean isAdmin() {
        return isLogin() && getMember().getRoles() == Role.ADMIN;
    }

    public MemberInfo getMember() {
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");

        return memberInfo;
    }

    public Member getEntity() {

        if (isLogin()) {
            Member member = new ModelMapper().map(getMember(), Member.class);
            return member;
        }

        return null;
    }

}
