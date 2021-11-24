package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.post.Post;
import com.petbuddy.api.model.post.Writer;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static com.petbuddy.api.util.DateTimeUtils.dateTimeOf;
import static com.petbuddy.api.util.DateTimeUtils.timestampOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcPostRepository implements PostRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcPostRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Post insert(Post post) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO posts(seq,user_seq,contents,like_count,comment_count,create_at) VALUES (null,?,?,?,?,?)", new String[]{"seq"});
      ps.setLong(1, post.getUserId().value());
      ps.setString(2, post.getContents());
      ps.setInt(3, post.getLikes());
      ps.setInt(4, post.getComments());
      ps.setTimestamp(5, timestampOf(post.getCreateAt()));
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    long generatedSeq = key != null ? key.longValue() : -1;
    return new Post.Builder(post)
      .seq(generatedSeq)
      .build();
  }

  @Override
  public void update(Post post) {
    jdbcTemplate.update(
      "UPDATE posts SET contents=?,like_count=?,comment_count=? WHERE seq=?",
      post.getContents(),
      post.getLikes(),
      post.getComments(),
      post.getSeq()
    );
  }

  @Override
  public Optional<Post> findById(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId) {
    List<Post> results = jdbcTemplate.query(
      "SELECT " +
          "p.*,u.email,u.name,ifnull(l.seq,false) as likesOfMe " +
        "FROM " +
          "posts p JOIN users u ON p.user_seq=u.seq LEFT OUTER JOIN likes l ON p.seq=l.post_seq AND l.user_seq=? " +
        "WHERE " +
          "p.seq=? AND p.user_seq=?",
      new Object[]{userId.value(), postId.value(), writerId.value()},
      mapper
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public List<Post> findAll(Id<User, Long> writerId, Id<User, Long> userId, long offset, int limit) {
    return jdbcTemplate.query(
      "SELECT " +
          "p.*,u.email,u.name,ifnull(l.seq,false) as likesOfMe " +
        "FROM " +
          "posts p JOIN users u ON p.user_seq=u.seq LEFT OUTER JOIN likes l ON p.seq=l.post_seq AND l.user_seq=? " +
        "WHERE " +
          "p.user_seq=? " +
        "ORDER BY " +
          "p.seq DESC " +
        "LIMIT " +
          "?,?",
      new Object[]{userId.value(), writerId.value(), offset, limit},
      mapper
    );
  }

  static RowMapper<Post> mapper = (rs, rowNum) -> new Post.Builder()
    .seq(rs.getLong("seq"))
    .userId(Id.of(User.class, rs.getLong("user_seq")))
    .contents(rs.getString("contents"))
    .likes(rs.getInt("like_count"))
    .likesOfMe(rs.getBoolean("likesOfMe"))
    .comments(rs.getInt("comment_count"))
    .writer(new Writer(new Email(rs.getString("email")), rs.getString("name")))
    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
    .build();

}