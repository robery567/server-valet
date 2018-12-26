package com.servervaletdev.repository.provider;

import com.servervaletdev.model.User;
import com.servervaletdev.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryProvider implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final PasswordEncoder passwordEncoder;

    UserRepositoryProvider(final JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT `id`, `email`, `password` FROM `user` WHERE `email` = ? LIMIT 1",
                new Object[]{email},
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"))
        );
    }


    @Override
    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO `user` (`username`, `email`, `password`, `creation_date`) VALUES (?, ?, ?, ?)",
                user.getUsername(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getCreationDate()
        );
    }

}
