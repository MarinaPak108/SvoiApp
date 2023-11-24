package com.svoiapp.config;

import com.svoiapp.entity.DataEntity;
import com.svoiapp.repo.DataRepo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

    private final DataRepo repo;

    public UserServiceImpl(DataRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<DataEntity> opt = Optional.ofNullable(repo.findDataEntityByLogin(login));

        org.springframework.security.core.userdetails.User springUser = null;

        if(opt.isEmpty() || opt == null){
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        } else{
            DataEntity data = opt.get();
            String role = data.getAuth().getName();
            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(role));

            springUser = new org.springframework.security.core.userdetails.User(login, data.getPwd(), ga);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new UsernamePasswordAuthenticationToken(springUser, null), ga));

        }

        return springUser;
    }
}
