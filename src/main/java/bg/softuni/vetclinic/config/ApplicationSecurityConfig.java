package bg.softuni.vetclinic.config;


import bg.softuni.vetclinic.service.impl.VetClinicUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final VetClinicUserService vetClinicUserService;
    private final PasswordEncoder passwordEncoder;


    public ApplicationSecurityConfig(VetClinicUserService vetClinicUserService, PasswordEncoder passwordEncoder) {
        this.vetClinicUserService = vetClinicUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/forms/**", "/vendor/**").permitAll()
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin().loginPage("/users/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .failureForwardUrl("/users/login-error"); //todo

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(vetClinicUserService).passwordEncoder(passwordEncoder);
    }


}
