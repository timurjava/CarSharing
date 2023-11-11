package carsharing.datalayer.car;

import carsharing.datalayer.CarSharingDao;
import carsharing.datalayer.DbClient;
import carsharing.datalayer.company.Company;
import org.h2.jdbcx.JdbcDataSource;

import java.util.ArrayList;
import java.util.List;

public class DBCarDAO implements CarSharingDao<Car> {

    static final String JDBC_DRIVER = "org.h2.Driver";
    String DB_URL ;
    private static final String SELECT_ALL = "SELECT * FROM CAR";
    private static final String SELECT_CAR = "SELECT * FROM CAR WHERE id = %d";
    private static final String SELECT_CAR_BY_COMAPAIN = "SELECT * FROM CAR WHERE  COMPANY_ID = %d";
    private static final String INSERT_DATA = "INSERT INTO CAR  (Name, COMPANY_ID) VALUES ('%s', %d)";
    private final DbClient dbClient;

    public DBCarDAO(String url) {
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
    public List<Car> findAll() {
        return  dbClient.selectForListCars(SELECT_ALL);
    }

    @Override
    public Car findById(int id) {
        return dbClient.selectCar(String.format(
                SELECT_CAR, id));
    }

    @Override
    public void add(Car car) {
        dbClient.run(String.format(
                INSERT_DATA, car.getName(),car.getCompany_id()));
    }

    @Override
    public void update(Car developer) {

    }

    @Override
    public void deleteById(int id) {

    }
    public List<Car> findByCompainId(int id) {
        return dbClient.selectForListCars(String.format(
                SELECT_CAR_BY_COMAPAIN, id));
    }
}
