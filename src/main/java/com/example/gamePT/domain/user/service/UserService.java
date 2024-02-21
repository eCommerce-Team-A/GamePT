package com.example.gamePT.domain.user.service;

import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.repository.UserRepository;
import com.example.gamePT.domain.user.request.SiteUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean signUp(SiteUserRequest.Signup signup, BindingResult br) {

        if(this.signupValidate(signup,br));

        SiteUser signUp = SiteUser.builder()
                .username(signup.getUsername())
                .password(passwordEncoder.encode(signup.getPassword()))
                .email(signup.getEmail())
                .nickname(signup.getNickname())
                .build();

        userRepository.save(signUp);

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

            br.rejectValue("loginId", "unique violation", "loginId unique violation");

            return false;
        }

        if (this.userRepository.existsByEmail(signup.getEmail())) {

            br.rejectValue("loginId", "unique violation", "loginId unique violation");

            return false;
        }

        return true;
    }
    
}
