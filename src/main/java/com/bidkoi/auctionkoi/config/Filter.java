package com.bidkoi.auctionkoi.config;

import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class Filter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

   @Autowired
   @Qualifier("handlerExceptionResolver")
   HandlerExceptionResolver handlerExceptionResolver;

    private final List<String> AUTH_PERMISSIONS = List.of(
            "/BidKoi/account/login",
            "/BidKoi/account/register",
            "/BidKoi/ws/**",
            "/BidKoi/account/creation",
            "/BidKoi/account"
    );

    public boolean checkIsPublicAPI(String uri) {
        //uri :

        // nếu gặp những cái api trong list ở trên => cho phép truy cập lun => true
        AntPathMatcher pathMatch = new AntPathMatcher();

        //check token => false
        return AUTH_PERMISSIONS.stream().anyMatch(pattern -> pathMatch.match(pattern, uri));

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // Hoặc thay thế "*" bằng nguồn cụ thể nếu muốn bảo mật
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        //check xem api mà nguười dùng yc có phải là một public api hay ko
        boolean isPublicAPI = checkIsPublicAPI(request.getRequestURI());

        if (isPublicAPI) {
            filterChain.doFilter(request, response);
        }else{
            String token = getToken(request);
            if (token == null) {
                //ko đc phép truy cap
                handlerExceptionResolver.resolveException(request,response,null,new AppException(ErrorCode.EMPTY_TOKEN));
                return;
            }
            // => có token
            // check token co đúng ko => lay thông tin account từ token
            Account account;
            try {
                account = tokenService.getAccountByToken(token);
            }catch (ExpiredJwtException e) {
                // response token het han
                handlerExceptionResolver.resolveException(request,response,null,new AppException(ErrorCode.EMPTY_TOKEN));
                return;
            }catch (MalformedJwtException e) {
                // response token sai
                handlerExceptionResolver.resolveException(request,response,null,new AppException(ErrorCode.EMPTY_TOKEN));
                return;
            }
            // token chuan => cho phep truy cap
            // lưu lại thong tin account

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    account,
                    token,
                    account.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //token ok cho vao
            filterChain.doFilter(request, response);
        }
    }

    public String getToken (HttpServletRequest request) {
        String authHedear = request.getHeader("Authorization");
        if(authHedear == null) return null;
        return authHedear.substring(7);
    }

}