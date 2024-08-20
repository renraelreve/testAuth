package sctp.ntu.booking_api.security;

import java.util.Arrays;

import javax.sql.DataSource;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
@PropertySource(value = { "classpath:application.properties" })
public class SecurityConfiguration {

  // Load environment variables from .env file
  // static {
  // Dotenv dotenv = Dotenv.configure().load();
  // System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
  // System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
  // System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
  // }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Profile("permitAll")
  SecurityFilterChain securityFilterChainPermitAll(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll());

    return http.build();
  }

  @Bean
  @Profile("withAuth")
  SecurityFilterChain securityFilterChainWithAuthentication(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/h2").permitAll() // allow access to h2Console
            .requestMatchers(HttpMethod.POST).permitAll() // allow any user to POST without Authentication
            // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#request-authorization-architecture
            // Matching by HTTP method
            .requestMatchers("/api/auth/**").permitAll() // Allow unauthenticated access to the auth endpoint
            .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  // https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter
  @Bean
  AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
      UserDetailsService userDetailService)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailService)
        .passwordEncoder(passwordEncoder)
        .and()
        .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  // @Bean
  // InMemoryUserDetailsManager userDetailsService() {
  // UserDetails user = User.builder()
  // .username("user")
  // .password(passwordEncoder().encode("password"))
  // // .roles("USER")
  // .build();
  // return new InMemoryUserDetailsManager(user);
  // }

  // https://howtodoinjava.com/spring-boot2/datasource-configuration/
  // @Bean(name = "h2DataSource")
  // DataSource h2DataSource()
  // {
  // @SuppressWarnings("rawtypes")
  // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
  // dataSourceBuilder.driverClassName("org.h2.Driver");
  // // dataSourceBuilder.url("jdbc:h2:file:C:/temp/test");
  // dataSourceBuilder.url("jdbc:h2:mem:booking-api");
  // dataSourceBuilder.username("sa");
  // dataSourceBuilder.password("");
  // return dataSourceBuilder.build();
  // }

  @Bean(name = "postgresDataSource")
  DataSource postgresDataSource(
      @Value("${spring.datasource.url}") String springDatasourceUrl,
      @Value("${spring.datasource.username}") String springDatasourceUsername,
      @Value("${spring.datasource.password}") String springDatasourcePassword) {

    System.out.println(
        "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX Checking spring.datasource.X @Value XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    System.out.println("XXXX" + springDatasourceUrl + "XXXX");
    System.out.println("XXXX" + springDatasourceUsername + "XXXX");
    System.out.println("XXXX" + springDatasourcePassword + "XXXX");

    @SuppressWarnings("rawtypes")
    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName("org.postgresql.Driver");
    dataSourceBuilder.url(springDatasourceUrl.replaceAll("'", ""));
    dataSourceBuilder.username(springDatasourceUsername);
    dataSourceBuilder.password(springDatasourcePassword);
    return dataSourceBuilder.build();
  }

  // https://howtodoinjava.com/spring-security/inmemory-jdbc-userdetails-service/

  @Bean
  // UserDetailsService jdbcUserDetailsService(DataSource h2DataSource) {
  UserDetailsService jdbcUserDetailsService(DataSource postgresDataSource) {
    String usersByUsernameQuery = "select name as username, password, true as enabled from user_name where name = ?";
    String authsByUserQuery = "select name as username, 'user' as authority from user_name where name = ?";
    // String usersByUsernameQuery = "select username, password, enabled from
    // tbl_users where username = ?"; // matching standard query from custom SQL
    // tables
    // String authsByUserQuery = "select username, authority from tbl_authorities
    // where username = ?"; // matching standard query from custom SQL tables

    // JdbcUserDetailsManager users = new JdbcUserDetailsManager(h2DataSource);
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(postgresDataSource);

    users.setUsersByUsernameQuery(usersByUsernameQuery);
    users.setAuthoritiesByUsernameQuery(authsByUserQuery);

    return users;
  }

}
