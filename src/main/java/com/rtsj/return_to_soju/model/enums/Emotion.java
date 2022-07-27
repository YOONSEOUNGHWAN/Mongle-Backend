package com.rtsj.return_to_soju.model.enums;

public enum Emotion {
    UPSET("분노"), HAPPY("행복"), EMBRASSED("당황"), SAD("슬픔"), ANXIETY("불안"), MIDDLE("중립");


    private String korean;

    Emotion(String korean) {
        this.korean = korean;
    }
}
