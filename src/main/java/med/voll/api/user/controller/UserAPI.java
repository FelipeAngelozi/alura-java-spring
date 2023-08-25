package med.voll.api.user.controller;

import jakarta.validation.Valid;
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

    @Autowired
    public UserAPI(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-in")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        Authentication authentication = authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
