package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.edu.khpi.project2023.model.User;
import ua.edu.khpi.project2023.payload.request.LoginRequest;
import ua.edu.khpi.project2023.payload.request.UserRegisterRequest;
import ua.edu.khpi.project2023.payload.response.MessageResponse;
import ua.edu.khpi.project2023.payload.response.UserInfoResponse;
import ua.edu.khpi.project2023.security.jwt.JwtUtils;
import ua.edu.khpi.project2023.security.model.AuthUser;
import ua.edu.khpi.project2023.service.UserService;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser userDetails = (AuthUser) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("student"); //TODO remake this part

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getName(),
                        userDetails.getEmail(),
                        role));
    }

    @PostMapping("/signup")
    ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(userService.addUser(userRegisterRequest));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
