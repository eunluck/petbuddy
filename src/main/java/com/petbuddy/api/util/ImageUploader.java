package com.petbuddy.api.util;

import com.google.common.base.Strings;
import com.petbuddy.api.aws.S3Client;
import com.petbuddy.api.error.FileIoExtension;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.error.NotImageExtension;
import com.petbuddy.api.error.ServiceRuntimeException;
import com.petbuddy.api.model.commons.ImageExtension;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class ImageUploader {


    public static final String IMAGE_TYPE = "image";
    public static final String POST_IMAGE_DIR = "posts/images/";
    public static final String USER_IMAGE_DIR = "users/images/";
    public static final String ALGORITHM_NAME = "MD5";
    private final S3Client s3Client;

/*


    @Transactional
    public List<UserImage> uploadPetImages(List<MultipartFile> multipartFiles) {
        if (Objects.isNull(multipartFiles) || multipartFiles.isEmpty()) {
            return Collections.emptyList();
        }
        return multipartFiles.stream()
                .map(this::uploadPostImage)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostImage uploadPostImage(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile)) {
            return null;
        }
        User user = (User) authenticationFacade.getPrincipal();
        validateImage(multipartFile);
        String imageUrl = s3Uploader
                .upload(multipartFile, POST_IMAGE_DIR + calculateUserHashCode(user));
        return PostImage.builder()
                .name(multipartFile.getName())
                .url(imageUrl)
                .build();
    }


*/


    private void validateImage(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            throw new NotFoundException(MultipartFile.class,"첨부된 이미지가 없습니다.");
        }
        ImageExtension.validateImageExtension(multipartFile);
        validateImageMimeType(multipartFile);
    }

    private void validateImageMimeType(MultipartFile multipartFile) {
        try {
            if (isNotImageMimeType(multipartFile)) {
                throw new NotImageExtension(
                        multipartFile.getName() + " 파일은 이미지 MIME 타입이 아닙니다!");
            }
        } catch (IOException e) {
            throw new FileIoExtension(multipartFile.getOriginalFilename() + "의 바이트 파일을 가져오는데 실패했습니다!");
        }
    }

    private boolean isNotImageMimeType(MultipartFile multipartFile) throws IOException {
        String mimeType = URLConnection.guessContentTypeFromName(multipartFile.getOriginalFilename());

        return Strings.isNullOrEmpty(mimeType);
    }
}
