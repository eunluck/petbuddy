package com.petbuddy.api.repository.subscription;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.notification.Subscription;
import com.petbuddy.api.model.user.User;
import com.petbuddy.api.util.DateTimeUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSubscriptionRepository implements SubscriptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSubscriptionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Subscription findById(Long seq) {
        List<Subscription> results = jdbcTemplate.query("SELECT * FROM subscriptions WHERE seq = ?",
                new Object[]{seq},
                mapper
        );
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Subscription save(Subscription subscription) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "MERGE INTO subscriptions(seq, user_seq, endpoint, public_key, auth, create_at) KEY (user_seq) " +
                            "VALUES (null,?,?,?,?,?)",
                    new String[]{"seq"});
            ps.setLong(1, subscription.getUserId().value());
            ps.setString(2, subscription.getNotificationEndPoint());
            ps.setString(3, subscription.getPublicKey());
            ps.setString(4, subscription.getAuth());
            ps.setTimestamp(5, DateTimeUtils.timestampOf(subscription.getCreateAt()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        long generatedSeq = key != null ? key.longValue() : -1;
        return new Subscription.SubscriptionBuilder(subscription)
                .seq(generatedSeq)
                .build();
    }

    @Override
    public Optional<Subscription> findByUserSeq(Long userSeq) {
        List<Subscription> results = jdbcTemplate.query("SELECT * FROM subscriptions WHERE user_seq = ?",
                new Object[]{userSeq},
                mapper
        );
        return Optional.ofNullable(results.get(0));
    }

    @Override
    public List<Subscription> findAll() {
        return jdbcTemplate.query("SELECT * FROM subscriptions", mapper);
    }

    static RowMapper<Subscription> mapper = (rs, rowNum) -> new Subscription.SubscriptionBuilder()
            .seq(rs.getLong("seq"))
            .userId(Id.of(User.class, rs.getLong("user_seq")))
            .notificationEndPoint(rs.getString("endpoint"))
            .auth(rs.getString("auth"))
            .publicKey(rs.getString("public_key"))
            .createAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at")))
            .build();

}
