package com.miniproject.library.util;

import com.miniproject.library.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;

public class JwtToken {
    private JwtToken(){
    }
    public static String getToken(User user){
        long nowMilis = System.currentTimeMillis();
        Date now = new Date(nowMilis);
        Date expireDate = new Date(nowMilis);
        Key key = MacProvider.generateKey();
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setAudience(String.valueOf(user.getUsername()))
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
