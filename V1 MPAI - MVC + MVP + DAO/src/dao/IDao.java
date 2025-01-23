package dao;

import java.io.IOException;
import java.util.List;

public interface IDao<T> {
    void insert(T entity) throws IOException;
    void update(T entity) throws IOException;
    void delete(T entity) throws IOException;
    T getById(int id);
    List<T> getAll();
}