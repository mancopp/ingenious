package pl.uni.opole.ingenious.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.uni.opole.ingenious.services.GenericService;

import java.util.List;

@RestController
public abstract class GenericController<T> {

    protected final GenericService<T> service;

    @Autowired
    protected GenericController(GenericService<T> service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<T> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public T add(@RequestBody T entity) {
        return service.add(entity);
    }

    @PutMapping("/edit")
    public T edit(@RequestBody T entity) {
        return service.edit(entity);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
