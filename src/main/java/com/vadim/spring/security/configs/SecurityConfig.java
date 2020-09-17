package com.vadim.spring.security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    UserService userService;
private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Qualifier("userDetailsService") UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Настройки доступа
        http
                .authorizeRequests()
                .antMatchers("/authenticated/**").authenticated() //Пускаем только аутенфицированных
                //выбор нескольких ролей для допуска
                .antMatchers("/all/**").hasRole("ADMIN")
                .antMatchers("/user/**").authenticated()
                .antMatchers("/editsuser").hasRole("ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/only_for_admins/**").hasRole("ADMIN") //тоже интересно
                .antMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
                .and() //разделитель - настраиваем другое
               // .httpBasic() //при попытке зайти на страницу, требующую авторизации перекидывает на определенную страницу
                .formLogin()//тоже самое , только переброска на свою собственную сверстанную красивую што 3,14здец форму
                .successHandler(successUserHandler)
                .and()
                .logout().logoutSuccessUrl("/");//страница, куда перебрасывает после выхода

    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}

