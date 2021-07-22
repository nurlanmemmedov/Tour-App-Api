package com.example.tourappapi.services;

import com.example.tourappapi.dto.LoginPostDto;
import com.example.tourappapi.dto.LoginResponseDto;
import com.example.tourappapi.dto.RegisterPostDto;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.ConfirmationToken;
import com.example.tourappapi.repositories.AgentRepository;
import com.example.tourappapi.repositories.ConfirmationTokenRepository;
import com.example.tourappapi.services.interfaces.AuthService;
import com.example.tourappapi.services.interfaces.EmailService;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    AgentRepository userRepository;
    EmailService emailService;
    ConfirmationTokenRepository confirmationTokenRepository;

    public AuthServiceImpl(AgentRepository userRepository,
                           EmailService emailService,
                           ConfirmationTokenRepository confirmationTokenRepository){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public String register(RegisterPostDto userDTO) {
        Boolean isRegisteredToKeycloak = registerToKeyclaok(userDTO);
        if (isRegisteredToKeycloak){
            Agent user = new Agent();
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setCompanyName(userDTO.getCompanyName());
            user.setAgentName(userDTO.getAgentName());
            user.setVoen(userDTO.getVoen());
            userRepository.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            emailService.sendMail(user.getEmail(), "Tour", "Verify account http://localhost:8000/api/v1/users/confirm-account?token=" + confirmationToken.getConfirmationToken());
            return "Please check your email";
        }
        return null;
    }

    @Override
    public void confirmAccount(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
            Agent agent = userRepository.getAgentByEmail(token.getAgent().getEmail());
            agent.setIsActive(true);
            userRepository.save(agent);
            emailService.sendMail(agent.getEmail(), "Congratilations",
                    "You are registered successfully!");
        }
        else
        {
            //TODO
        }
    }

    @Override
    public LoginResponseDto login(LoginPostDto user) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");
        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse response =
                authzClient.obtainAccessToken(user.getEmail(), user.getPassword());
        return new LoginResponseDto(response.getToken());
    }

    public boolean registerToKeyclaok(RegisterPostDto userDTO){
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("nurlan").password("rafet123")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);
        keycloak.tokenManager().getAccessToken();
        System.out.println(response.getStatus());
        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(userDTO.getPassword());
            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCred);
        }
        return response.getStatus() == 201;
    }
}
