package com.petbuddy.api.model.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class Comment {

  private final Long seq;

  private final Id<User, Long> userId;

  private final Id<Post, Long> postId;

  private String contents;

  private final Writer writer;

  private final LocalDateTime createAt;

  public Comment(Id<User, Long> userId, Id<Post, Long> postId, Writer writer, String contents) {
    this(null, userId, postId, contents, writer, null);
  }

  public Comment(Long seq, Id<User, Long> userId, Id<Post, Long> postId, String contents, Writer writer, LocalDateTime createAt) {
    checkNotNull(userId, "userId must be provided.");
    checkNotNull(postId, "postId must be provided.");
    checkArgument(isNotEmpty(contents), "contents must be provided.");
    checkArgument(
      contents.length() >= 4 && contents.length() <= 500,
      "comment contents length must be between 4 and 500 characters."
    );

    this.seq = seq;
    this.userId = userId;
    this.postId = postId;
    this.contents = contents;
    this.writer = writer;
    this.createAt = defaultIfNull(createAt, now());
  }

  public void modify(String contents) {
    checkArgument(isNotEmpty(contents), "contents must be provided.");
    checkArgument(
      contents.length() >= 4 && contents.length() <= 500,
      "post contents length must be between 4 and 500 characters."
    );

    this.contents = contents;
  }

  public Long getSeq() {
    return seq;
  }

  public Id<User, Long> getUserId() {
    return userId;
  }

  public Id<Post, Long> getPostId() {
    return postId;
  }

  public String getContents() {
    return contents;
  }

  public Optional<Writer> getWriter() {
    return ofNullable(writer);
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Comment comment = (Comment) o;
    return Objects.equals(seq, comment.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("userId", userId)
      .append("postId", postId)
      .append("contents", contents)
      .append("writer", writer)
      .append("createAt", createAt)
      .toString();
  }

  static public class Builder {
    private Long seq;
    private Id<User, Long> userId;
    private Id<Post, Long> postId;
    private String contents;
    private Writer writer;
    private LocalDateTime createAt;

    public Builder() {}

    public Builder(Comment comment) {
      this.seq = comment.seq;
      this.userId = comment.userId;
      this.postId = comment.postId;
      this.contents = comment.contents;
      this.writer = comment.writer;
      this.createAt = comment.createAt;
    }

    public Builder seq(Long seq) {
      this.seq = seq;
      return this;
    }

    public Builder userId(Id<User, Long> userId) {
      this.userId = userId;
      return this;
    }

    public Builder postId(Id<Post, Long> postId) {
      this.postId = postId;
      return this;
    }

    public Builder contents(String contents) {
      this.contents = contents;
      return this;
    }

    public Builder writer(Writer writer) {
      this.writer = writer;
      return this;
    }

    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public Comment build() {
      return new Comment(seq, userId, postId, contents, writer, createAt);
    }
  }

}