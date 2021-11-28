package com.petbuddy.api.controller.post;

import com.petbuddy.api.model.pet.Comment;
import com.petbuddy.api.model.pet.Writer;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class CommentDto {

  @ApiModelProperty(value = "PK", required = true)
  private Long seq;

  @ApiModelProperty(value = "내용", required = true)
  private String contents;

  @ApiModelProperty(value = "작성자")
  private Writer writer;

  @ApiModelProperty(value = "작성일시", required = true)
  private LocalDateTime createAt;

  public CommentDto(Comment source) {
    copyProperties(source, this);

    this.writer = source.getWriter().orElse(null);
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
      .append("writer", writer)
      .append("createAt", createAt)
      .toString();
  }

}