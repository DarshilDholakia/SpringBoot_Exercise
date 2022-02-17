package com.amigoscode.car;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("postgres")
public class CarSQLDataAccessService implements CarDAO{

    private JdbcTemplate jdbcTemplate; //injecting JdbcTemplate to our DataAccessService class - as this line stands,
    // this variable has a null value by default

    public CarSQLDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    } //

    @Override
    public Car selectCarById(Integer id) {
        String insertSQL = """
                SELECT * FROM car
                WHERE car.id = ?
                """;
        return jdbcTemplate.query(insertSQL, new CarRowMapper(), id)
                .stream()
                .findFirst()
                .get();
    }

    @Override
    public List<Car> selectAllCars() {

        String insertSQL = """
               SELECT id, regnumber, brand, price 
               FROM car
               """; //Never use SELECT *, instead be specific with which columns you want

//        return jdbcTemplate.query(insertSQL, new CarRowMapper()); //what do the inputs mean into the .query() method?

        List<Car> cars = jdbcTemplate.query(insertSQL, (rs, rowNum) -> {
            System.out.println(rowNum); //this will print 0, 1, 2, 3 in the console as it cycles through each row of the
            //rs
            Car car = new Car(
                    rs.getInt("id"),
                    rs.getString("regnumber"),
                    Brand.valueOf(rs.getString("brand")),
                    rs.getDouble("price")
            );
            return car;
        });
        return cars;
    }

    @Override
    public int insertCar(Car car) {
        String insertSQL = "INSERT INTO car(regnumber, brand, price) VALUES(?, ?, ?)";
        return jdbcTemplate.update(insertSQL,
                car.getRegNumber(),
                car.getBrand().name(), //.name() method used to convert the enum to String as that is the data type which
                //is used to store brand names in sql table (TEXT)
                car.getPrice()); //jdbcTemplate.query() method is to get the data from the DB whereas jdbcTemplate.update()
        // method is to modify DB (this includes deleting, updating or creating)
    }//

    @Override
    public int deleteCar(Integer id) {
        String insertSQL = """
                DELETE FROM car
                WHERE car.id = ?
                """;
        return jdbcTemplate.update(insertSQL, id); //jdbcTemplate.update() returns the number of rows affected (int)

    }

    @Override
    public int updateCar(Integer id, Car update) { //this returns the list in ResponseBody but in order: {id = 2, 3, 4, 1}
        String insertSQL = """
                UPDATE car
                SET (regnumber, brand, price) = (?, ?, ?)
                WHERE car.id = ?
                """;
        return jdbcTemplate.update(insertSQL, update.getRegNumber(), update.getBrand(), update.getPrice(), id);
    }
}
