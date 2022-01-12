package com.example.jwttest.Dao;

import com.example.jwttest.Entity.JwtAuthEntity;
import com.example.jwttest.Repository.JwtAuthRepository;
import com.example.jwttest.Repository.UserRepository;
import com.example.jwttest.Vo.Jwt.JwtMemberModel;
import com.example.jwttest.Entity.PatientUserEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthDao {
    @Autowired
    UserRepository userRepo;
    @Autowired
    JwtAuthRepository authRepo;
    @Autowired
    Gson gson;

    public JwtMemberModel selectMember(JwtMemberModel member){
        log.info("selectMember input : "+gson.toJson(member));
        PatientUserEntity userData = userRepo.findByPatientNo(Long.parseLong(member.getUid()));
        if(userData==null)
            return null;
        return JwtMemberModel.builder()
                .uid(userData.getPatientNo().toString())
                .id(userData.getPatientId())
                .birthday(userData.getDateOfBirth())
                .build();
    }
    public JwtMemberModel selectMemberOauth(JwtMemberModel member){
        log.info("selectMemberOauth Input: "+gson.toJson(member));
        return JwtMemberModel.builder().build();
    }

    public JwtMemberModel selectMemberById(JwtMemberModel member){
        PatientUserEntity userData = userRepo.findByPatientId(member.getId());
        if(userData==null)
            return null;
        return JwtMemberModel.builder().uid(userData.getPatientNo().toString()).id(userData.getPatientId()).password(userData.getPatientPw()).build();
    }

    public void deleteMemberOauth(JwtMemberModel member){
        String uid = member.getUid();
        String id = member.getId();
        String token = member.getToken();
        String expiredDateTime = member.getExpiredDttm();
        JwtAuthEntity authData = authRepo.findByPatientNoAndStatusOrderByRegDateDesc(Long.parseLong(uid), 0);
//        JwtAuthEntity authData = authRepo.findByPatientNoAndTokenAndStatus(Long.parseLong(uid), token, 0);
        if(authData==null)
            log.error("Login - deleteMemberOauth : Fail Get Auth Data - "+Long.parseLong(uid)+" / "+gson.toJson(member));
        else{
            authData.setStatus(9);
            log.info("Login - deleteMemberOauth : "+gson.toJson(authData));
            authRepo.save(authData);
        }
    }

    public void insertMemberOauth(JwtMemberModel member){
        String uid = member.getUid();
        String id = member.getId();
        String token = member.getToken();
        String expiredDateTime = member.getExpiredDttm();
        JwtAuthEntity authData = JwtAuthEntity.builder().patientNo(Long.parseLong(uid)).token(token).expireDateTime(expiredDateTime).build();
        authRepo.save(authData);
    }
}
