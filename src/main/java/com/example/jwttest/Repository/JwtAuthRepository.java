package com.example.jwttest.Repository;

import com.example.jwttest.Entity.JwtAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtAuthRepository extends JpaRepository<JwtAuthEntity, Long> {

    JwtAuthEntity findByPatientNoAndStatusOrderByRegDateDesc(String patientNo, int status);

    JwtAuthEntity findByAccessTokenAndPatientNo(String accessToken, String patientNo);

}
