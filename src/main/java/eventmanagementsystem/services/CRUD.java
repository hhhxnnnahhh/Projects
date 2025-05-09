package eventmanagementsystem.services;

public interface CRUD<T> {
    void create(T entity);

    T read(int id);

    void update(int id, T updatedEntity);

    void delete(int id);
}

