package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.Role;
import web.model.User;
import web.service.SecurityService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ShowPageController {
    private UserService userService;
    private SecurityService securityService;

    public ShowPageController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public String loginPage() {
        if(securityService.getListRoles().isEmpty()){
           securityService.addRole("ROLE_ADMIN");
           securityService.addRole("ROLE_USER");
        }
        if(userService.getListUser().isEmpty()){
            User user = new User("Admin@mail.net", "root", "Admin", "Adminov", 30);
            Set<Role> role = new HashSet<>();
            role.add(securityService.getRoleByName("ROLE_ADMIN"));
            user.setRoles(role);
            userService.addUser(user);
        }
        return "/login";}

    @GetMapping(value = "admin")
    public String showAdminPage(){
        return "admin";
    }

    @GetMapping("/user")
    public String showUserPage(){
        return "user";
    }
}
