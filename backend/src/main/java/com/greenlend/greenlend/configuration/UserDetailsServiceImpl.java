package com.greenlend.greenlend.configuration;

import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.repository.GLUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private GLUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        GLUser user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().toString()));

        CustomUserDetails customUserDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), grantedAuthorities);
        customUserDetails.setFirstName(user.getFirstname());
        customUserDetails.setLastName(user.getLastname());
        customUserDetails.setId(user.getUserId());


        return customUserDetails;
    }

}