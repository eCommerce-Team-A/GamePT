package com.example.gamePT.domain.duoArticle.controller;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import com.example.gamePT.global.riot.entity.LeagueDTO;
import com.example.gamePT.global.riot.entity.SummonerDTO;
import com.example.gamePT.global.riot.service.RiotApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/duo")
public class DuoArticleController {
    private final DuoArticleService duoArticleService;
    private final UserService userService;
    private final RiotApiService riotApiService;

    @GetMapping("/list")
    public String list(Model model) throws IOException {
        List<DuoArticle> duoArticleList = this.duoArticleService.getAllDuoArticles();
        model.addAttribute("duoArticleList", duoArticleList);
        return "duo/list";
    }

    @PostMapping("/create")
    public String create(@RequestParam("myLine") String myLine, @RequestParam("findLine") String findLine,
                         @RequestParam(name = "microphoneCheck", defaultValue = "false") boolean microphoneCheck,
                         @RequestParam("content") String content, Principal principal) {
        SiteUser siteUser = this.userService.findByUsername(principal.getName());
        SummonerDTO summonerDTO = this.riotApiService.getSummoner(siteUser.getPuuid());
        String summonerId = summonerDTO.getId();
        String tier = "UNRANKED";
        String rank = "UR";
        int wins = 0;
        int losses = 0;
        if (this.riotApiService.getLeague(summonerId) != null) {
            LeagueDTO leagueDTO = this.riotApiService.getLeague(summonerId);
            tier = leagueDTO.getTier();
            rank = tier.charAt(0) + "" + this.getNum(leagueDTO.getRank());
            wins = leagueDTO.getWins();
            losses = leagueDTO.getLosses();
        }
//        for (String matchId : this.riotApiService.getMatchIds(siteUser.getPuuid())) {
//            this.riotApiService.getMatchInfo(matchId).getInfo().getParticipants();
//        }
        this.duoArticleService.createDuoArticle(myLine, findLine, microphoneCheck, content, siteUser.getUsername(),
                siteUser.getPuuid(), siteUser.getGameName(), siteUser.getTag(), summonerDTO.getProfileIconId(), tier, rank, wins, losses);
        return "redirect:/duo/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        DuoArticle duoArticle = this.duoArticleService.getDuoArticleById(id);
        this.duoArticleService.deleteDuoArticle(duoArticle);
        return "redirect:/duo/list";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,@RequestParam("myLine") String myLine, @RequestParam("findLine") String findLine,
                         @RequestParam(name = "microphoneCheck", defaultValue = "false") boolean microphoneCheck,
                         @RequestParam("content") String content) {
        DuoArticle duoArticle = this.duoArticleService.getDuoArticleById(id);
        this.duoArticleService.modifyDuoArticle(duoArticle, myLine, findLine, microphoneCheck, content);
        return "redirect:/duo/list";
    }


    public Integer getNum(String rank) {
        return switch (rank) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            default -> null;
        };
    }


}
