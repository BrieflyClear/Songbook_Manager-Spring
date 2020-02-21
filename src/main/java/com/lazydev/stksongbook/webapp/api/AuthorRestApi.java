package com.lazydev.stksongbook.webapp.api;

import com.lazydev.stksongbook.webapp.manager.AuthorManager;
import com.lazydev.stksongbook.webapp.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorRestApi {

    @Autowired
    private AuthorManager manager;

    @GetMapping("/get")
    public Iterable<Author> getAll(){
        return manager.findAll();
    }

    @GetMapping("/get/id/{id}")
    public Optional<Author> getById(@PathVariable("id") Long id) {
        return manager.findById(id);
    }

    @GetMapping("/get/name/{name}")
    public Iterable<Author> getByName(@PathVariable("name") String name){
        return manager.findByName(name);
    }

    @PostMapping   // Add mapping?
    public Author addAuthor(@RequestBody Author author) {
        return manager.save(author);
    }

    @PutMapping   // Add mapping?
    public Author updateAuthor(@RequestBody Author author) {
        return manager.save(author);
    }

    @DeleteMapping   // Add mapping?
    public void deleteAuthor(@RequestParam Long id) {
        manager.deleteById(id);
    }
}