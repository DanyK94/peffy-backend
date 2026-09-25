package dg.peffy_backend.user;


import java.util.List;
import java.time.Instant;

import org.springframework.stereotype.Service;

import dg.peffy_backend.exception.ResourceNotFoundException;

@Service 
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User CreateUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassw(request.getPassw());
        user.setCreatedAt(Instant.now());
        return userRepository.save(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: "+ id +" not found"));
    }

    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    public User UpdateUser(User user) {
        User exsUser = userRepository.findById(user.getId()).orElseThrow();
        exsUser.setUsername(user.getUsername());
        exsUser.setEmail(user.getEmail());
        return userRepository.save(exsUser);
    }

    public void DeleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    
}
