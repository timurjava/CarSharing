package carsharing.datalayer.customer;

import carsharing.datalayer.CarSharingDao;
import carsharing.datalayer.DbClient;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class DBCustomerDAO implements CarSharingDao<Customer> {

    static final String JDBC_DRIVER = "org.h2.Driver";
    String DB_URL ;
    private static final String SELECT_ALL = "SELECT * FROM CUSTOMER";
    private static final String SELECT_BY_CAR_ID = "SELECT * FROM CUSTOMER  WHERE RENTED_CAR_ID = %d";
    private static final String INSERT_DATA = "INSERT INTO CUSTOMER  (Name, RENTED_CAR_ID) VALUES ('%s', %d)";
    private static final String INSERT_DATA_NULL = "INSERT INTO CUSTOMER  (Name, RENTED_CAR_ID) VALUES ('%s', NULL)";
    private static final String RETURN_CAR = "UPDATE  CUSTOMER  SET RENTED_CAR_ID = NULL WHERE id = %d";
    private static final String RENT_CAR = "UPDATE  CUSTOMER  SET RENTED_CAR_ID = %d WHERE id = %d";

    private final DbClient dbClient;

    public DBCustomerDAO(String url) {
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
    public List<Customer> findAll() {
        return dbClient.selectForListCustomer(SELECT_ALL);
    }

    @Override
    public Customer findById(int id) {
        return dbClient.selectCustomer(String.format(
                SELECT_BY_CAR_ID, id));
    }

    @Override
    public void add(Customer customer) {
        dbClient.run(String.format(
                INSERT_DATA_NULL, customer.getName()));
    }

    @Override
    public void update(Customer customer) {
        dbClient.run(String.format(
                RETURN_CAR, customer.getId()));
    }

    @Override
    public void deleteById(int id) {

    }

    public void updateRent(Customer customer, int carId) {
        dbClient.run(String.format(
                RENT_CAR, carId, customer.getId()));
    }

}
