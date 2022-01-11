package com.example.jwttest.Vo.Jwt;

import java.io.Serializable;
import java.util.Collection;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtMemberModel implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    private String uid;
    private String id;
    private String password;
    private String memberName;
    private String genderCd;
    private String birthday;
    private String phone;
    private String email;
    private String token;
    private String expiredDttm;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return this.uid;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}