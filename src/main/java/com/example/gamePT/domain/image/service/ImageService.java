package com.example.gamePT.domain.image.service;

import com.example.gamePT.domain.image.entity.Image;
import com.example.gamePT.domain.image.repository.ImageRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public void saveUserProfile(SiteUser siteUser, MultipartFile profileImg) throws IOException {

        String filePath = "";
        String originalFileName = "";

        if (profileImg.isEmpty()) {
            filePath = "/img/kakako-00.jpg";
            originalFileName = "defaultImage";
        } else if (!profileImg.isEmpty()) {

            createFolder("user");

            originalFileName = profileImg.getOriginalFilename();
            filePath = "user/" + UUID.randomUUID().toString() +"."+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            File profileImgFile = new File(fileDirPath +"/"+ filePath);
            profileImg.transferTo(profileImgFile);
        }

        Image userProfile = Image.builder().path(filePath).originalFileName(originalFileName).relationId(siteUser.getId()).relationEntity("siteUser").build();

        this.imageRepository.save(userProfile);
    }

    public void createFolder(String folderName){

        File folder = new File(fileDirPath+"\\"+folderName+"\\");

        // 해당 디렉토리가 없다면 디렉토리를 생성.
        if (!folder.exists()) {
            try{
                folder.mkdirs();
            }catch(Exception e){
                e.getStackTrace();
            }
        }
    }

}
