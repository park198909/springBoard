package com.study.controllers.members;

import com.study.commons.validators.MobileValidator;
import com.study.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileValidator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         *  1. 아이디 중복 체크
         *  2. 비밀번호 복잡성 체크(알파벳(대문자,소문자), 숫자, 특수문자)
         *  3. 비밀번호, 비밀번호확인 일치
         *  4. 휴대전화번호(선택) - 입력된 경우 형식 체크
         *  5. 휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드객체에 저장
         *  6. 필수 약관 동의 체크
         */

        JoinForm joinForm = (JoinForm) target;
        String userId = joinForm.getUserId();
        String userPw = joinForm.getUserPw();
        String userPwRe = joinForm.getUserPwRe();
        String mobile = joinForm.getEmail();

        // 1. 아이디 중복 체크
        if (userId != null && !userId.isBlank() &&  memberRepository.exists(userId)) {
            errors.rejectValue("userId", "Validation.duplicate.userId");
        }

        // 3. 비밀번호, 비밀번호확인 일치
        if (userPw != null && !userPw.isBlank() && userPwRe != null && !userPwRe.isBlank()) {
            errors.rejectValue("userPwRe", "Validation.incorrect.userPwRe");
        }

        // 4. 휴대전화번호(선택) - 입력된 경우 형식 체크

    }
}
