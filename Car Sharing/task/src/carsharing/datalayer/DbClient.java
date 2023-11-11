package carsharing.datalayer;

import carsharing.datalayer.car.Car;
import carsharing.datalayer.company.Company;
import carsharing.datalayer.customer.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbClient {
    private final DataSource dataSource;

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run(String str) {
        try (Connection con = dataSource.getConnection(); // Statement creation
             Statement statement = con.createStatement()
        ) {
            statement.executeUpdate(str); // Statement execution
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public Company select(String query) {
//        List<Company> companies = selectForList(query);
//        if (companies.size() == 1) {
//            return companies.get(0);
//        } else if (companies.size() == 0) {
//            return null;
//        } else {
//            throw new IllegalStateException("Query returned more than one object");
//        }
//    }

    public List<Company> selectForListCompanies(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Company developer = new Company(name, id);
                companies.add(developer);
            }

            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }
    public List<Car> selectForListCars(String query) {
        List<Car> companies = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                int comapnyId = resultSetItem.getInt("COMPANY_ID");
                String name = resultSetItem.getString("name");
                Car developer = new Car(id,name, comapnyId);
                companies.add(developer);
            }

            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }
    public List<Customer>  selectForListCustomer(String query) {
        List<Customer> companies = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                int rented_car_id = resultSetItem.getInt("RENTED_CAR_ID");
                String name = resultSetItem.getString("name");
                Customer customer = new Customer(id,name, rented_car_id);
                companies.add(customer);
            }

            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }
    public Car selectCar(String query) {
        Car car = new Car();
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                int comapnyId = resultSetItem.getInt("COMPANY_ID");
                String name = resultSetItem.getString("name");
                car.setId(id);
                car.setName(name);
                car.setCompany_id(comapnyId);
            }

            return car;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return car;
    }

    public Company selectCompany(String query) {
        Company company = new Company();
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                company.setId(id);
                company.setName(name);
            }

            return company;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return company;
    }
    public Customer selectCustomer(String query) {
        Customer customer = new Customer();
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                int rented_car_id = resultSetItem.getInt("RENTED_CAR_ID");
                customer.setId(id);
                customer.setName(name);
                customer.setRented_car_id(rented_car_id);
            }

            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }
}
