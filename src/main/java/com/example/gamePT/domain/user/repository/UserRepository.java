package com.example.gamePT.domain.user.repository;

import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {


    Optional<SiteUser> findByUsername(String loginId);

    Optional<SiteUser> findByEmail(String mail);

    boolean existsByUsername(String loginId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<SiteUser> findByNicknameAndEmail(String nickname, String email);

    Optional<SiteUser> findByUsernameAndEmail(String username, String email);

}
