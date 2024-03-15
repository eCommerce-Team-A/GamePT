package com.example.gamePT.domain.image.service;

import com.example.gamePT.domain.expertRequest.entity.ExpertRequest;
import com.example.gamePT.domain.image.entity.Image;
import com.example.gamePT.domain.image.repository.ImageRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public void updateUserProfile(SiteUser loginedUser, MultipartFile profileImg) throws IOException {

        Image image = findByUser(loginedUser);

        String originalFileName = profileImg.getOriginalFilename();
        String filePath = "user/" + UUID.randomUUID().toString() + "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        File profileImgFile = new File(fileDirPath + "/" + filePath);
        profileImg.transferTo(profileImgFile);
        filePath = "/file/" + filePath;

        image = image.toBuilder().path(filePath).originalFileName(originalFileName).relationId(loginedUser.getId()).build();

        imageRepository.save(image);
    }

    public Image findByUser(SiteUser siteUser) {

        return imageRepository.findByRelationEntityAndRelationId("siteUser", siteUser.getId()).get();
    }

    public void saveUserProfile(SiteUser siteUser, MultipartFile profileImg) throws IOException {

        String filePath = "";
        String originalFileName = "";

        if (profileImg.isEmpty()) {
            filePath = "/img/kakako-00.jpg";
            originalFileName = "defaultImage";
        } else if (!profileImg.isEmpty()) {

            createFolder("user");

            originalFileName = profileImg.getOriginalFilename();
            filePath = "user/" + UUID.randomUUID().toString() + "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            File profileImgFile = new File(fileDirPath + "/" + filePath);
            profileImg.transferTo(profileImgFile);
            filePath = "/file/" + filePath;
        }

        Image userProfile = Image.builder().path(filePath).originalFileName(originalFileName).relationId(siteUser.getId()).relationEntity("siteUser").build();

        this.imageRepository.save(userProfile);
    }

    public void createFolder(String folderName) {

        File folder = new File(fileDirPath + "\\" + folderName + "\\");

        // 해당 디렉토리가 없다면 디렉토리를 생성.
        if (!folder.exists()) {
            try {
                folder.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    public String getSiteUserImg(Long id) {
        Optional<Image> profileImg = imageRepository.findByRelationEntityAndRelationId("siteUser", id);

        if (profileImg.isEmpty()) return null;

        return profileImg.get().getPath();
    }

    public void saveRequestProfile(ExpertRequest expertRequest, MultipartFile requestImg) throws IOException {

        this.createFolder("expert_request");
        String originalFileName = requestImg.getOriginalFilename();
        String path = "expert_request/" + UUID.randomUUID().toString() + "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        File requestImgFile = new File(fileDirPath + "/" + path);
        requestImg.transferTo(requestImgFile);
        path = "/file/" + path;

        Image requestProfile = Image.builder()
                .path(path)
                .originalFileName(originalFileName)
                .relationId(expertRequest.getId())
                .relationEntity("expertRequest")
                .build();

        this.imageRepository.save(requestProfile);
    }

    public String getRequestImg(Long id) {
        Optional<Image> profileImg = imageRepository.findByRelationEntityAndRelationId("expertRequest", id);

        if (profileImg.isEmpty()) return null;

        return profileImg.get().getPath();
    }

}
