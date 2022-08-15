package com.rtsj.return_to_soju.model.dto;

import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.Data;

@Data
public class EmotionWithPercentDto {

    private Emotion emotion;

    private float percent;
}
