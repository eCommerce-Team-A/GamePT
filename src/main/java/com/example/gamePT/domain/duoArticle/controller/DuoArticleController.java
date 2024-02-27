package com.example.gamePT.domain.duoArticle.controller;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
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
        String summonerId = this.riotApiService.getSummoner(siteUser.getPuuid()).getId();
        String gameName = this.riotApiService.getSummoner(siteUser.getPuuid()).getName();
        String tier = "";
        int wins = 0;
        int losses = 0;
        if (this.riotApiService.getLeague(summonerId) == null) {
            tier = "unranked";
        } else {
            tier = this.riotApiService.getLeague(summonerId).getTier() + this.riotApiService.getLeague(summonerId).getRank();
            wins = this.riotApiService.getLeague(summonerId).getWins();
            losses = this.riotApiService.getLeague(summonerId).getLosses();
        }
        double avgKills = 0;
        double avgDeaths = 0;
        double avgAssists = 0;
        for (String matchId : this.riotApiService.getMatchIds(siteUser.getPuuid())) {
            avgKills += this.riotApiService.getParticipant(this.riotApiService.getMatchInfo(matchId), siteUser.getPuuid()).getKills();
            avgDeaths += this.riotApiService.getParticipant(this.riotApiService.getMatchInfo(matchId), siteUser.getPuuid()).getDeaths();
            avgAssists += this.riotApiService.getParticipant(this.riotApiService.getMatchInfo(matchId), siteUser.getPuuid()).getAssists();
        }
        this.duoArticleService.createDuoArticle(myLine, findLine, microphoneCheck, content, gameName, tier, wins, losses, avgKills, avgDeaths, avgAssists);
        return "redirect:/duo/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        DuoArticle duoArticle = this.duoArticleService.getDuoArticleById(id);
        this.duoArticleService.deleteDuoArticle(duoArticle);
        return "redirect:/duo/list";
    }

    @GetMapping("/api/create")
    public String apiCreate() {
        return "duo/api_test_form";
    }


}
