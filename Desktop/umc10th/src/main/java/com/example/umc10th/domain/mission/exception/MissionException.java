package com.example.umc10th.domain.mission.exception;

import lombok.Getter;


public class MissionException extends RuntimeException {
    public MissionException(String message) {
        super(message);
    }
}
