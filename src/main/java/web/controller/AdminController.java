package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.SecurityService;
import web.service.UserService;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private SecurityService securityService;

    public AdminController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }



    @GetMapping(value = "/users")
    public List<User> getListUsers(){
        return userService.getListUser();
    }

    @GetMapping(value = "/principal")
    public User getPrincipal(Principal principal){
        return userService.getUserByLogin(principal.getName());
    }

    @GetMapping(value = "/{id}/user")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @GetMapping(value = "/roles")
    public List<Role> getAllRoles(){
        return securityService.getListRoles();
    }

    @PostMapping(value = "/add")
    public ResponseEntity addUser(@RequestBody User user){
        if(userService.getUserByLogin(user.getUsername()) != null){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Set<Role> roleUser = new HashSet<>();
        for (Role role:user.getRoles()) {
            Role userRole = securityService.getRoleByName(String.valueOf(role));
            roleUser.add(userRole);
        }
        user.setRoles(roleUser);
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = {"/edit"})
    public void updateUser(@RequestBody User user) {
        boolean byCoding = false;
        if(!user.getPassword().equals("") || user.getPassword() != null){
           byCoding = true;
        }
        Set<Role> roleUser = new HashSet<>();
        for (Role role:user.getRoles()) {
            Role userRole = securityService.getRoleByName(String.valueOf(role));
            roleUser.add(userRole);
        }
        user.setRoles(roleUser);
        userService.updateUser(user, byCoding);
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public void deleteUser(@PathVariable long id) {
        userService.remove(id);
    }
}