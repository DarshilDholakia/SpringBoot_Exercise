package com.amigoscode.car;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //giving this Service class this @Service annotation automatically creates an instance of class CarService and
//this object is called a bean which is a singleton (can be accessed across different classes without having to create
//instances continuously
public class CarService {

    private CarDAO carDAO;

    public CarService(@Qualifier("postgres") CarDAO carDAO) { //carDAO interface being injected into CarService since that
        //is the interface and this will give us access to all its different implementations (in this case:
        //CarListDataAccessService and CarFileDataAccessService
        this.carDAO = carDAO;
    }

    public void registerNewCar(Car car) {
        // check if reg number is valid and not take
        if (car.getPrice() <= 0 || car.getPrice() == null) {
            throw new IllegalStateException("Car price cannot be 0 or less");
        }

        if (car == null) {
            throw new IllegalStateException("Input cannot be null");
        }

        if (car.getBrand() == null) {
            throw new IllegalStateException("Car Brand cannot be null");
        }

        if (car.getRegNumber() == null) {
            throw new IllegalStateException("Car regNumber cannot be null");
        }

        int result = carDAO.insertCar(car);

        if (result != 1) { //figure out why this is...
            throw new IllegalStateException("Could not save car...");
        }
    }

    public List<Car> getAllCars() {
        return carDAO.selectAllCars();
    }

    public Car getCarById(Integer id) {
        return carDAO.selectCarById(id);
    }

    public int deleteCar(Integer id) {
        int result = carDAO.deleteCar(id);
        return result;
    }

    public int updateCar(Integer id, Car update) {
        int result = carDAO.updateCar(id, update);
        return result;
    }

    //Always include the conditionals which make sure the input is valid in the Service class because we do not want to
    //send a query to the DB which will return an error!
}
