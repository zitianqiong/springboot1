package pers.zitianqiong.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.zitianqiong.utils.JwtTokenUtil;
import pers.zitianqiong.utils.RedisUtil;

/**
 * <p>描述：JWT 登录授权过滤器 前置拦截</p>
 *
 * @author 丛吉钰
 * @date 2022/11/2
 */
@Slf4j
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    //登录需要userDetailsService
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Header是前端传给我们.
        //要验证的头：
        String authHeader = request.getHeader(tokenHeader);
        //存在token，如果不存在或者开头不是tokenHead
        if (null != authHeader && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            //从token中获取用户名
            String userName = jwtTokenUtil.getUserNameFromToken(authToken);
            if (redisUtil.exists("userToken:"+userName)) {
                String token = redisUtil.get("userToken:" + userName);
                if (!authToken.equals(token)){
                    throw new AccessDeniedException("token过期");
                }
            }else {
                throw new AccessDeniedException("用户token无效");
            }
            //token存在，用户名未登录
            if (null != userName && null == SecurityContextHolder.getContext().getAuthentication()) {
                //登录了
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                //重新放到用户对象当中：返回是boolean
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //放行
        filterChain.doFilter(request, httpServletResponse);
    }
}
