package com.jafp.bankapi.security.jwt;

import com.jafp.bankapi.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


  private final JwtService jwtService;
  private final CustomUserDetailsService customUserDetailsService;


  public JwtAuthenticationFilter(
          JwtService jwtService,
          CustomUserDetailsService customUserDetailsService
  ) {
    this.jwtService = jwtService;
    this.customUserDetailsService = customUserDetailsService;
  }


  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {


    final String authHeader =
            request.getHeader("Authorization");


    String username = null;
    String jwt = null;


    if(authHeader != null && authHeader.startsWith("Bearer ")){

      jwt = authHeader.substring(7);

      username = jwtService.extractUsername(jwt);
    }


    if(username != null &&
            SecurityContextHolder.getContext()
                    .getAuthentication() == null){


      UserDetails userDetails =
              customUserDetailsService
                      .loadUserByUsername(username);


      if(jwtService.isTokenValid(
              jwt,
              userDetails.getUsername()
      )){


        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );


        authentication.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request)
        );


        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
      }
    }


    filterChain.doFilter(request,response);
  }
}