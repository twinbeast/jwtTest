package com.example.jwttest.Dao;

import com.example.jwttest.Entity.PatientUserEntity;
import com.example.jwttest.Repository.UserRepository;
import com.example.jwttest.Vo.PatientUserVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UserDao {
    @Autowired
    UserRepository userRepo;
    @Autowired
    Gson gson;

    public Boolean duplicateCheck(String userId){
        PatientUserEntity entity = userRepo.findByPatientId(userId);
        log.info("duplicateCheck : "+gson.toJson(entity));
        if(entity!=null)
            return true;
        else
            return false;
    }

    public PatientUserVo insertUserData(PatientUserVo userData){
        PatientUserEntity entity = userRepo.save(PatientUserEntity.builder()
                .patientId(userData.getPatientId())
                .patientPw(userData.getPatientPw())
                .country(userData.getCountry())
                .dateOfBirth(userData.getDateOfBirth())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .sex(userData.getGender())
                .status(0)
                .regDate(LocalDateTime.now())
                .build());
        return PatientUserVo.builder()
                .patientNo(entity.getPatientNo().intValue())
                .patientId(entity.getPatientId())
                .patientPw(entity.getPatientPw())
                .dateOfBirth(entity.getDateOfBirth())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getSex())
                .regDate(entity.getRegDate().toString())
                .status(entity.getStatus())
                .build();
    }
}
