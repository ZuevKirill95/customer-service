package ru.sber.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.sber.backend.constants.GeneratePassword;
import ru.sber.backend.entities.request.LoginRequest;
import ru.sber.backend.entities.request.SignupRequest;
import ru.sber.backend.exceptions.UserNotFound;
import ru.sber.backend.models.*;
import ru.sber.backend.services.EmailService;
import ru.sber.backend.services.EmailServiceImpl;
import ru.sber.backend.services.JwtService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Работа с рестами связанных с учетными данными пользователя
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final String keycloakTokenUrl = "http://localhost:8080/realms/customer-realm/protocol/openid-connect/token";
    private final String keycloakCreateUserUrl = "http://localhost:8080/admin/realms/customer-realm/users";
    private final String keycloakUpdateUserUrl = "http://localhost:8080/admin/realms/customer-realm/users/";
    private final String clientId = "login-app";
    private final String grantType = "password";
    private final JwtService jwtService;
    private final EmailService emailService;

    @Autowired
    public AuthorizationController(JwtService jwtService, EmailService emailService) {
        this.jwtService = jwtService;
        this.emailService = emailService;
    }


    /**
     * Обновляет данные профиля пользователя
     *
     * @param signupRequest новые данные
     * @return статус операции
     * @throws JsonProcessingException
     */
    @PreAuthorize("hasRole('client_user')")
    @PutMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Выводим новые данные о клиенте {}", signupRequest);
        Jwt jwt = jwtService.getJwtSecurityContext();

        UpdateResponse updateResponse = new UpdateResponse();

        updateResponse.setEmail(
                Optional.ofNullable(signupRequest.getEmail())
                        .orElseGet(() -> jwtService.getEmailClaim(jwt))
        );

        Attributes attributes = new Attributes();

        attributes.setPhoneNumber(
                Optional.ofNullable(signupRequest.getNumber())
                        .orElseGet(() -> jwtService.getPhoneNumberClaim(jwt))
        );
        updateResponse.setAttributes(attributes);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<UpdateResponse> userEntity = new HttpEntity<>(updateResponse, userHeaders);

        log.info("Http entity: {}", userEntity);
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakUpdateUserUrl + jwtService.getSubClaim(jwtService.getJwtSecurityContext()),
                    HttpMethod.PUT, userEntity, String.class);
            log.info("Результат отправки на keycloak: {}", userResponseEntity.getStatusCode());

            return new ResponseEntity<>(userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Осуществляет вход пользователя
     *
     * @param loginRequest - данные для входа
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody LoginRequest loginRequest) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", loginRequest.getUsername());
        tokenBody.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);
            log.info("status: {}", tokenResponseEntity.getStatusCode());
            return new ResponseEntity<>(tokenResponseEntity.getBody(),
                    tokenResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Осуществляет смену пароля пользователя
     *
     * @param changePassword данные для смены пароля
     * @return результат
     * @throws JsonProcessingException
     */
    @PreAuthorize("hasRole('client_user')")
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePasswordUser(@RequestBody ChangePassword changePassword) throws JsonProcessingException {
        LoginRequest authAccount = new LoginRequest(jwtService.getPreferredUsernameClaim(
                jwtService.getJwtSecurityContext()
        )
                , changePassword.getOldPassword());

        if (signInUser(authAccount).getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            return ResponseEntity.badRequest().build();
        }

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setType("password");
        resetPasswordRequest.setTemporary(false);
        resetPasswordRequest.setValue(changePassword.getNewPassword());
        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<ResetPasswordRequest> resetEntity = new HttpEntity<>(resetPasswordRequest, userHeaders);
        String userId = jwtService.getSubClaim(jwtService.getJwtSecurityContext());
        try {
            ResponseEntity<String> resetResponseEntity = new RestTemplate().exchange(
                    keycloakUpdateUserUrl +
                            userId +
                            "/reset-password",
                    HttpMethod.PUT, resetEntity, String.class);
            return new ResponseEntity<>(resetResponseEntity.getStatusCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    /**
     * Реализует логику регистрации пользователя
     *
     * @param signupRequest данные для регистрации
     * @return результат регистрации
     * @throws JsonProcessingException
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Выводим данные о клиенте {}", signupRequest);
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(signupRequest.getUsername());
        userRequest.setEmail(signupRequest.getEmail());
        userRequest.setEnabled(true);

        Attributes attributes = new Attributes();
        attributes.setPhoneNumber(signupRequest.getNumber()
        );
        attributes.setDateBirthday(signupRequest.getDateOfBirth().toString());
        userRequest.setAttributes(attributes);

        Credential credential = new Credential();
        credential.setType(grantType);
        credential.setValue(signupRequest.getPassword());

        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        userRequest.setCredentials(credentials);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<UserRequest> userEntity = new HttpEntity<>(userRequest, userHeaders);

        log.info("Http entity: {}", userEntity);
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl, HttpMethod.POST, userEntity, String.class);
            log.info("Результат отправки на keycloak: {}", userResponseEntity.getStatusCode());

            return new ResponseEntity<>(userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обновляет токен пользователя
     *
     * @param refreshToken токен для обновления access токена
     * @return новый токен
     */
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshUser(@RequestBody RefreshToken refreshToken) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "refresh_token");
        tokenBody.add("client_id", clientId);
        tokenBody.add("refresh_token", refreshToken.getRefresh_token());

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);
            log.info("result refresh: {}", tokenResponseEntity.getStatusCode());
            return new ResponseEntity<>(tokenResponseEntity.getBody(),
                    tokenResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Возвращает данные пользователя
     *
     * @return данные пользователя
     */
    @PreAuthorize("hasRole('client_user')")
    @GetMapping
    public ResponseEntity<UserResponse> getUserDetails() {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);

        Jwt jwt = jwtService.getJwtSecurityContext();
        UserResponse userDetails = new UserResponse(
                jwtService.getSubClaim(jwt), jwtService.getPreferredUsernameClaim(jwt),
                jwtService.getEmailClaim(jwt), jwtService.getPhoneNumberClaim(jwt),
                jwtService.getDateBirthdayClaim(jwt)
        );


        return new ResponseEntity<>(userDetails, userHeaders, HttpStatus.OK);
    }

    /**
     * Сбрасывает пароль пользователя
     *
     * @param resetPassword данные необходимые для сброса пароля
     * @throws JsonProcessingException
     */
    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPassword resetPassword) throws JsonProcessingException {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setType("password");
        resetPasswordRequest.setTemporary(false);
        String newPassword = GeneratePassword.generateRandomPassword(20);
        resetPasswordRequest.setValue(newPassword);
        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<ResetPasswordRequest> resetEntity = new HttpEntity<>(resetPasswordRequest, userHeaders);
        log.info("Http entity: {}", resetEntity);
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl + "?email=" + resetPassword.getEmail(),
                    HttpMethod.GET, resetEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode usersNode = objectMapper.readTree(userResponseEntity.getBody());
            if (!usersNode.isArray() || usersNode.size() <= 0) {
                throw new UserNotFound();
            }
            JsonNode userNode = usersNode.get(0);
            String userId = userNode.get("id").asText();
            String username = userNode.get("username").asText();

            log.info("user: {}", userResponseEntity.getBody());

            ResponseEntity<String> resetResponseEntity = new RestTemplate().exchange(
                    keycloakUpdateUserUrl +
                            userId +
                            "/reset-password",
                    HttpMethod.PUT, resetEntity, String.class);
            log.info("Результат отправки на keycloak: {}", resetResponseEntity.getStatusCode());
            resetPassword.setPassword(newPassword);
            resetPassword.setUsername(username);
            log.info("email service: {}", emailService);
            emailService.sendResetPassword(resetPassword);
            return new ResponseEntity<>(resetResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получает данные администратора
     *
     * @return
     * @throws JsonProcessingException
     */
    private HttpHeaders getHttpHeadersAdmin() throws JsonProcessingException {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(signInUser(new LoginRequest("admin", "11111")).getBody());
        String accessToken = rootNode.path("access_token").asText();
        userHeaders.setBearerAuth(accessToken);
        return userHeaders;
    }
}
