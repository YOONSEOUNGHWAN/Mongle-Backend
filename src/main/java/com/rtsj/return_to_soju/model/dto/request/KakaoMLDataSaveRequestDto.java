package com.rtsj.return_to_soju.model.dto.request;

import com.rtsj.return_to_soju.model.dto.dto.KakaoMLData;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

@Data
public class KakaoMLDataSaveRequestDto {
    private Long userId;
    private List<KakaoMLData> data;

}

