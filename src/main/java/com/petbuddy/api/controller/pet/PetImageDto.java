package com.petbuddy.api.controller.pet;

import com.petbuddy.api.model.commons.BaseEntity;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.PetImage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class PetImageDto  {

    @ApiModelProperty(value = "이미지 ID", required = true)
    private Long id;
    @ApiModelProperty(value = "파일이름", required = true)
    private String name;
    @ApiModelProperty(value = "이미지 url", required = true)
    private String url;
    @ApiModelProperty(value = "생성일시", required = true)
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "수정일시", required = true)
    private LocalDateTime updatedAt;

    public static PetImageDto of(PetImage petImage) {
        return new PetImageDto(petImage.getId(),
                petImage.getName(),
                petImage.getUrl(),
                petImage.getCreatedAt(),
                petImage.getUpdatedAt());
    }

    public static List<PetImageDto> listOf(List<PetImage> petImage) {
        return petImage.stream()
                .map(PetImageDto::of)
                .collect(Collectors.toList());
    }

}
