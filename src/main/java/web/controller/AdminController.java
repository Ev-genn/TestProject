package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.security.SecurityService;
import web.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
        Optional<User> userByDb = userService.addUser(user);
        return userByDb.isPresent() ? new ResponseEntity(HttpStatus.CONFLICT) : new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = {"/edit"})
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public void deleteUser(@PathVariable long id) {
        userService.remove(id);
    }
}