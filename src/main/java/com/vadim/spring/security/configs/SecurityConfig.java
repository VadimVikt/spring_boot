package com.vadim.spring.security.configs;

import com.vadim.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Настройки доступа
        http
                .authorizeRequests()
                .antMatchers("/authenticated/**").authenticated() //Пускаем только аутенфицированных
                //выбор нескольких ролей для допуска
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/only_for_admins/**").hasRole("ADMIN") //тоже интересно
                .antMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
                .and() //разделитель - настраиваем другое
               // .httpBasic() //при попытке зайти на страницу, требующую авторизации перекидывает на определенную страницу
                .formLogin()//тоже самое , только переброска на свою собственную сверстанную красивую што 3,14здец форму
                .and()
                .logout().logoutSuccessUrl("/");//страница, куда перебрасывает после выхода

    }
    // inMemoryAuthentications Пользователи находятся в памяти
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2y$12$hzWtJ9CDai1pZBJ3F/Nj1eimrgQQbPg/CG8DjANcAJx5GvLiIEJla")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2y$12$hzWtJ9CDai1pZBJ3F/Nj1eimrgQQbPg/CG8DjANcAJx5GvLiIEJla")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    //jdbc Authentication работа с юзерами не имея сущности юзер
//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
        //Можем при подключении попросить спринг заранее положить туда пользователей
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2y$12$hzWtJ9CDai1pZBJ3F/Nj1eimrgQQbPg/CG8DjANcAJx5GvLiIEJla")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2y$12$hzWtJ9CDai1pZBJ3F/Nj1eimrgQQbPg/CG8DjANcAJx5GvLiIEJla")
//                .roles("ADMIN", "USER")
//                .build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        //Создаем двух учебных пользователей в базе, предварительно проверив их наличие
//        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(user.getUsername());
//        }
//        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(admin.getUsername());
//        }
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
//        return jdbcUserDetailsManager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

}

