package com.world_cup.reservation.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Data
@Service
@Slf4j
@RequiredArgsConstructor
public class ExtractJWT {



    private final SecurityProperties secProperties;

    // algorithm that hash with

    Algorithm _algorithm ;
    @PostConstruct
    private void setAlgorithms(){
        _algorithm= Algorithm.HMAC256(secProperties.getSecret_key().getBytes());
    }

    public Long userIdJWTExtraction(String token){
        String tokenWithoutBearer = token.substring((secProperties.getBearer() + " ").length());


        JWTVerifier verifier = JWT.require(_algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(tokenWithoutBearer);
        String userId = decodedJWT.getClaim("id").toString();

        System.out.println(userId);
        if(userId == null) return null;

        return Long.valueOf(userId);

    }


    public String endAccessTokenTime(String accessToken , HttpServletRequest request){
        String token = accessToken.substring((secProperties.getBearer() + " ").length());
        // check algorithm that hash with
        JWTVerifier verifier = JWT.require(_algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return JWT.create()
                .withSubject(decodedJWT.getSubject())
                .withExpiresAt(new Date(System.currentTimeMillis() - 30 * 60 * 1000)) //expire after 30 minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("id",  Long.valueOf(decodedJWT.getSubject()))
                .withClaim("role",  decodedJWT.getClaim("role").asString())
                .withClaim("isVerified",  decodedJWT.getClaim("isVerified").asBoolean())
                .withClaim("username", decodedJWT.getClaim("username").asInt())
                .sign(_algorithm);
    }


    public String creatAccessToken(String userId, String username, int role , boolean isVerified,
                                   HttpServletRequest request) {

        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) //expire after 30 minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("id",  Long.valueOf(userId))
                .withClaim("role",  role)
                .withClaim("isVerified",  isVerified)
                .withClaim("username", username)
                .sign(_algorithm);
    }

    public String createRefreshToken(String userId,
                                     String username,
                                     HttpServletRequest request) {

        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 24 * 7 * 60 * 1000)) //expire after a week
                .withIssuer(request.getRequestURL().toString())
                .withClaim("username", username)
                .sign(_algorithm);
    }




    public  String refreshAccessToken(String refreshToken) {

        String token = refreshToken.substring((secProperties.getBearer() + " ").length());
        // check algorithm that hash with
        JWTVerifier verifier = JWT.require(_algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("username").asString();
    }

    public Map<String, String> getTokensAsJson(String access_token, String refresh_token) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        return tokens;
    }



    public boolean checkIfTokenIsExpired(String token){
        String btoken = token.substring((secProperties.getBearer() + " ").length());

        JWTVerifier verifier = JWT.require(_algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(btoken);
        if (decodedJWT.getExpiresAt().before(new Date())){
            return true;
        }
        return false;
    }

}
