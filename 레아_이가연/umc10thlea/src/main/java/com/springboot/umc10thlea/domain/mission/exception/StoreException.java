package com.springboot.umc10thlea.domain.mission.exception;


import com.springboot.umc10thlea.global.apiPayload.code.BaseErrorCode;
import com.springboot.umc10thlea.global.apiPayload.exception.ProjectException;

//미션 생성 시 가게 정보를 찾음
public class StoreException extends ProjectException {
    public StoreException(BaseErrorCode code) {super(code);}
}