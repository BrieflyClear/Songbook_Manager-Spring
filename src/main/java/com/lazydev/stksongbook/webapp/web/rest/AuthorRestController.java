package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.service.mappers.AuthorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorRestController {

  private AuthorService service;
  private AuthorMapper authorMapper;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<AuthorDTO> getAll(HttpServletResponse response) {
    return service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public AuthorDTO getById(@PathVariable("id") Long id, HttpServletResponse response) {
    Optional<Author> optAuthor = service.findById(id);
    return optAuthor.map(this::convertToDto).orElse(null);
  }

  @GetMapping("/name/{name}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<AuthorDTO> getByName(@PathVariable("name") String name, HttpServletResponse response) {
    return service.findByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public AuthorDTO create(@RequestBody AuthorDTO authorDto, HttpServletResponse response) {
    //Preconditions.checkNotNull(authorDto);
    Author author = convertToEntity(authorDto);
    Author created = service.save(author);

    //eventPublisher.publishEvent(new ResourceCreated(this, response, created.getId()));
    return convertToDto(created);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody AuthorDTO authorDto) {
    Author author = convertToEntity(authorDto);
    service.save(author);
  }

  @DeleteMapping("/id/{id}")
  public void delete(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

  public AuthorDTO convertToDto(Author author) {
    return authorMapper.map(author);
  }

  public Author convertToEntity(AuthorDTO authorDto) {
    return authorMapper.map(authorDto);
  }
}