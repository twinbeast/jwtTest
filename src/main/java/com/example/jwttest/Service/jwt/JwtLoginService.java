package com.example.jwttest.Service.jwt;

import com.example.jwttest.Dao.JwtAuthDao;
import com.example.jwttest.Vo.Jwt.JwtMemberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtLoginService implements UserDetailsService {
    @Autowired
    JwtAuthDao memberDAO;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername : "+uid);
        JwtMemberModel resMemberModel = memberDAO.selectMember(JwtMemberModel.builder()
                .uid(uid)
                .build());

        if (resMemberModel == null) {
            throw new UsernameNotFoundException("ID : '" + uid + "' not found");
        }
        return resMemberModel;
    }


}