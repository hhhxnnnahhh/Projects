package eventmanagementsystem.services;

public interface CRUD<T> {
    // Create operation
    void create(T entity);

    // Read operation
    T read(int id);

    // Update operation
    void update(int id, T updatedEntity);

    // Delete operation
    void delete(int id);
}

