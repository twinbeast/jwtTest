package com.example.jwttest.Controller;

import com.example.jwttest.Service.UserService;
import com.example.jwttest.Service.jwt.JwtLoginService;
import com.example.jwttest.Vo.Jwt.JwtModel;
import com.example.jwttest.Security.jwt.JwtTokenProvider;
import com.example.jwttest.Dao.JwtAuthDao;
import com.example.jwttest.Vo.Jwt.JwtMemberModel;
import com.example.jwttest.Vo.PatientUserVo;
import com.example.jwttest.Vo.ResultVo;
import com.google.gson.Gson;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private JwtAuthDao memberDAO;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    JwtLoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    Gson gson;

    @ResponseBody
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@RequestBody PatientUserVo signUpData){
        log.info("/signUp: "+gson.toJson(signUpData));
        boolean duplicateChk = userService.checkDuplicate(signUpData.getPatientId());
        if(duplicateChk==false)
            return gson.toJson(ResultVo.builder().result("fail").msg("Duplicated").build());

        return gson.toJson(userService.signUp(signUpData));
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String selectMemberByIdAndPassword(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestBody JwtMemberModel memberModel) {
        log.info("LoginController : selectMemberByIdAndPassword - "+gson.toJson(memberModel));
        JwtMemberModel selectMember = memberDAO.selectMemberById(memberModel);
        log.info("LoginController : selectMemberByIdAndPassword - "+gson.toJson(selectMember));
        String inputPassword = memberModel.getPassword();
        log.info(inputPassword+" encode : "+passwordEncoder.encode(inputPassword));

        if (!ObjectUtils.isEmpty(selectMember)) {
            if (passwordEncoder.matches(inputPassword, selectMember.getPassword())) {
                log.info("LoginController : selectMember - "+gson.toJson(selectMember));
                JwtModel jwtModel = jwtTokenProvider.createToken(selectMember.getUid(), selectMember.getId());
                log.info("LoginController : jwtModel - "+gson.toJson(jwtModel));

                if (StringUtils.isNotBlank(jwtModel.getAccessToken())) {
                    jwtTokenProvider.createCookie(response, jwtModel.getAccessToken());
                }

                if (StringUtils.isNotBlank(jwtModel.getRefreshToken())) {
                    memberModel = JwtMemberModel.builder()
                            .uid(selectMember.getUid())
                            .id(selectMember.getId())
                            .token(jwtModel.getRefreshToken())
                            .expiredDttm(jwtModel.getRefreshTokenExpirationDate())
                            .build();

                    memberDAO.deleteMemberOauth(memberModel);
                    memberDAO.insertMemberOauth(memberModel);
                }
            } else {
                // 로그인 실패
                return gson.toJson(ResultVo.builder().result("fail").msg("fail_1").build());
            }
        } else {
            // 로그인 실패
            return gson.toJson(ResultVo.builder().result("fail").msg("fail_2").build());
        }

        return gson.toJson(ResultVo.builder().result("success").msg("").build());
    }

    @ResponseBody
    @RequestMapping(value = "/loginTest", method = RequestMethod.POST)
    public String loginTest(HttpServletRequest request){

        return gson.toJson(ResultVo.builder().result("fail").msg("loginTest Fail").build());
    }
}
