package com.thitiwas.recruit.recruit.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thitiwas.recruit.recruit.config.Constant;
import com.thitiwas.recruit.recruit.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${custom.constant.secretLogin}")
    private String secretLogin;

    @Value("${custom.constant.issuer}")
    private String issuer;

    public String createToken(Member member) {
        //HMAC
        Algorithm algorithmHS = getAlgorithm(secretLogin);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date dateExpire = calendar.getTime();

        return JWT.create()
                .withClaim("id", member.getId())
                .withClaim(Constant.JWT_USER_TYPE, Constant.JWT_USER_TYPE_MEMBER)
                // who create
                .withIssuer(issuer)
                .withExpiresAt(dateExpire)
                .sign(algorithmHS);
    }

    /**
    * if verify passed will return decoded else return null
    * */
    public DecodedJWT verifyToken(String token) {
        DecodedJWT ret;
        try {
            Algorithm algorithm = getAlgorithm(secretLogin);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            ret = verifier.verify(token);
        } catch (Exception e) {
            ret = null;
        }

        return ret;
    }

    private Algorithm getAlgorithm(String secretLogin) {
        return Algorithm.HMAC256(secretLogin);
    }
}
