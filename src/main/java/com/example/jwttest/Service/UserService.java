package com.example.jwttest.Service;

import com.example.jwttest.Dao.JwtAuthDao;
import com.example.jwttest.Dao.UserDao;
import com.example.jwttest.Security.jwt.JwtTokenProvider;
import com.example.jwttest.Vo.Jwt.JwtMemberModel;
import com.example.jwttest.Vo.Jwt.JwtModel;
import com.example.jwttest.Vo.PatientUserVo;
import com.example.jwttest.Vo.ResultVo;
import com.google.gson.Gson;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    JwtAuthDao memberDAO;
    @Autowired
    Gson gson;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public ResultVo checkDuplicate(String userId){
        if(userDao.duplicateCheck(userId))
            return ResultVo.builder().result("fail").msg("Duplicated").build();
        else
            return ResultVo.builder().result("success").msg("Not Duplicated").build();
    }

    public ResultVo signUp(PatientUserVo user){
        log.info("/signUp: "+gson.toJson(user));
        if(userDao.duplicateCheck(user.getPatientId()))
            return ResultVo.builder().result("fail").msg("Duplicated").build();

        user.setPatientPw(passwordEncoder.encode(user.getPatientPw()));
        user = userDao.insertUserData(user);
        if(user==null)
            return ResultVo.builder().result("fail").msg("Fail Insert").build();
        else {
            user.setPatientPw(null);
            return ResultVo.builder().result("success").msg("").data(user).build();
        }
    }

    public ResultVo signIn(HttpServletResponse response, JwtMemberModel memberModel){
        JwtMemberModel selectMember = memberDAO.selectMemberById(memberModel);
        String inputPassword = memberModel.getPassword();

        if (ObjectUtils.isEmpty(selectMember)) {
            // ????????? ?????? - ??????????????? ??????
            log.error("UserService : signIn INPUT - "+gson.toJson(memberModel));
            return ResultVo.builder().result("fail").msg("Fail Get User Data").build();
        }else if(!passwordEncoder.matches(inputPassword, selectMember.getPassword())){
            // ????????? ?????? - ???????????? ??????
            log.error("UserService : signIn GET selectMemberById - "+gson.toJson(selectMember));
            return ResultVo.builder().result("fail").msg("Fail Wrong Password").build();
        }

        JwtModel jwtModel = jwtTokenProvider.createToken(selectMember.getUid(), selectMember.getId());      //?????? ??????
        log.info("LoginController : jwtModel Token - "+gson.toJson(jwtModel));

        if (StringUtils.isNotBlank(jwtModel.getAccessToken())) {
            jwtTokenProvider.createCookie(response, jwtModel.getAccessToken());                             //?????? ?????? (AccessToken)
        }

        if (StringUtils.isNotBlank(jwtModel.getRefreshToken())) {
            memberModel = JwtMemberModel.builder()
                    .uid(selectMember.getUid())
                    .id(selectMember.getId())
                    .token(jwtModel.getRefreshToken())
                    .expiredDttm(jwtModel.getRefreshTokenExpirationDate())
                    .build();

            memberDAO.deleteMemberOauth(memberModel);                                                       //?????? refreshToken ?????? (DB) - ???????????? ??????
            memberDAO.insertMemberOauth(memberModel);                                                       //??? refreshToken ??????(DB)
        }

        return ResultVo.builder().result("success").msg("").build();
    }
}
