package com.example.gamePT.domain.user.service;

import com.example.gamePT.domain.image.service.ImageService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.repository.UserRepository;
import com.example.gamePT.domain.user.request.SiteUserRequest;
import com.example.gamePT.domain.user.response.SiteUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;

    public SiteUserResponse.IsUnique isUnique(SiteUserRequest.IsUniqueAjax isUniqueAjax, BindingResult br) {

        if(br.hasErrors()){
            return SiteUserResponse.IsUnique.builder().isSuccess(false).message(br.getAllErrors().get(0).getDefaultMessage()).build();
        }

        String[] strArray = {"username", "email", "nickname"};

        if(!Arrays.stream(strArray).anyMatch(isUniqueAjax.getName()::equals)) {
            return SiteUserResponse.IsUnique.builder().isSuccess(false).message("잘못된 요청 입니다.").build();
        }

        if(isUniqueAjax.getName().equals("username") && this.userRepository.existsByUsername(isUniqueAjax.getValue())){
            return SiteUserResponse.IsUnique.builder().isSuccess(false).message("이미 가입 되어 있는 아이디 입니다.").build();
        }else if(isUniqueAjax.getName().equals("email") && this.userRepository.existsByEmail(isUniqueAjax.getValue())){
            return SiteUserResponse.IsUnique.builder().isSuccess(false).message("이미 가입 되어 있는 이메일 입니다.").build();
        }
        else if(isUniqueAjax.getName().equals("nickname") && this.userRepository.existsByNickname(isUniqueAjax.getValue())){
            return SiteUserResponse.IsUnique.builder().isSuccess(false).message("이미 가입 되어 있는 닉네임 입니다.").build();
        }

        return SiteUserResponse.IsUnique.builder().isSuccess(true).message(isUniqueAjax.getValue()+"은 사용 가능 합니다.").build();
    }

    public boolean signUp(SiteUserRequest.Signup signup, BindingResult br, MultipartFile profileImg) throws IOException {

        if(!this.signupValidate(signup,br)) return false;

        SiteUser signUp = SiteUser.builder()
                .username(signup.getUsername())
                .password(passwordEncoder.encode(signup.getPassword()))
                .email(signup.getEmail())
                .nickname(signup.getNickname())
                .build();

        SiteUser siteUser = userRepository.save(signUp);

        this.imageService.saveUserProfile(siteUser, profileImg);

        return true;
    }

    private boolean signupValidate(SiteUserRequest.Signup signup, BindingResult br){

        if(br.hasErrors()){
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

        if (this.userRepository.existsByEmail(signup.getNickname())) {

            br.rejectValue("nickname", "unique violation", "가입되어 있는 nickname 입니다.");

            return false;
        }

        return true;
    }
    
}
