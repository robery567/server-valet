package com.servervaletdev.repository.provider;

import com.servervaletdev.model.User;
import com.servervaletdev.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceProvider implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getIdByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        grantList.add(authority);

        UserDetailProvider UserDetails = new UserDetailProvider(user.getId(), user.getPassword(), grantList);
        UserDetails.setUsername(username);

        return UserDetails;
    }

}