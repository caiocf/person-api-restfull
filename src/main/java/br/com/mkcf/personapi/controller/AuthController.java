package br.com.mkcf.personapi.controller;

import br.com.mkcf.personapi.repository.UserRepository;
import br.com.mkcf.personapi.security.AccountCredentialsVO;
import br.com.mkcf.personapi.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path= "/auth")
@Tag(name  = "Auth Endpoint")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary ="auth user")
    @SecurityRequirements
    public ResponseEntity signin(@RequestBody @Valid AccountCredentialsVO data){
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

            var user = userRepository.findByUsernane(username);

            var token = "";
            if(user != null){
                token = tokenProvider.createToken(username, user.get().getRoles());
            } else {
                throw new UsernameNotFoundException(String.format("Username %s not found", username));
            }

            Map model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        }catch (AuthenticationException e){
            throw  new BadCredentialsException("Invalid username/password supplied!");
        }



    }
}
