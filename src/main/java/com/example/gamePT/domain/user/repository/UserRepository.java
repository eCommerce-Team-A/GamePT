package com.example.gamePT.domain.user.repository;

import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    boolean existsByPuuid(String puuid);

    Page<SiteUser> findByAuthorization(String authorization, Pageable pageable);

    @Query("select "
            + "distinct s "
            + "from SiteUser s "
            + "right join Expert e on s.id=e.siteUserId "
            + "left outer join Career c on c.expert = e "
            + "where "
            + "   s.nickname like %:kw% "
            + "   or e.introduce like %:kw% "
            + "   or c.content like %:kw% "
            + "   or c.category like %:kw% "
            + "order by e.createDate DESC")
    Page<SiteUser> findByAuthorizationAndKwCreateDateDesc(@Param("kw") String kw, Pageable pageable);

    @Query("select "
            + "distinct s "
            + "from SiteUser s "
            + "right join Expert e on s.id=e.siteUserId "
            + "left outer join Career c on c.expert = e "
            + "where "
            + "   s.nickname like %:kw% "
            + "   or e.introduce like %:kw% "
            + "   or c.content like %:kw% "
            + "   or c.category like %:kw% "
            + "order by e.createDate ASC")
    Page<SiteUser> findByAuthorizationAndKwCreateDateAsc(@Param("kw") String kw, Pageable pageable);

    @Query("select "
            + "distinct s "
            + "from SiteUser s "
            + "right join Expert e on s.id=e.siteUserId "
            + "left outer join Career c on c.expert = e "
            + "left outer join Course co on co.author = s "
            + "left outer join Review r on r.course = co "
            + "where "
            + "   s.nickname like %:kw% "
            + "   or e.introduce like %:kw% "
            + "   or c.content like %:kw% "
            + "   or c.category like %:kw% "
            + "group by s.id "
            + "order by count(r.id) desc ")
    Page<SiteUser> findByAuthorizationAndKwOrderByReviewCount(@Param("kw") String kw, Pageable pageable);

    @Query("select "
            + "distinct s "
            + "from SiteUser s "
            + "right join Expert e on s.id=e.siteUserId "
            + "left outer join Career c on c.expert = e "
            + "left outer join Course co on co.author = s "
            + "left outer join Review r on r.course = co "
            + "where "
            + "   s.nickname like %:kw% "
            + "   or e.introduce like %:kw% "
            + "   or c.content like %:kw% "
            + "   or c.category like %:kw% "
            + "group by s.id "
            + "order by avg(r.score) desc ")
    Page<SiteUser> findByAuthorizationAndKwOrderByReviewGrade(@Param("kw") String kw, Pageable pageable);

    List<SiteUser> findTop5ByAuthorizationOrderByCreateDateDesc(String authorization);
}
