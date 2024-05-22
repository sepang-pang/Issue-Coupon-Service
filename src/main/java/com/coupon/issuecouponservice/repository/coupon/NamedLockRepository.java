package com.coupon.issuecouponservice.repository.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NamedLockRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void getLock(String key) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("key", key);

        String sql = "SELECT GET_LOCK(:key , 3000)";

        Integer result = jdbcTemplate.queryForObject(sql, params, Integer.class);

        checkResult(result);
    }

    public void releaseLock(String key) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("key", key);

        String sql = "SELECT RELEASE_LOCK(:key)";

        Integer result = jdbcTemplate.queryForObject(sql, params, Integer.class);

        checkResult(result);
    }

    private void checkResult(Integer result) {
        if (result != 1) {
            throw new RuntimeException("LOCK 을 수행하는 중에 오류가 발생하였습니다.");
        }
    }
}
