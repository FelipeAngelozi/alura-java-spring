package med.voll.api.tools.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.user.model.User;
import med.voll.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Autowired
    public SecurityFilter(TokenService tokenService,
                          UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = tokenRecovery(request);
        if (Objects.nonNull(tokenJWT)) {
            String subject = tokenService.getSubject(tokenJWT);
            UserDetails user = userRepository.findByLogin(subject);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        };

        filterChain.doFilter(request, response);
    }

    private String tokenRecovery(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(Objects.nonNull(authorizationHeader)) return authorizationHeader.replace("Bearer", "");
        return null;
    }
}
