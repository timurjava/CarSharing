package carsharing.services;

import carsharing.datalayer.car.Car;
import carsharing.datalayer.company.Company;
import carsharing.datalayer.customer.Customer;
import carsharing.datalayer.customer.DBCustomerDAO;

import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private boolean isWorking = true;
    private int step = 0;
    private ManagerMenu managerMenu = new ManagerMenu();
    private CustomerMenu customerMenu = new CustomerMenu();
    DBCustomerDAO dbCustomerDAO;
    public void start(String url) {
        Scanner scanner = new Scanner(System.in);
        dbCustomerDAO = new DBCustomerDAO(url);
        while(isWorking) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            System.out.print("> ");
            step = scanner.nextInt();
            switch (step) {
                case 3:
                    createCustomer();
                    break;
                case 2:
                    chooseCustomerMenu(url);
                    break;
                case 1:
                    managerMenu.start(url);
                    break;
                case 0:
                    isWorking = false;
                    break;
                default:
                    isWorking = false;
                    System.out.println("Мы не работаем с этим");
            }
            System.out.println("");
        }
    }

    public void chooseCustomerMenu(String url) {
        Scanner scanner = new Scanner(System.in);
        int step= 0;
        List<Customer> customers =  dbCustomerDAO.findAll();
        System.out.println();
        if(dbCustomerDAO.findAll().isEmpty()) {
            System.out.println("The customer list is empty!");
        }else {
            System.out.println("Choose a customer:");
            customers.stream().forEach(customer -> {
                System.out.println(customer.getId() + ". " + customer.getName());
            });
            System.out.println("0. Back");
            System.out.print("> ");
            step = scanner.nextInt();
            if(step != 0) {
                customerMenu.start(url, step - 1, customers);
            }
        }

    }
    public void createCustomer() {
        Scanner scanner = new Scanner(System.in);
        String Customer;
        System.out.println("Enter the customer name:");
        System.out.print("> ");
        Customer = scanner.nextLine();
        dbCustomerDAO.add(new Customer(Customer));
        System.out.println("The customer was added!");
    }
}
