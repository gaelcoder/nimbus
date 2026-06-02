package com.bank.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    // O UserDetailsService é uma interface do próprio Spring para buscar o usuário
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Pega o cabeçalho Authorization da requisição
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Se não tiver cabeçalho ou não começar com "Bearer ", passa direto (vai ser barrado no SecurityConfig se a rota for protegida)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai o token (tirando o "Bearer " que tem 7 caracteres)
        jwt = authHeader.substring(7);

        // 4. Extrai o email (subject) de dentro do token
        userEmail = jwtService.extractUsername(jwt); // (Você precisará adicionar este método extractUsername no JwtService)

        // 5. Se tem e-mail no token e o usuário AINDA NÃO está autenticado no contexto atual
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Busca o usuário no banco de dados
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. Se o token for válido para este usuário
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Cria o "crachá" oficial do Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Coloca o crachá no pescoço do usuário (Salva no contexto de segurança)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8. Segue o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}