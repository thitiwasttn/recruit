package com.thitiwas.recruit.recruit.model;

import com.thitiwas.recruit.recruit.entity.Job;
import com.thitiwas.recruit.recruit.entity.MemberCertificate;
import com.thitiwas.recruit.recruit.entity.MemberProfile;
import com.thitiwas.recruit.recruit.entity.MemberVideo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateMemberFullM {
    private MemberProfile memberProfile;
    private List<Job> jobs;
    private List<MemberVideoM> memberVideos;
    private List<MemberCertM> memberCertificates;
}
