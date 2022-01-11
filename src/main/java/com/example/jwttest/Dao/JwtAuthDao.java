package com.example.jwttest.Dao;

import com.example.jwttest.Entity.JwtAuthEntity;
import com.example.jwttest.Repository.JwtAuthRepository;
import com.example.jwttest.Repository.UserRepository;
import com.example.jwttest.Vo.Jwt.JwtMemberModel;
import com.example.jwttest.Entity.PatientUserEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class JwtAuthDao {
    @Autowired
    UserRepository repo;
    @Autowired
    JwtAuthRepository authrepo;
    @Autowired
    Gson gson;

    public JwtMemberModel selectMember(JwtMemberModel member){
        log.info("selectMember input : "+gson.toJson(member));
        PatientUserEntity userData = repo.findByPatientId(member.getUid());
        return null;
    }
    public JwtMemberModel selectMemberOauth(JwtMemberModel member){
        return JwtMemberModel.builder().build();
    }

    public JwtMemberModel selectMemberById(JwtMemberModel member){
        PatientUserEntity userData = repo.findByPatientId(member.getId());
        if(userData==null)
            return null;
        return JwtMemberModel.builder().uid(userData.getPatientNo().toString()).id(userData.getPatientId()).password(userData.getPatientPw()).build();
    }

    public void deleteMemberOauth(JwtMemberModel member){
        String uid = member.getUid();
        String id = member.getId();
        String token = member.getToken();
        String expiredDateTime = member.getExpiredDttm();
        JwtAuthEntity authData = authrepo.findByPatientNoAndStatusOrderByRegDateDesc(uid, 0);
        authData.setStatus(9);
        authrepo.save(authData);
    }
    public void insertMemberOauth(JwtMemberModel member){
        String uid = member.getUid();
        String id = member.getId();
        String token = member.getToken();
        String expiredDateTime = member.getExpiredDttm();
        JwtAuthEntity authData = authrepo.findByPatientNoAndStatusOrderByRegDateDesc(uid, 9);
        authData.setToken(token);
        authData.setExpireDateTime(expiredDateTime);
    }
}
