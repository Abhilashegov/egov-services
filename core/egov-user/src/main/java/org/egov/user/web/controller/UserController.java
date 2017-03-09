package org.egov.user.web.controller;

import org.egov.user.domain.service.UserService;
import org.egov.user.persistence.entity.User;
import org.egov.user.web.contract.*;
import org.egov.user.web.contract.Error;
import org.egov.user.web.contract.auth.SecureUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private UserService userService;
    private TokenStore tokenStore;

    public UserController(UserService userService, TokenStore tokenStore) {
        this.userService = userService;
        this.tokenStore = tokenStore;
    }

    @PostMapping("/_create")
    public ResponseEntity<UserDetailResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        org.egov.user.domain.model.User user = createUserRequest.toDomainForCreate();
        User savedUser = userService.save(user);
        UserRequest userRequest = new UserRequest(savedUser);
        List<UserRequest> users = new ArrayList<UserRequest>() {
            {
                add(userRequest);
            }
        };
        ResponseInfo responseInfo = ResponseInfo.builder().status(String.valueOf(HttpStatus.OK.value())).build();
        UserDetailResponse createdUserResponse = new UserDetailResponse(responseInfo, users);
        return new ResponseEntity<>(createdUserResponse, HttpStatus.OK);
    }

    @PostMapping("/_search")
    public ResponseEntity<?> get(@RequestBody UserSearchRequest request) {
        if (request.getId() != null && request.getId().size() > 0) {
            List<User> userEntities = userService.getUsersById(request.getId());
            List<org.egov.user.web.contract.UserRequest> userContracts = userEntities.stream()
                    .map(org.egov.user.web.contract.UserRequest::new).collect(Collectors.toList());
            ResponseInfo responseInfo = ResponseInfo.builder().status(String.valueOf(HttpStatus.OK.value())).build();
            UserResponse getUserByIdResponse = new UserResponse(responseInfo, userContracts);
            return new ResponseEntity<>(getUserByIdResponse, HttpStatus.OK);
        } else if (request.getUserName() != null && !request.getUserName().isEmpty()) {
            User user = userService.getUserByUsername(request.getUserName());
            List<User> userEntities = new ArrayList<>();
            if (user != null)
                userEntities.add(user);
            List<org.egov.user.web.contract.UserRequest> userContracts = userEntities.stream()
                    .map(org.egov.user.web.contract.UserRequest::new).collect(Collectors.toList());
            ResponseInfo responseInfo = ResponseInfo.builder().status(String.valueOf(HttpStatus.OK.value())).build();
            UserResponse getUserByIdResponse = new UserResponse(responseInfo, userContracts);
            return new ResponseEntity<>(getUserByIdResponse, HttpStatus.OK);
        } else {
            Error error = Error.builder().code(400).message("User ids or username cannot be empty").build();
            ResponseInfo responseInfo = ResponseInfo.builder().status(HttpStatus.BAD_REQUEST.toString()).build();
            ErrorResponse errorResponse = ErrorResponse.builder().error(error).responseInfo(responseInfo).build();

            return new ResponseEntity<>(errorResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/_details")
    public ResponseEntity<?> getUser(@RequestParam(value = "access_token") String accessToken) {
        OAuth2Authentication authentication = tokenStore.readAuthentication(accessToken);
        if (authentication != null)
            return new ResponseEntity<org.egov.user.web.contract.auth.User>(
                    ((SecureUser) authentication.getPrincipal()).getUser(), HttpStatus.OK);
        else {
            ErrorResponse errRes = populateErrors();
            return new ResponseEntity<ErrorResponse>(errRes, HttpStatus.BAD_REQUEST);
        }
    }

    private ErrorResponse populateErrors() {
        ResponseInfo responseInfo = ResponseInfo.builder().status(HttpStatus.BAD_REQUEST.toString()).apiId("").build();

        Error error = Error.builder().code(1).description("Error while fetching user details").build();

        return ErrorResponse.builder().responseInfo(responseInfo).error(error).build();
    }
}
