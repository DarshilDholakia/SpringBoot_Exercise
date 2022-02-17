package com.amigoscode.car;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> { //RowMapper is a functional interface (has 1 abstract method)
    @Override
    public Car mapRow(ResultSet resultSet, int rowNum) throws SQLException { //what do the inputs into mapRow mean?
        return new Car(resultSet.getInt("id"), //.getInt method retrieves the value of the designated column
                //in the current row of this ResultSet object
                resultSet.getString("regnumber"),
                Brand.valueOf(resultSet.getString("brand")), //converting the string back to an enum after
                //it has been obtained from the sql table because the constructor takes in an enum value for brand
                resultSet.getDouble("price")
        );
    }
}
