package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        for(int i = 1; i<5; i++) {
            String name = "Georg"+i;
            String lastName = "Velickiy";
            byte age = (byte)(28+i);
            User user = new User(name, lastName, age);
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        userService.removeUserById(2L);
        List<User> list = userService.getAllUsers();
        for(User user:list){
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
