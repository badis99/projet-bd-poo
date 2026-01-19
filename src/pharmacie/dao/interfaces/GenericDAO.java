package pharmacie.dao.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Generic DAO interface defining standard CRUD operations.
 * 
 * @param <T>  The entity type
 * @param <ID> The primary key type
 */
public interface GenericDAO<T, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    /**
     * Saves the entity. If it's new (ID is null or 0), inserts it. Otherwise
     * updates it.
     */
    void save(T entity);

    void delete(ID id);
}
