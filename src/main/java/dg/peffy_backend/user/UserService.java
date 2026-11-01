package dg.peffy_backend.user;


import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dg.peffy_backend.auth.RefreshTokenService;
import dg.peffy_backend.auth.dto.LoginResult;
import dg.peffy_backend.exception.DuplicateResourceException;
import dg.peffy_backend.exception.InvalidCredentialsException;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.security.JwtService;
import dg.peffy_backend.user.dto.CreateUserRequest;
import dg.peffy_backend.user.dto.LoginRequest;
import dg.peffy_backend.user.dto.UserResponse;

@Service 
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    //______ USER CREATION ________ //
    public ResponseEntity<UserResponse> createUser(CreateUserRequest request){
        // 1. Verifica email già esistente
        if (userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("Email already registered");
             
        }
        try {
            // 2. Crea User
            User user = new User();
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            // 3. Hash password
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

            // 4. Salva User & Response
            User saved = userRepository.save(user);
            return  ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(saved));

            
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("Email already registered");
        }
    }

    private UserResponse mapToResponse(User user){
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCreatedAt());
    }

    //_____________________________//
    //_________LOGIN______________//
    @Transactional
    public LoginResult login(LoginRequest request){

        User user = userRepository.findByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        String refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResult(
            accessToken, "Bearer", jwtService.getAccessTokenExpirationSeconds(), mapToResponse(user), refreshToken
        );

    }

    

    //_____________________________//


    public UserResponse getUserById(Integer id) {
        return mapToResponse(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: "+ id +" not found")));
    }

    public List<UserResponse> GetAllUsers() {
        List<User> listUser = userRepository.findAll();
        if (listUser == null) {return Collections.emptyList();}
        return listUser.stream().map(this::mapToResponse).toList();
    }

    public UserResponse UpdateUser(User user) {
        User exsUser = userRepository.findById(user.getId()).orElseThrow();
        exsUser.setFirstName(user.getFirstName());
        exsUser.setEmail(user.getEmail());
        return mapToResponse(userRepository.save(exsUser));
    }

    public void DeleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    //ADMIN LV1
    
    public List<User> GetAllUsersMaster() {
        List<User> listUser = userRepository.findAll();
        return listUser;
    }

    @Transactional 
    public User UpdatePassword(Integer userId, String password){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPasswordHash(passwordEncoder.encode(password));
        user = userRepository.save(user);
        return user;
    }
}
