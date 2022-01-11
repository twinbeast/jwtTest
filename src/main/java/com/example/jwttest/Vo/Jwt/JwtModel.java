package com.example.jwttest.Vo.Jwt;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String accessTokenExpirationDate;
    private String refreshToken;
    private String refreshTokenExpirationDate;
}
