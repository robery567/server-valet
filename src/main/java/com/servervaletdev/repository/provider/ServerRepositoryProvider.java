package com.servervaletdev.repository.provider;

import com.servervaletdev.model.Server;
import com.servervaletdev.repository.ServerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServerRepositoryProvider implements ServerRepository {
    private final JdbcTemplate jdbcTemplate;

    ServerRepositoryProvider(final JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Server getServerByUserId(Integer userId) {
        return jdbcTemplate.queryForObject(
                "SELECT `id`, `hostname`, `username`, `password`, `port`, `user_id` FROM `server` WHERE `user_id` = ? LIMIT 1",
                new Object[]{userId},
                (rs, rowNum) -> new Server(
                        rs.getInt("id"),
                        rs.getString("hostname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("port"),
                        rs.getInt("user_id")
                )
        );
    }

    @Override
    public void save(Server server) {
        jdbcTemplate.update(
                "INSERT INTO `server` (`hostname`, `username`, `password`, `port`, `user_id`, `distribution_name`) VALUES (?, ?, ?, ?, ?, ?)",
                server.getHostName(),
                server.getUserName(),
                server.getPassword(),
                server.getPort(),
                server.getUserId(),
                server.getDistributionName()
        );
    }
}
