package com.amigoscode.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //this annotation must always be on top of the API or the controller class - this enables the use of
//HTTP request methods: GET, POST, PUT, DELETE
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(path = "cars")
    public void addCar(@RequestBody Car car) { //this annotation means that the user can input their payload in the body
        //of their request generated through Postman
        carService.registerNewCar(car);
    }

    @GetMapping(path = "cars")
    public List<Car> getCars() {
        return carService.getAllCars();
    }

    @GetMapping(path = "cars/{id}")
    public Car getCarById(@PathVariable("id") Integer carId) {
        return carService.getCarById(carId);
    }

    @DeleteMapping(path = "cars/{id}")
    public void deleteCarById(@PathVariable("id") Integer carId) {
        carService.deleteCar(carId);
    }

    @PutMapping(path = "cars/{id}") //these last 3 methods all have the same URL path however, they are different request
    //methods: Get, Delete, and Put so depending on what you're trying to achieve, the URL doesn't matter
    public void updateCar(@PathVariable("id") Integer carId, @RequestBody Car update) {
        carService.updateCar(carId, update);
    }
}
