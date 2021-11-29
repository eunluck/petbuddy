package com.petbuddy.api.controller.post;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.Writer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@NoArgsConstructor
public class PetDto {

  @ApiModelProperty(value = "PK", required = true)
  private Long seq;

  @ApiModelProperty(value = "내용", required = true)
  private String contents;

  @ApiModelProperty(value = "좋아요 횟수", required = true)
  private int likes;

  @ApiModelProperty(value = "나의 좋아요 여부", required = true)
  private boolean likesOfMe;

  @ApiModelProperty(value = "댓글 갯수", required = true)
  private int comments;

  @ApiModelProperty(value = "작성자")
  private Writer writer;

  @ApiModelProperty(value = "작성일시", required = true)
  private LocalDateTime createAt;

  public PetDto(Pet source) {
    copyProperties(source, this);

  }


}