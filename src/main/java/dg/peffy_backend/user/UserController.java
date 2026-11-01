package dg.peffy_backend.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginAuthentication(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
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
