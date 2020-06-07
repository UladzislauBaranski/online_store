package com.gmail.vladbaransky.servicemodule.impl;

import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.AppUserPrincipal;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService;

    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        logger.info("User '" + username + "' was found");
        return new AppUserPrincipal(user);
    }
}
