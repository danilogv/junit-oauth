package com.testes.junit_oauth.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keys.KeyManager;
import org.springframework.security.crypto.keys.StaticKeyGeneratingKeyManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.UUID;

@Configuration
@EnableWebSecurity
@Import(OAuth2AuthorizationServerConfiguration.class)
public class Seguranca extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder senha;

    //Obtém o usuário de acesso
    @Bean
    public UserDetailsService getUsuario() {
        InMemoryUserDetailsManager usuarioConfiguracao = new InMemoryUserDetailsManager();
        UserDetails usuario = User.withUsername("danilo").password(this.senha.encode("123")).authorities("read").build();
        usuarioConfiguracao.createUser(usuario);
        return usuarioConfiguracao;
    }

    //Obtém a senha criptografada
    @Bean
    public PasswordEncoder getSenha() {
        this.senha = new BCryptPasswordEncoder();
        return this.senha;
    }

    //Obtém o cliente de acesso
    @Bean
    public RegisteredClientRepository getCliente() {
        RegisteredClient cliente =
            RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("cliente")
                .clientSecret("123")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/authorized")
                .scope("read")
                .build()
        ;

        RegisteredClientRepository registroCliente =  new InMemoryRegisteredClientRepository(cliente);
        return registroCliente;
    }

    //Obtém a chave de criptografia
    @Bean
    public KeyManager getChave() {
        KeyManager chave = new StaticKeyGeneratingKeyManager();
        return chave;
    }

}
