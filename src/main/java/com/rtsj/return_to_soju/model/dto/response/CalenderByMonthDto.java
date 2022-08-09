package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CalenderByMonthDto {

    private LocalDate date;
    private Emotion emotion;
    private List<String> subjectList = new ArrayList<>();
}
