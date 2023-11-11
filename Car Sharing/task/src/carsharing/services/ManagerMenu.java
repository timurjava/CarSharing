package carsharing.services;

import carsharing.datalayer.company.Company;
import carsharing.datalayer.company.DBCompanyDAO;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private boolean isWorking = true;
    private int step = 0;
    DBCompanyDAO dbCompanyDAO;

    CompanyMenu companyMenu = new CompanyMenu();
    String url;


    public void start(String url) {
        Scanner scanner = new Scanner(System.in);
        dbCompanyDAO = new DBCompanyDAO(url);
        isWorking = true;
        this.url = url;
        while(isWorking) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            System.out.print("> ");
            step = scanner.nextInt();
            switch (step) {
                case 2:
                    createCompany();
                    break;
                case 1:
                    companyList();
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

    public void companyList() {
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
                companyMenu.start(url, step - 1, companies);
            }
        }
    }

    public void createCompany() {
        Scanner scanner = new Scanner(System.in);
        String company;
        System.out.println("Enter the company name:");
        System.out.print("> ");
        company = scanner.nextLine();
        dbCompanyDAO.add(new Company(company));
        System.out.println("The company was created!");
    }

}
