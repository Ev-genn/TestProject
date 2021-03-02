package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "user")
    public String listUsers(Model model){ ;
        model.addAttribute("listUsers", userService.getListUser());
        return "users";
    }

    @GetMapping(value = "/user/add")
    public String showAdd(Model model) {
        User user = new User();
        model.addAttribute("add", true);
        model.addAttribute("user", user);
        return "user-edit";
    }
    @PostMapping(value = "/user/add")
    public String addUser(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/user";
    }
    @GetMapping(value = {"/user/{id}/edit"})
    public String showEdit(Model model, @PathVariable long id) {
        User user = userService.getUserById(id);
        model.addAttribute("add", false);
        model.addAttribute("user", user);
        return "user-edit";
    }
    @PostMapping(value = {"/user/{id}/edit"})
    public String updateUser(@PathVariable long id,
                             @ModelAttribute("user") User user) {
            user.setId(id);
            userService.updateUser(user);
            return "redirect:/user";
    }

    @GetMapping(value = {"/user/{id}/delete"})
    public String deleteUser(@PathVariable long id) {
        userService.remove(id);
        return "redirect:/user";
    }
}
