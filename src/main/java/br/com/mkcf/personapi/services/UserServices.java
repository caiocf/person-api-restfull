package br.com.mkcf.personapi.services;

import br.com.mkcf.personapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public static final String USERNAME_NOT_FOUND = "Username %s not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsernane(username);
        if(user.isPresent())
            return user.get();
        else
            throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username));
    }
}
