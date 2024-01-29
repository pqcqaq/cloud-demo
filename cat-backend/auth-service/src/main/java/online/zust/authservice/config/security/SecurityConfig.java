package online.zust.authservice.config.security;

import online.zust.authservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * SpringSecurity 5.4.x以上新用法配置
 * 为避免循环依赖，仅用于配置HttpSecurity
 *
 * @author qcqcqc
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 角色继承
     *
     * @return RoleHierarchy
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

//    /**
//     * 配置忽略的路径
//     *
//     * @return WebSecurityCustomizer
//     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 设置中文配置
        messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
        return messageSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置过滤
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 添加自定义的jwt过滤器，把jwt过滤器添加在用户名密码过滤器之前
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //关闭csrf
        return http.csrf().disable()
                //关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 配置RememberMe
                .and().rememberMe().tokenValiditySeconds(60 * 60 * 24)
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
                .and().authorizeRequests(auth ->
                        auth.antMatchers("/register").permitAll()
                                .antMatchers("/login").permitAll()
                                .antMatchers("/logout").permitAll()
                                .antMatchers("/verifyToken").permitAll()
                                .antMatchers("/refreshToken").permitAll()
                                .anyRequest().authenticated()
                )
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .userDetailsService(userService)
                .build();
    }

    /**
     * 配置跨源访问(CORS)
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许跨域的域名，可以用*表示允许任何域名使用，当证书为true时，不能设置为*，否则会报错
        configuration.setAllowedOrigins(List.of("*"));
        // 允许的方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 允许任何头
        configuration.setAllowedHeaders(List.of("*"));
        // 是否支持安全证书
        configuration.setAllowCredentials(false);
        // 允许的header
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Content-Disposition"));
        // 跨域允许时间
        configuration.setMaxAge(3600L);

        // 配置源对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有URL应用CORS配置
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
