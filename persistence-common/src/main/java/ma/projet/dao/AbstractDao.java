package ma.projet.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import ma.projet.util.HibernateUtil;

public abstract class AbstractDao<T> implements IDao<T> {
    private final Class<T> entityType;
    private final String persistenceUnit;

    protected AbstractDao(Class<T> entityType, String persistenceUnit) {
        this.entityType = Objects.requireNonNull(entityType);
        this.persistenceUnit = Objects.requireNonNull(persistenceUnit);
    }

    @Override
    public T create(T entity) {
        Objects.requireNonNull(entity);
        return inTransaction(manager -> {
            manager.persist(entity);
            return entity;
        });
    }

    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity);
        return inTransaction(manager -> manager.merge(entity));
    }

    @Override
    public Optional<T> findById(Object id) {
        Objects.requireNonNull(id);
        return read(manager -> Optional.ofNullable(manager.find(entityType, id)));
    }

    @Override
    public List<T> findAll() {
        return read(manager -> manager.createQuery(
                "select entity from " + entityType.getSimpleName() + " entity", entityType)
                .getResultList());
    }

    @Override
    public void delete(T entity) {
        Objects.requireNonNull(entity);
        inTransaction(manager -> {
            T managed = manager.contains(entity) ? entity : manager.merge(entity);
            manager.remove(managed);
            return null;
        });
    }

    protected final <R> R read(Function<EntityManager, R> operation) {
        EntityManager manager = HibernateUtil.getEntityManagerFactory(persistenceUnit).createEntityManager();
        try (manager) {
            return operation.apply(manager);
        }
    }

    protected final <R> R inTransaction(Function<EntityManager, R> operation) {
        EntityManager manager = HibernateUtil.getEntityManagerFactory(persistenceUnit).createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try (manager) {
            try {
                transaction.begin();
                R result = operation.apply(manager);
                transaction.commit();
                return result;
            } catch (RuntimeException exception) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw exception;
            }
        }
    }
}
