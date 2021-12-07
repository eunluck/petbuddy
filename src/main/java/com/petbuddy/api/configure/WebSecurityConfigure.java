package com.petbuddy.api.configure;

import com.petbuddy.api.model.user.Role;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.security.*;
import com.petbuddy.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.math.NumberUtils.toLong;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

  private final Jwt jwt;

  private final JwtTokenConfigure jwtTokenConfigure;

  private final JwtAccessDeniedHandler accessDeniedHandler;

  private final EntryPointUnauthorizedHandler unauthorizedHandler;

  public WebSecurityConfigure(Jwt jwt, JwtTokenConfigure jwtTokenConfigure, JwtAccessDeniedHandler accessDeniedHandler, EntryPointUnauthorizedHandler unauthorizedHandler) {
    this.jwt = jwt;
    this.jwtTokenConfigure = jwtTokenConfigure;
    this.accessDeniedHandler = accessDeniedHandler;
    this.unauthorizedHandler = unauthorizedHandler;
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter(jwtTokenConfigure.getHeader(), jwt);
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/swagger-resources", "/webjars/**", "/static/**", "/templates/**", "/h2/**");
  }

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder builder, JwtAuthenticationProvider authenticationProvider) {
    builder.authenticationProvider(authenticationProvider);
  }

  @Bean
  public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, UserService userService) {
    return new JwtAuthenticationProvider(jwt, userService);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ConnectionBasedVoter connectionBasedVoter() {
    final String regex = "^/api/user/(\\d+)/post/.*$";
    Pattern pattern = Pattern.compile(regex);
    RequestMatcher requiresAuthorizationRequestMatcher = new RegexRequestMatcher(pattern.pattern(), null);
    return new ConnectionBasedVoter(
      requiresAuthorizationRequestMatcher,
      (String url) -> {
        /* url에서 targetId를 추출하기 위해 정규식 처리 */
        Matcher matcher = pattern.matcher(url);
        long id = matcher.find() ? toLong(matcher.group(1), -1) : -1;
        return  id;
      }
    );
  }

  @Bean
  public AccessDecisionManager accessDecisionManager() {
    List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
    decisionVoters.add(new WebExpressionVoter());
    decisionVoters.add(connectionBasedVoter());
    // 모든 voter가 승인해야 해야한다.
    return new UnanimousBased(decisionVoters);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf()
        .disable()
      .headers()
        .disable()
      .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
      .authorizeRequests()
        .antMatchers("/api/_hcheck").permitAll()
        .antMatchers("/api/auth").permitAll()
        .antMatchers("/api/user/join").permitAll()
        .antMatchers("/api/user/exists").permitAll()
        .antMatchers("/api/**").hasRole(Role.USER.name())
        //.accessDecisionManager(accessDecisionManager())
        .anyRequest().permitAll()
        .and()
      .formLogin()
        .disable();
    http
      .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

}