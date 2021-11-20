package ua.training.cruise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.repository.UserRepository;


@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable Long id){
        return userRepository.findById(id).get();
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        User byId = userRepository.findById(id).get();
        byId.setBalance(user.getBalance());
        return userRepository.save(byId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }



}
