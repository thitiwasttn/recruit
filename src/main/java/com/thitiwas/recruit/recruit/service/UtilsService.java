package com.thitiwas.recruit.recruit.service;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UtilsService {

    @Value("${custom.constant.secretPassword}")
    private String secret;

    public String hashStr(String originalString) {
        return DigestUtils.sha256Hex(originalString.concat(secret));
    }
}
