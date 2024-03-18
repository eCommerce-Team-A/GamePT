//package com.example.gamePT.global.util;
//
//import com.example.gamePT.domain.course.entity.Course;
//import com.example.gamePT.domain.course.repository.CourseRepository;
//import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
//import com.example.gamePT.domain.duoArticle.repository.DuoArticleRepository;
//import com.example.gamePT.domain.expert.entity.Expert;
//import com.example.gamePT.domain.expert.repository.ExpertRepository;
//import com.example.gamePT.domain.image.service.ImageService;
//import com.example.gamePT.domain.qna.entity.QnA;
//import com.example.gamePT.domain.qna.repository.QnARepository;
//import com.example.gamePT.domain.user.entity.SiteUser;
//import com.example.gamePT.domain.user.repository.UserRepository;
//import com.example.gamePT.global.riot.entity.LeagueDTO;
//import com.example.gamePT.global.riot.entity.SummonerDTO;
//import com.example.gamePT.global.riot.service.RiotApiService;
//import jakarta.validation.constraints.Null;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Component
//@RequiredArgsConstructor
//public class ServiceInit implements InitializingBean {
//
//    private final PasswordEncoder passwordEncoder;
//    private final CourseRepository courseRepository;
//    private final UserRepository userRepository;
//    private final ImageService imageService;
//    private final RiotApiService riotApiService;
//    private final QnARepository qnARepository;
//    private final DuoArticleRepository duoArticleRepository;
//    private final ExpertRepository expertRepository;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        init(); //메소드 최초 호출시키기
//    }
//
//    public void init() throws IOException {
//
//        String pid = riotApiService.getPuuid("기가막힌플레이","KR1");
//
//        SiteUser su = SiteUser.builder().
//                username("admin").
//                nickname("admin").
//                password(passwordEncoder.encode("123")).
//                email("admin@gmail.com").
//                gameName("기가막힌플레이").
//                tag("KR1").
//                authorization("Admin").
//                puuid(pid).
//                point(1000000).
//                build();
//        su = userRepository.save(su);//자신이 호출하고싶은 메소드
//        imageService.saveUserProfile(su, getMultipartFile());
//
//        String[] riotData = {"닉넴할게읍서 KR1",
//                "동동쓰 123",
//                "망치를받아라 6335",
//                "오학철 IVE",
//                "서리얼 KR1",
//                "쌔게때리기 KR1",
//                "나는너너는나 6974",
//                "레드포션 KR1",
//                "도옴앙챠 KR1",
//                "항개만줘쓰라 KR1",
//                "롤체홍선생 KR1",
//                "손희원 KR1",
//                "가드라 KR1",
//                "Meloey KR1",
//                "경안이 KR1",
//                "류지한 KR1",
//                "청년우진 KR1",
//                "고양이 춘식이",
//                "후랭이 KR1",
//                "쭈니대박 KR1"};
//        String[] lineList = {"fill","top","jungle","middle","bottom","utility"};
//
//        int k = 0;
//
//        for(int i = 0 ; i<10; i++){
//
//            String pid2 = riotApiService.getPuuid(riotData[i].split(" ")[0],riotData[i].split(" ")[1]);
//
//            SiteUser su2 = SiteUser.builder().
//                    username("seller"+i).
//                    nickname(riotData[i].split(" ")[0]).
//                    password(passwordEncoder.encode("123")).
//                    email("seller"+i+"@gmail.com").
//                    gameName(riotData[i].split(" ")[0]).
//                    tag(riotData[i].split(" ")[1]).
//                    authorization("Expert").
//                    puuid(pid2).
//                    point(1000000).
//                    build();
//            su2 = userRepository.save(su2);//자신이 호출하고싶은 메소드
//            imageService.saveUserProfile(su2, getMultipartFile());
//
//            Course cu = Course.builder().
//                    author(su2).
//                    name(su2.getNickname() + "의 기가막힌 강의").
//                    introduce(su2.getNickname() + "강의의 예시 데이터에요. 같이 잘 해보아요!").
//                    curriculum(su2.getNickname() + "의 강의는 고객이 만족할때까지 쉬지 않고 계속해요").
//                    price((int)((Math.random() * 899) + 100)*100).
//                    isActive(true).
//                    build();
//
//            courseRepository.save(cu);
//
//            Expert expert = Expert.builder()
//                    .siteUserId(su2.getId())
//                    .introduce("전문가 " + su2.getNickname() + "의 소개글 이에요~")
//                    .build();
//
//            expertRepository.save(expert);
//
//            SummonerDTO summonerDTO = this.riotApiService.getSummoner(su2.getPuuid());
//            LeagueDTO leagueDTO = this.riotApiService.getLeague(summonerDTO.getId());
//
//            if(k+1 > 5){
//                k = 0;
//            }
//
//            DuoArticle duoArticle = DuoArticle.builder()
//                    .myLine(lineList[k])
//                    .findLine(lineList[k+1])
//                    .microphoneCheck(i%2 == 0)
//                    .username(su2.getUsername())
//                    .puuid(su2.getPuuid())
//                    .content(su2.getNickname()+"이랑 게임해요")
//                    .gameName(su2.getGameName())
//                    .tag(su2.getTag())
//                    .profileIconId(summonerDTO.getProfileIconId())
//                    .tier(leagueDTO.getTier())
//                    .rank(leagueDTO.getTier().charAt(0) + "" + this.getNum(leagueDTO.getRank()))
//                    .wins(leagueDTO.getWins())
//                    .losses(leagueDTO.getLosses())
//                    .build();
//            duoArticleRepository.save(duoArticle);
//            k++;
//        }
//
//        for(int i = 10 ; i<20; i++){
//
//            String pid2 = riotApiService.getPuuid(riotData[i].split(" ")[0],riotData[i].split(" ")[1]);
//
//            SiteUser su2 = SiteUser.builder().
//                    username("user"+i).
//                    nickname(riotData[i].split(" ")[0]).
//                    password(passwordEncoder.encode("123")).
//                    email("seller"+i+"@gmail.com").
//                    gameName(riotData[i].split(" ")[0]).
//                    tag(riotData[i].split(" ")[1]).
//                    authorization("Member").
//                    puuid(pid2).
//                    point(1000000).
//                    build();
//
//            su2 = userRepository.save(su2);//자신이 호출하고싶은 메소드
//            imageService.saveUserProfile(su2, getMultipartFile());
//            QnA qna = new QnA();
//            if(i%2 == 0){
//                qna = QnA.builder().
//                        author(su2).
//                        title(su2.getNickname() + "의 문의문의").
//                        content(su2.getNickname() + "의 나문희 내용 이에요~").
//                        isBlind(false).
//                        isAnswered(false).
//                        build();
//            }else{
//                qna = QnA.builder().
//                        author(su2).
//                        title(su2.getNickname() + "의 문의문의").
//                        content(su2.getNickname() + "의 나문희 내용 이에요~").
//                        isBlind(true).
//                        isAnswered(false).
//                        build();
//            }
//            qnARepository.save(qna);
//
//            SummonerDTO summonerDTO = this.riotApiService.getSummoner(su2.getPuuid());
//            LeagueDTO leagueDTO = this.riotApiService.getLeague(summonerDTO.getId());
//
//            if(k+1 > 5){
//                k = 0;
//            }
//
//            DuoArticle duoArticle = DuoArticle.builder()
//                    .myLine(lineList[k])
//                    .findLine(lineList[k+1])
//                    .microphoneCheck(i%2 == 0)
//                    .username(su2.getUsername())
//                    .puuid(su2.getPuuid())
//                    .content(su2.getNickname()+"이랑 게임해요")
//                    .gameName(su2.getGameName())
//                    .tag(su2.getTag())
//                    .profileIconId(summonerDTO.getProfileIconId())
//                    .tier(leagueDTO.getTier())
//                    .rank(leagueDTO.getTier().charAt(0) + "" + this.getNum(leagueDTO.getRank()))
//                    .wins(leagueDTO.getWins())
//                    .losses(leagueDTO.getLosses())
//                    .build();
//
//            duoArticleRepository.save(duoArticle);
//
//            k++;
//        }
//
//    }
//    public Integer getNum(String rank) {
//        return switch (rank) {
//            case "I" -> 1;
//            case "II" -> 2;
//            case "III" -> 3;
//            case "IV" -> 4;
//            default -> null;
//        };
//    }
//
//
//    public MultipartFile getMultipartFile(){
//        return new MultipartFile() {
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public String getOriginalFilename() {
//                return null;
//            }
//
//            @Override
//            public String getContentType() {
//                return null;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return true;
//            }
//
//            @Override
//            public long getSize() {
//                return 0;
//            }
//
//            @Override
//            public byte[] getBytes() throws IOException {
//                return new byte[0];
//            }
//
//            @Override
//            public InputStream getInputStream() throws IOException {
//                return null;
//            }
//
//            @Override
//            public void transferTo(File dest) throws IOException, IllegalStateException {
//
//            }
//        };
//    }
//}