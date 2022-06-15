package com.petbuddy.api.util;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.petbuddy.api.aws.S3Client;
import com.petbuddy.api.error.NotFoundAlgorithmException;
import com.petbuddy.api.model.commons.AttachedFile;
import com.petbuddy.api.model.pet.PetImage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
@Slf4j
public class ImageUploader {


    public static final String PET_IMAGE_DIR = "pets";
    public static final String USER_IMAGE_DIR = "profiles";
    public static final String ALGORITHM_NAME = "MD5";
    private final S3Client s3Client;


    @Transactional
    public Optional<String> uploadProfileImage(AttachedFile profileFile) {
        String profileImageUrl = null;
        if (profileFile != null) {
            String key = profileFile.randomName(USER_IMAGE_DIR, "jpeg");
            try {
                profileImageUrl = s3Client.upload(profileFile.inputStream(), profileFile.length(), key, profileFile.getContentType(), null);
            } catch (AmazonS3Exception e) {
                log.warn("Amazon S3 error (key: {}): {}", key, e.getMessage(), e);
            }
        }
        return ofNullable(profileImageUrl);
    }

    public List<PetImage> uploadPetImages(Long userId, List<AttachedFile> attachedFiles) {

        return attachedFiles.stream()
                .map(attachedFile ->
                        uploadPetImage(userId,attachedFile))
                .collect(Collectors.toList());
    }

    @Transactional
    public PetImage uploadPetImage(Long userId, AttachedFile multipartFile) {
        if (Objects.isNull(multipartFile)) {
            return null;
        }
        String imageUrl = s3Client
                .upload(multipartFile.inputStream(),multipartFile.length(), multipartFile.generatePetFileName(PET_IMAGE_DIR +"/"+calculateUserHashCode(userId)),multipartFile.getContentType(),null);
        return PetImage.builder()
                .name(multipartFile.getOriginalFileName())
                .url(imageUrl)
                .build();
    }

    private String calculateUserHashCode(Long userId) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_NAME);
            final byte[] bytes = String.valueOf(userId).getBytes();
            Objects.requireNonNull(messageDigest).update(bytes);
            byte[] digest = messageDigest.digest();
            return DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new NotFoundAlgorithmException(ALGORITHM_NAME);
        }
    }


/*
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
                throw new NotImageExtension(multipartFile.getName());
            }
        } catch (IOException e) {
            throw new FileIoExtension(multipartFile.getOriginalFilename() + "의 바이트 파일을 가져오는데 실패했습니다!");
        }
    }

    private boolean isNotImageMimeType(MultipartFile multipartFile) throws IOException {
        String mimeType = URLConnection.guessContentTypeFromName(multipartFile.getOriginalFilename());

        return Strings.isNullOrEmpty(mimeType);
    }*/
}
