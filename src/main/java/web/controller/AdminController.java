package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.SecurityService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private SecurityService securityService;

    public AdminController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping(value = "/user")
    public String listUsers(Model model){
        model.addAttribute("listUsers", userService.getListUser());
        return "users";
    }

    @PostMapping(value = "/user")
    public String listUsers(){return "redirect:/user";}

    @GetMapping(value = "/user/add")
    public String showAdd(Model model) {
        User user = new User();
        model.addAttribute("add", true);
        model.addAttribute("user", user);
        return "user-edit";
    }
    @PostMapping(value = "/user/add")
    public String addUser(@ModelAttribute("user") User user, @ModelAttribute("checkAdminRole") String checkAdminRole,
                          BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "registration";
        }
        if(userService.getUserByLogin(user.getUsername()) != null){
            model.addAttribute("loginError", true);
            return "registration";
        }
        Set<Role> roles = new HashSet<>();
        roles.add(securityService.getRoleByName("ROLE_USER"));
        if(!checkAdminRole.equals("")) {
            roles.add(securityService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin/user";
    }
    @GetMapping(value = {"/user/{id}/edit"})
    public String showEdit(Model model, @PathVariable long id) {
        User user = userService.getUserById(id);
        Set<Role> roles = user.getRoles();
        String checkAdminRole;
        if(roles.size()==2){
            checkAdminRole = "on";
        } else { checkAdminRole = null; }
        model.addAttribute("checkAdminRole", checkAdminRole);
        model.addAttribute("add", false);
        model.addAttribute("user", user);
        return "user-edit";
    }
    @PostMapping(value = {"/user/{id}/edit"})
    public String updateUser(@PathVariable long id,
                             @ModelAttribute("user") User user, @ModelAttribute("checkAdminRole") String checkAdminRole) {
        user.setId(id);
        Set<Role> roles = new HashSet<>();
        roles.add(securityService.getRoleByName("ROLE_USER"));
        if(!checkAdminRole.equals("")) {
            roles.add(securityService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping(value = {"/user/{id}/delete"})
    public String deleteUser(@PathVariable long id) {
        userService.remove(id);
        return "redirect:/admin/user";
    }
}