package com.thitiwas.recruit.recruit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVideoM {
    private Long videoId;
    private boolean videoModify;
    private String videoName;
    private MultipartFile file;
}
