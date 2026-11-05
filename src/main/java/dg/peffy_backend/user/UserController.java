package dg.peffy_backend.user;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dg.peffy_backend.auth.config.RefreshTokenCookieConfig;
import dg.peffy_backend.auth.config.RefreshTokenCookieProperties;
import dg.peffy_backend.auth.dto.LoginResult;
import dg.peffy_backend.user.dto.CreateUserRequest;
import dg.peffy_backend.user.dto.LoginRequest;
import dg.peffy_backend.user.dto.LoginResponse;
import dg.peffy_backend.user.dto.UserResponse;
import dg.peffy_backend.user.dto.DebugDto.PassUpRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController 
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RefreshTokenCookieProperties cookieProperties;

    public UserController(UserService userService, RefreshTokenCookieProperties cookieProperties) {
        this.userService = userService;
        this.cookieProperties = cookieProperties;
    }

    @PostMapping ("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginAuthentication(@Valid @RequestBody LoginRequest request) {

        LoginResult result = userService.login(request);

        ResponseCookie refreshCookie = ResponseCookie
            .from(RefreshTokenCookieConfig.COOKIE_NAME, result.refreshToken())
            .httpOnly(true)
            .secure(cookieProperties.secure())
            .sameSite("Lax")
            .path(RefreshTokenCookieConfig.COOKIE_PATH)
            .maxAge(RefreshTokenCookieConfig.REFRESH_TOKEN_EXPIRATION_SECONDS)
            .build();

        LoginResponse response = new LoginResponse(result.accessToken(), result.tokenType(), result.expiresIn(), result.user());
        
        

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).body(response);
    }
    

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("/")
    public List<UserResponse> getAllUsers() {
        return userService.GetAllUsers();
    } 
    
    ////ADMIN LV1
    @PostMapping("/master/")
    public List<User> getAllUsersMaster() {
        return userService.GetAllUsersMaster();
    } 

    @PostMapping("/master/uspw")
    public User updatePassword(@RequestBody PassUpRequest request){
        return userService.UpdatePassword(request.id(), request.password());
    }
    
}
