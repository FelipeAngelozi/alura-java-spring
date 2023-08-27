package med.voll.api.user.controller;

import jakarta.validation.Valid;
import med.voll.api.tools.security.TokenService;
import med.voll.api.tools.security.dtos.TokenDataDTO;
import med.voll.api.user.model.User;
import med.voll.api.user.model.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserAPI {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public UserAPI(AuthenticationManager authenticationManager,
                   TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDataDTO(tokenJWT));
    }
}
