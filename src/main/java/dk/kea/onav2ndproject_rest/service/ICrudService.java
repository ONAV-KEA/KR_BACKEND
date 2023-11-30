package dk.kea.onav2ndproject_rest.service;

import java.util.Optional;
import java.util.Set;

public interface ICrudService<T,ID> {
    Set<T> findAll();
    T save(T object);
    void delete(T object);
    void deleteById(ID id);
    Optional<T> findById(ID id);
}
