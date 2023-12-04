package com.miniproject.library.util;

import com.miniproject.library.dto.user.UserRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;

public class JwtToken {
    private JwtToken(){
    }
    public static String getToken(UserRequest request){
        long nowMilis = System.currentTimeMillis();
        Date now = new Date(nowMilis);
        Date expireDate = new Date(nowMilis);
        Key key = MacProvider.generateKey();
        return Jwts.builder()
                .setSubject(String.valueOf(request))
                .setAudience("users")
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
