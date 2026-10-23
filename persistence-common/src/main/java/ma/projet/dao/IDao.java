package ma.projet.dao;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    T create(T entity);

    T update(T entity);

    Optional<T> findById(Object id);

    List<T> findAll();

    void delete(T entity);
}
