package com.rtsj.return_to_soju.model.enums;

public enum Emotion {
    JOY("기쁨"), HOPE("희망"), NEUTRALITY("중립"), SADNESS("슬픔"), ANGER("분노"), ANXIETY("불안"), TIREDNESS("피곤"), REGRET("후회");
//    행복(기쁨) / 슬픔 / 당황 ->피곤 / 불안 / 중립 / 분노 / + 후회  희망
//
//    행복(기쁨)희망 / 슬픔+ 후회  / 당황 ->피곤 / 불안 / 중립 / 분노 /
    // 슬픔, 피곤, 후회 -> 우울 <> 비우울
    private String korean;

    Emotion(String korean) {
        this.korean = korean;
    }
}
