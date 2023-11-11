package carsharing.datalayer.company;

import carsharing.datalayer.CarSharingDao;
import carsharing.datalayer.DbClient;
import carsharing.datalayer.car.Car;
import org.h2.jdbcx.JdbcDataSource;

import java.util.ArrayList;
import java.util.List;

public class DBCompanyDAO implements CarSharingDao<Company> {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
     String DB_URL ;
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT_COMPANY = "SELECT * FROM COMPANY  WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO COMPANY (Name) VALUES ('%s')";

    private final DbClient dbClient;

    public DBCompanyDAO(String url) {
        DB_URL = url;
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        dbClient = new DbClient(dataSource);
        try{
            Class.forName(JDBC_DRIVER);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Company> findAll() {
        return dbClient.selectForListCompanies(SELECT_ALL);
    }

    @Override
    public Company findById(int id) {
        return dbClient.selectCompany(String.format(
                SELECT_COMPANY, id));
    }

    @Override
    public void add(Company company) {
        dbClient.run(String.format(
                INSERT_DATA, company.getName()));
    }

    @Override
    public void update(Company company) {

    }

    @Override
    public void deleteById(int id) {

    }

}
