package com.example.gamePT.domain.user.service;

import com.example.gamePT.domain.image.service.ImageService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.repository.UserRepository;
import com.example.gamePT.domain.user.request.SiteUserRequest;
import com.example.gamePT.domain.user.response.SiteUserResponse;
import com.example.gamePT.global.email.EmailService;
import com.example.gamePT.global.riot.service.RiotApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RiotApiService riotApiService;

    private final ImageService imageService;

    public boolean update(SiteUserRequest.Update updateForm, BindingResult br, MultipartFile profileImg) throws IOException {

        if (br.hasErrors()) {
            return false;
        }

        SiteUser loginedUser = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // 가입된 닉네임이 있는 경우
        if (userRepository.existsByNickname(updateForm.getNickname())) {

            // 현재 내 닉네임과 다른데 아이디가 있는 경우
            if (!loginedUser.getNickname().equals(updateForm.getNickname())) {
                br.rejectValue("nickname", "unique violation", "가입되어 있는 nickname 입니다.");
                return false;
            }
        } else {
            loginedUser = loginedUser.toBuilder().nickname(updateForm.getNickname()).build();
            userRepository.save(loginedUser);
        }


        if (!updateForm.getPassword().equals("") || !updateForm.getPasswordConfirm().equals("")) {
            if (!updateForm.getPassword().equals(updateForm.getPasswordConfirm())) {
                br.rejectValue("passwordConfirm", "not matched", "passwordConfirm does not matched with password");
                return false;
            }

            loginedUser = loginedUser.toBuilder().password(passwordEncoder.encode(updateForm.getPassword())).build();

            userRepository.save(loginedUser);
        }


        if (!profileImg.isEmpty()) {
            imageService.updateUserProfile(loginedUser, profileImg);
        }

        return true;
    }

    public SiteUserResponse.AjaxRes findUserInfoAjax(SiteUserRequest.FindUserInfoAjax data) {

        String message = "";

        if (data.getType().isEmpty()) {
            return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("유효하지 않은 접근입니다.").build();
        }

        if (data.getType().equals("findUsername")) {
            if (data.getNickname().isEmpty() || data.getEmail().isEmpty()) {
                return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("유효하지 않은 접근입니다.").build();
            }

            Optional<SiteUser> siteUser = userRepository.findByNicknameAndEmail(data.getNickname(), data.getEmail());

            if (siteUser.isEmpty()) {
                return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("해당 정보의 회원이 없습니다.").build();
            }

            return SiteUserResponse.AjaxRes.builder().isSuccess(true).message("회원님의 아이디는 " + siteUser.get().getUsername() + " 입니다.").build();

        }

        if (data.getType().equals("findPassword")) {
            if (data.getUsername().isEmpty() || data.getEmail().isEmpty()) {
                return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("유효하지 않은 접근입니다.").build();
            }

            Optional<SiteUser> siteUser = userRepository.findByUsernameAndEmail(data.getUsername(), data.getEmail());

            if (siteUser.isEmpty()) {
                return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("해당 정보의 회원이 없습니다.").build();
            }

            // 메일 보내기
            String tempPassword = emailService.sendConfirm(siteUser.get().getEmail()) + "";

            // 비밀번호 변경
            SiteUser changePasswordUser = siteUser.get();

            changePasswordUser = changePasswordUser.toBuilder().password(passwordEncoder.encode(tempPassword)).build();

            this.userRepository.save(changePasswordUser);

            return SiteUserResponse.AjaxRes.builder().isSuccess(true).message("회원님의 Email로 임시 비밀번호를 발송 하였습니다.").build();
        }

        return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("유효하지 않은 접근입니다.").build();
    }

    public SiteUserResponse.AjaxRes isUnique(SiteUserRequest.IsUniqueAjax isUniqueAjax, BindingResult br) {

        if (br.hasErrors()) {
            return SiteUserResponse.AjaxRes.builder().isSuccess(false).message(br.getAllErrors().get(0).getDefaultMessage()).build();
        }

        String[] strArray = {"username", "email", "nickname", "gameName-tag"};

        if (!Arrays.stream(strArray).anyMatch(isUniqueAjax.getName()::equals)) {
            return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("잘못된 요청 입니다.").build();
        }

        if (isUniqueAjax.getName().equals("username") && this.userRepository.existsByUsername(isUniqueAjax.getValue())) {
            return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("이미 가입 되어 있는 아이디 입니다.").build();
        } else if (isUniqueAjax.getName().equals("email") && this.userRepository.existsByEmail(isUniqueAjax.getValue())) {
            return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("이미 가입 되어 있는 이메일 입니다.").build();
        } else if (isUniqueAjax.getName().equals("nickname") && this.userRepository.existsByNickname(isUniqueAjax.getValue())) {
            return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("이미 가입 되어 있는 닉네임 입니다.").build();
        } else if (isUniqueAjax.getName().equals("gameName-tag")) {
            String[] gameName_tagList = isUniqueAjax.getValue().split("라이엇");

            String puuid = riotApiService.getPuuid(gameName_tagList[0], gameName_tagList[1]);

            if (puuid == null) {
                return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("존재하지 않는 소환사입니다.").build();
            }
            if (this.userRepository.existsByPuuid(puuid)) {
                return SiteUserResponse.AjaxRes.builder().isSuccess(false).message("이미 가입되어 있는 소환사입니다.").build();
            }
        }

        return SiteUserResponse.AjaxRes.builder().isSuccess(true).message(isUniqueAjax.getValue() + "은 사용 가능 합니다.").build();
    }

    public boolean signUp(SiteUserRequest.Signup signup, BindingResult br, MultipartFile profileImg) throws IOException {

        String puuid = riotApiService.getPuuid(signup.getGameName(), signup.getTag());

        if (!this.signupValidate(signup, br, puuid)) return false;

        SiteUser signUp = SiteUser.builder()
                .username(signup.getUsername())
                .password(passwordEncoder.encode(signup.getPassword()))
                .email(signup.getEmail())
                .nickname(signup.getNickname())
                .gameName(signup.getGameName())
                .tag(signup.getTag())
                .puuid(puuid)
                .build();

        //admin 권한
        if (signup.getUsername().equals("admin")) {
            signUp = signUp.toBuilder()
                    .authorization("Admin")
                    .build();
        }

        SiteUser siteUser = userRepository.save(signUp);
        this.imageService.saveUserProfile(siteUser, profileImg);

        return true;
    }

    private boolean signupValidate(SiteUserRequest.Signup signup, BindingResult br, String puuid) {

        if (br.hasErrors()) {
            return false;
        }

        if (puuid == null) {

            br.rejectValue("non-existent summoner", "non-existent summoner", "존재하지 않는 소환사 입니다.");

            return false;
        }

        if (this.userRepository.existsByPuuid(puuid)) {

            br.rejectValue("puuid", "unique violation", "가입 되어 있는 소환사 입니다.");

            return false;
        }

        if (!signup.getPassword().equals(signup.getPasswordConfirm())) {

            br.rejectValue("passwordConfirm", "not matched", "passwordConfirm does not matched with password");

            return false;
        }

        if (this.userRepository.existsByUsername(signup.getUsername())) {

            br.rejectValue("username", "unique violation", "가입 되어 있는 아이디 입니다.");

            return false;
        }

        if (this.userRepository.existsByEmail(signup.getEmail())) {

            br.rejectValue("email", "unique violation", "가입되어 있는 email 입니다.");

            return false;
        }

        if (this.userRepository.existsByNickname(signup.getNickname())) {

            br.rejectValue("nickname", "unique violation", "가입되어 있는 nickname 입니다.");

            return false;
        }

        return true;
    }


    public SiteUser findByUsername(String username) {
        return this.userRepository.findByUsername(username).get();
    }

    public SiteUser findByUserId(Long id) {
        Optional<SiteUser> _user = this.userRepository.findById(id);
        if (_user.isEmpty()) {
            return null;
        }
        return _user.get();
    }

    public String getProfileImg(Long id) {
        return imageService.getSiteUserImg(id);
    }

    public void approveExpert(SiteUser siteUser, String authorization) {
        SiteUser changeUser = siteUser.toBuilder()
                .authorization(authorization)
                .build();
        this.userRepository.save(changeUser);
    }

    public void save(SiteUser siteUser) {
        this.userRepository.save(siteUser);
    }


}
