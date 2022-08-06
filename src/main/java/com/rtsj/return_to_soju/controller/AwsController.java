package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.request.UploadKakaoTextDto;
import com.rtsj.return_to_soju.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Aws Controller", description = "Aws controller desc")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AwsController {

    private final S3Service s3Service;

    @Operation(
            summary = "카카오톡 텍스트 보내기",
            description = "카카오톡 텍스트를 보내면 백엔드 단에서 s3 스토리지에 올린다."
    )
    @ApiResponses(value =
            @ApiResponse(responseCode = "201", description = "요청 성공",
            content = @Content(schema = @Schema(implementation = List.class)))
    )
    @PostMapping("/s3/kakao")
    public List<String> uploadKakaoText(@ModelAttribute UploadKakaoTextDto dto) {
        List<String> strings = s3Service.uploadFile(dto.getFiles());
        return strings;
    }
}
