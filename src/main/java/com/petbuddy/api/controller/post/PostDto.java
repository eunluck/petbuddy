package com.petbuddy.api.controller.post;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.Writer;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class PostDto {

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

  public PostDto(Pet source) {
    copyProperties(source, this);

  }

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public boolean isLikesOfMe() {
    return likesOfMe;
  }

  public void setLikesOfMe(boolean likesOfMe) {
    this.likesOfMe = likesOfMe;
  }

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public Writer getWriter() {
    return writer;
  }

  public void setWriter(Writer writer) {
    this.writer = writer;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("contents", contents)
      .append("likes", likes)
      .append("likesOfMe", likesOfMe)
      .append("comments", comments)
      .append("writer", writer)
      .append("createAt", createAt)
      .toString();
  }

}