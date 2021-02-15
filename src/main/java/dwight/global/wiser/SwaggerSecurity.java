@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
			.authorizeRequests()
				.antMatchers("/swagger-ui.html").authenticated()
				.anyRequest().permitAll()
				.and()
			.formLogin()
				.loginPage("/auth")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("harambee_staff")
				.password("DwightGlobalStaff")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}