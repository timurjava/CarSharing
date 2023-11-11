package carsharing.datalayer;

import carsharing.datalayer.company.Company;

import java.util.List;

public interface CarSharingDao<T> {
    List<T> findAll();
    T findById(int id);
    void add(T t);
    void update(T t);
    void deleteById(int id);
}
