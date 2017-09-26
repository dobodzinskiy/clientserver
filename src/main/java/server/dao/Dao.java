package server.dao;

import java.util.List;

public interface Dao<T> {

    T persist(T entity);

    List<T> getAll();

}
