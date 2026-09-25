package dg.peffy_backend.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service 
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User CreateUser(User user) {
        return userRepository.save(user);
    }

    public User GetUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
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
