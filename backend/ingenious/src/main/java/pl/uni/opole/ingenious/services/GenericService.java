package pl.uni.opole.ingenious.services;

import org.springframework.stereotype.Service;
import pl.uni.opole.ingenious.repositories.GenericRepository;

import java.util.List;

@Service
public abstract class GenericService<T> {
    protected final GenericRepository<T> repository;

    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public T add(T entity) {
        return repository.save(entity);
    }

    public T edit(T entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
