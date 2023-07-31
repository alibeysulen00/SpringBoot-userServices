package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name
            , @RequestParam String email) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/get")
    public @ResponseBody User getUserById(@RequestParam Integer id){
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateUser(@RequestParam Integer id, @RequestParam String name, @RequestParam String email) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            userRepository.save(user);
            return "Updated";
        }
        return "User not found";
    }

    @PostMapping(path="/delete")
    public @ResponseBody String deleteUserByName(@RequestParam String name) {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                userRepository.delete(user);
                return "User deleted: " + name;
            }
        }
        return "User not found: " + name;
    }

}