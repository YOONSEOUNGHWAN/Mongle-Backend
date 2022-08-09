package com.rtsj.return_to_soju.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SentenceByEmotionWithDayDto {

    @Schema(description = "추출된 문장 list")
    private List<String> sentenceList = new ArrayList<>();

    @Schema(description = "원본 파일 url")
    private String url;

}
