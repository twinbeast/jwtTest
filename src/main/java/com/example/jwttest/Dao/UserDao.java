package com.example.jwttest.Dao;

import com.example.jwttest.Entity.PatientUserEntity;
import com.example.jwttest.Repository.UserRepository;
import com.example.jwttest.Vo.PatientUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDao {
    @Autowired
    UserRepository userRepo;

    public Boolean getUserById(String userId){
        PatientUserEntity entity = userRepo.findByPatientId(userId);
        if(entity==null)
            return true;
        else
            return false;
    }

    public PatientUserVo insertUserData(PatientUserVo userData){
        PatientUserEntity entity = userRepo.save(PatientUserEntity.builder()
                .patientId(userData.getPatientId())
                .patientPw(userData.getPaitentPw())
                .country(userData.getCountry())
                .dateOfBirth(userData.getDateOfBirth())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .sex(userData.getGender())
                .build());
        return PatientUserVo.builder().patientNo(entity.getPatientNo().intValue())
                .patientId(entity.getPatientId())
                .paitentPw(entity.getPatientPw())
                .dateOfBirth(entity.getDateOfBirth())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getSex())
                .regDate(entity.getRegDate().toString())
                .status(entity.getStatus())
                .build();
    }
}