package com.petbuddy.api.model.commons;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.*;

@Getter
public class AttachedFile {

  private final String originalFileName;

  private final String contentType;

  private final byte[] bytes;

  public AttachedFile( String originalFileName, String contentType, byte[] bytes) {

    this.originalFileName = originalFileName;
    this.contentType = contentType;
    this.bytes = bytes;
  }

  private static boolean verify(MultipartFile multipartFile) {
    if (multipartFile != null && multipartFile.getSize() > 0 && multipartFile.getOriginalFilename() != null) {
      String contentType = multipartFile.getContentType();
      // 첨부파일 타입을 확인하고 이미지인 경우 처리
      if (isNotEmpty(contentType) && contentType.toLowerCase().startsWith("image"))
        return true;
    }
    return false;
  }




  public static List<AttachedFile> toAttachedFile(List<MultipartFile> multipartFiles) {


    return multipartFiles.stream().map(file -> toAttachedFile(file).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
  }





  public static Optional<AttachedFile> toAttachedFile(MultipartFile multipartFile) {
    try {
      return ofNullable(
        verify(multipartFile)
          ? new AttachedFile(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getBytes())
          : null
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String extension(String defaultExtension) {
    return defaultIfEmpty(getExtension(originalFileName), defaultExtension);
  }

  public String randomName(String defaultExtension) {
    return randomName(null, defaultExtension);
  }

  public String randomName(String basePath, String defaultExtension) {
    String name = isEmpty(basePath) ? UUID.randomUUID().toString() : basePath + "/" + UUID.randomUUID().toString();
    return name + "." + extension(defaultExtension);
  }

  public InputStream inputStream() {
    return new ByteArrayInputStream(bytes);
  }

  public long length() {
    return bytes.length;
  }

  public String getContentType() {
    return contentType;
  }


  public String generatePetFileName(String key){
    return key
            + "/"
            + UUID.randomUUID().toString()
            + "_"
            + originalFileName;

  }


}