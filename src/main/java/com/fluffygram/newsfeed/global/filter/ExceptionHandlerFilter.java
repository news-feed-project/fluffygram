package com.fluffygram.newsfeed.global.filter;

import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.WrongAccessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (WrongAccessException ex){

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8"); // UTF-8 인코딩 설정

            ExceptionType exceptionType = ex.getExceptionType();

            String errorMsg =
                    "datetime :" + LocalDateTime.now() +
                    ",\n" +
                    "httpStatus :" + exceptionType.getStatus().toString() +
                    ",\n" +
                    "error :" + exceptionType.getMessage();


            response.getWriter().write(errorMsg);

        }
    }
}
