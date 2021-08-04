package com.example.tourappapi.services;

import com.example.tourappapi.dto.*;
import com.example.tourappapi.exceptions.*;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.ConfirmationToken;
import com.example.tourappapi.repositories.AgentRepository;
import com.example.tourappapi.repositories.ConfirmationTokenRepository;
import com.example.tourappapi.services.interfaces.AgentService;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    @Value("${forgotpassword.url}")
    private String forgotPasswordUrl;

    AgentService service;
    EmailService emailService;
    ConfirmationTokenRepository confirmationTokenRepository;

    public AuthServiceImpl(AgentService service,
                           EmailService emailService,
                           ConfirmationTokenRepository confirmationTokenRepository){
        this.service = service;
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    /**
     * {@inheritDoc}
     * @param userDTO
     * @return
     */
    @Override
    public String register(RegisterPostDto userDTO) {
        Agent user = new Agent();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setCompanyName(userDTO.getCompanyName());
        user.setAgentName(userDTO.getAgentName());
        user.setVoen(userDTO.getVoen());
        service.checkIfExists(user);
        Boolean isRegisteredToKeycloak = registerToKeyclaok(userDTO);
        if (isRegisteredToKeycloak){
            service.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            emailService.sendMail(user.getEmail(), "Tour", "Verify account http://localhost:8000/api/v1/users/confirm-account?token=" + confirmationToken.getConfirmationToken());
            return "Please check your email";
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @param confirmationToken
     */
    @Override
    public void confirmAccount(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.getByConfirmationToken(confirmationToken);
        if (token == null || token.getAgent() == null) throw new InvalidTokenException();
        if (LocalDateTime.now().isAfter(token.getCreatedDate().plusDays(3))) throw new TokenExpiredException();
        Agent agent = service.getByEmail(token.getAgent().getEmail());
        if (agent == null) throw new InvalidTokenException();
        agent.setIsActive(true);
        service.save(agent);
        emailService.sendMail(agent.getEmail(), "Congratilations",
                "You are registered successfully!");
    }

    /**
     * {@inheritDoc}
     * @param user
     * @return
     */
    @Override
    public LoginResponseDto login(LoginPostDto user) {
        try {
            service.getByEmail(user.getEmail());
        }catch (Exception e){
            throw new LoginException();
        }
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");
        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        try{
            AccessTokenResponse response = authzClient.obtainAccessToken(user.getEmail(), user.getPassword());
            return new LoginResponseDto(response.getToken());
        }catch (Exception e){
            throw new LoginException();
        }
    }

    /**
     * {@inheritDoc}
     * @param email
     */
    @Override
    public void forgotPassword(String email) {
        Agent agent = service.getByEmail(email);
        if (agent == null) throw new NoSuchEmailException();
        ConfirmationToken token = new ConfirmationToken(agent);
        confirmationTokenRepository.save(token);
        emailService.sendMail(agent.getEmail(), "Tour", "Reset your password " + forgotPasswordUrl + token.getConfirmationToken());
    }

    /**
     * {@inheritDoc}
     * @param dto
     */
    @Override
    public void resetPassword(ResetPasswordDto dto) {
        ConfirmationToken token = confirmationTokenRepository.getByConfirmationToken(dto.getToken());
        if (token == null) throw new InvalidTokenException();
        if (LocalDateTime.now().isAfter(token.getCreatedDate().plusDays(3))) throw new TokenExpiredException();
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("nurlan").password("rafet123")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> userRepresentations = usersResource.search(token.getAgent().getUsername());
        UserRepresentation userRepresentation = userRepresentations.stream()
                .filter(u -> u.getUsername().equals(token.getAgent().getUsername())).findFirst().orElse(null);
        if (userRepresentation == null) throw new UserNotFoundException();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(dto.getPassword());
        userResource.resetPassword(passwordCred);
    }

    @Override
    public void changePassword(String username, ChangePasswordDto dto) {
        if (dto.getOldPassword().equals(dto.getNewPassword())) throw new PasswordsAreTheSameException();
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("nurlan").password("rafet123")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> userRepresentations = usersResource.search(username);
        UserRepresentation userRepresentation = userRepresentations.stream()
                .filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        if (userRepresentation == null) throw new UserNotFoundException();
        LoginPostDto login = LoginPostDto.builder().email(userRepresentation.getEmail()).password(dto.getOldPassword()).build();
        try{
            login(login);
        }catch (Exception e){
            throw new OldPassIsIncorrectException();
        }
        UserResource userResource = usersResource.get(userRepresentation.getId());
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(dto.getNewPassword());
        userResource.resetPassword(passwordCred);
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
