package ru.geekbrains.backend.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;
import ru.geekbrains.backend.security.filter.AuthTokenFilter;
import ru.geekbrains.backend.security.filter.ExceptionHandlerFilter;
import ru.geekbrains.backend.security.service.UserService;


@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAsync
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    private AuthTokenFilter authTokenFilter;
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthTokenFilter(AuthTokenFilter authTokenFilter) {
        this.authTokenFilter = authTokenFilter;
    }

    @Autowired
    public void setExceptionHandlerFilter(ExceptionHandlerFilter exceptionHandlerFilter) {
        this.exceptionHandlerFilter = exceptionHandlerFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //стейтлесс
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();

        //Отключаем авторизацию тк форма авторизации создается не на спринг технологии
        http.formLogin().disable();
        //отключаем стандартную форму авторизации в браузере
        http.httpBasic().disable();

        //обязательное использование https
        http.requiresChannel().anyRequest().requiresSecure();

        //валидация jwt до того как он попадет в контроллер
        http.addFilterBefore(authTokenFilter, SessionManagementFilter.class);

        http.addFilterBefore(exceptionHandlerFilter, AuthTokenFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // https://stackoverflow.com/questions/39314176/filter-invoke-twice-when-register-as-spring-bean
    @Bean
    public FilterRegistrationBean registration(AuthTokenFilter authTokenFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(authTokenFilter);
        registrationBean.setEnabled(true);

        return registrationBean;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }
}
