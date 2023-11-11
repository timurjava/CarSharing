package carsharing.services;

import carsharing.datalayer.car.Car;
import carsharing.datalayer.car.DBCarDAO;
import carsharing.datalayer.company.Company;
import carsharing.datalayer.company.DBCompanyDAO;
import carsharing.datalayer.customer.Customer;
import carsharing.datalayer.customer.DBCustomerDAO;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu {

    private boolean isWorking = true;
    private int step = 0;
    private int j = 0;
    DBCustomerDAO dbCustomerDAO;
    DBCarDAO dbCarDAO;
    DBCompanyDAO dbCompanyDAO;

    public void start (String url, int id, List<Customer> customers) {
        Scanner scanner = new Scanner(System.in);
        dbCustomerDAO = new DBCustomerDAO(url);
        dbCarDAO = new DBCarDAO(url);
        dbCompanyDAO = new DBCompanyDAO(url);
        System.out.println();
        isWorking = true;
        while(isWorking) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            System.out.print("> ");
            step = scanner.nextInt();
            switch (step) {
                case 3:
                    rentedCar(customers.get(id));
                    break;
                case 2:
                    returnCar(customers.get(id));
                    break;
                case 1:
                    rentCar(customers.get(id));
                    break;
                case 0:
                    isWorking = false;
                    break;
                default:
                    isWorking = false;
                    System.out.println("Мы не работаем с этим");
            }
            System.out.println();
        }

    }

    public void rentedCar(Customer customer) {
        if(customer.getRented_car_id() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            System.out.println("Your rented car:");
            Car car = dbCarDAO.findById(customer.getRented_car_id());
            System.out.println(car.getName());
            System.out.println("Company:");
            System.out.println(dbCompanyDAO.findById(car.getId()).getName());
        }
    }
    public void returnCar(Customer customer) {
        if(customer.getRented_car_id() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            dbCustomerDAO.update(customer);
            customer.setRented_car_id(0);
            System.out.println("You've returned a rented car!");
        }
    }

    public void rentCar(Customer customer) {
        j = 0;
        if(customer.getRented_car_id() != 0) {
            System.out.println("You've already rented a car!");
        }else {
            Scanner scanner = new Scanner(System.in);
            int step= 0;
            List<Company> companies =  dbCompanyDAO.findAll();
            if(dbCompanyDAO.findAll().isEmpty()) {
                System.out.println("The company list is empty!");
            }else {
                System.out.println("Choose the company:");
                companies.stream().forEach(company -> {
                    System.out.println(company.getId() + ". " + company.getName());
                });
                System.out.println("0. Back");
                System.out.print("> ");
                step = scanner.nextInt();

                if(step != 0) {
                    List<Car> cars = dbCarDAO.findByCompainId(step);
                    cars = cars.stream().filter(car -> dbCustomerDAO.findById(car.getId()).getName() == null).toList();
                    if (cars.isEmpty()) {
                        System.out.println("No available cars in the '" + companies.get(step - 1).getName() +"' company");
                    } else {
                        System.out.println("Choose a car:");
                        for (Car car: cars) {
                            System.out.println(++j + ". " + car.getName());
                        }
                        step = scanner.nextInt();
                        if(step != 0) {
                            dbCustomerDAO.updateRent(customer, cars.get(step - 1).getId());
                            customer.setRented_car_id(cars.get(step - 1).getId());
                            System.out.println("You rented '" + cars.get(step - 1).getName() + "'");
                            System.out.println();
                        }
                    }
//                    dbCarDAO.findByCompainId(step).stream().forEach( car -> {
//                        if (dbCustomerDAO.findById(car.getId()).getName() == null) {
//                            System.out.println(car.getName());
//                        }else {
//                            System.out.println("No available cars in the " + companies.get(step).getName() +" company");
//                        }
//                    });
                }
            }
        }
    }
}
