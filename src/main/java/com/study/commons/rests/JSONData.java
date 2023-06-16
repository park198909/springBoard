package com.study.commons.rests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JSONData<T> {
    private boolean success; // 성공여부 확인
    private HttpStatus status = HttpStatus.OK;  // 200
    private String message; // 에러메시지 확인용
    private T data; // 성공 데이터
}
