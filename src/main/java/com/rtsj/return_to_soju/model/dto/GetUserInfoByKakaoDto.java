package com.rtsj.return_to_soju.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoByKakaoDto {

    private Long id;
    private String nickname;
    public void setAllData(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
