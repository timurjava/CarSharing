package carsharing.services;

import carsharing.datalayer.car.Car;
import carsharing.datalayer.car.DBCarDAO;
import carsharing.datalayer.company.Company;

import java.util.List;
import java.util.Scanner;

public class CompanyMenu {
    private boolean isWorking = true;
    private int step = 0;
    DBCarDAO dbCarDAO;

    public void start (String url, int id, List<Company> companies) {
        Scanner scanner = new Scanner(System.in);
        dbCarDAO = new DBCarDAO(url);
        System.out.println("'" + companies.get(id).getName() + "'" + " company:");
        isWorking = true;
        while(isWorking) {
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            System.out.print("> ");
            step = scanner.nextInt();
            switch (step) {
                case 2:
                    createCar(companies.get(id).getId());
                    break;
                case 1:
                    carList(companies.get(id).getId());
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

    public void createCar(int companyId) {
        Scanner scanner = new Scanner(System.in);
        String car;
        System.out.println("Enter the car name:");
        System.out.print("> ");
        car = scanner.nextLine();
        dbCarDAO.add(new Car(car,companyId));
        System.out.println("The car was created!");
    }
    public void carList(int company) {
        int j = 0;
        List<Car> allCars =  dbCarDAO.findAll().stream()
                .filter(car -> car.getCompany_id() == company).toList();
        if(allCars.isEmpty()) {
            System.out.println("The car list is empty!");
        }else {
            System.out.println("Car list:");
            for (Car car:allCars) {
                if(car.getCompany_id() == company) {
                    System.out.println(++j + ". " + car.getName());
                }
            }
        }
    }
}
