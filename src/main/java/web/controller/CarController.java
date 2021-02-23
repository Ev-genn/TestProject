package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CarController {

    @GetMapping("/cars")
    public String printCar(@RequestParam(required = false) Integer count, Model model){
        Car kia = new Car("KIA","stinger",2018);
        Car bmv = new Car("BMV", "520", 2018);
        Car audi = new Car("AUDI", "A6", 2018);
        Car vw = new Car("VW","Golf",2018);
        Car volvo = new Car("Volvo","s90",2018);

        List<Car> list = new ArrayList<>();
        list.add(kia);
        list.add(bmv);
        list.add(audi);
        list.add(vw);
        list.add(volvo);
        if(count != null && count<5) {
            list=list.stream().limit(count).collect(Collectors.toList());
        }
        model.addAttribute("cars",list);

        return "car";
    }
}
