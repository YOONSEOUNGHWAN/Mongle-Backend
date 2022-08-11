package com.rtsj.return_to_soju.model.enums;

public enum Emotion {
    JOY("기쁨"), HOPE("희망"), NEUTRALITY("중립"), SADNESS("슬픔"), ANGER("분노"), ANXIETY("불안"), TIREDNESS("피곤"), REGRET("후회");

    private String korean;

    Emotion(String korean) {
        this.korean = korean;
    }
}
