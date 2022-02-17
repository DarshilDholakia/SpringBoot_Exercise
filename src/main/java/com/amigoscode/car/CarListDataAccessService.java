package com.amigoscode.car;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("list")
public class CarListDataAccessService implements CarDAO {

    private List<Car> carsDB;

    public CarListDataAccessService() { //constructor
        this.carsDB = new ArrayList<>(); //what does this line mean??
    }

    @Override
    public Car selectCarById(Integer id) {
        //find car with valid id (if not valid then throw exception)
        //if null then throw exception
        return carsDB
                .stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Car with ID not found!")); //if id is null or not in list DB
    }

    @Override
    public List<Car> selectAllCars() {
        return carsDB;
    }

    @Override
    public int insertCar(Car car) {
        carsDB.add(car);
        return 1; //trick to make testing easier by avoiding void method
    }

    @Override
    public int deleteCar(Integer id) {
        for (Car car: carsDB) {
            if (car.getId() == id) {
                carsDB.remove(car);
                return 1;
            }
        }
        throw new IllegalStateException("Car with ID not found, so cannot be deleted!");
    }

    @Override
    public int updateCar(Integer id, Car update) {
        for (int i = 0; i < carsDB.size(); i++) {
            if (carsDB.get(i).getId() == id) {
                carsDB.set(i, update);
                return 1;
            }
        }
//        throw new IllegalStateException("Car with ID not found, so cannot be updated!");
        return 0;
    }
}
