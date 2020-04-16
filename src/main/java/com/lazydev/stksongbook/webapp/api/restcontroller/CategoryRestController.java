package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.api.mappers.CategoryMapper;
import com.lazydev.stksongbook.webapp.api.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryRestController {

  private CategoryService service;
  private CategoryMapper modelMapper;
  private SongMapper songMapper;

  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryDTO> getAll() {
    return service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public CategoryDTO getById(@PathVariable("id") Long id) {
    Optional<Category> object = service.findById(id);
    return object.map(this::convertToDto).orElse(null);
  }

  @GetMapping("/name/{name}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryDTO> getByName(@PathVariable("name") String name) {
    return service.findByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}/songs")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<SongDTO> getSongsByCategoryId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    return tmp.map(category -> category.getSongs().stream().map(songMapper::songToSongDTO).collect(Collectors.toList()))
        .orElse(null);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public CategoryDTO create(@RequestBody CategoryDTO categoryDto) {
    return convertToDto(service.save(convertToEntity(categoryDto)));
  }

  @PutMapping("/id/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDto) {
    var category = convertToEntity(categoryDto);
    category.setId(id);
    service.save(category);
  }

  @DeleteMapping("/id/{id}")
  public void delete(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

  public CategoryDTO convertToDto(Category category) {
    return modelMapper.categoryToCategoryDTO(category);
  }

  public Category convertToEntity(CategoryDTO categoryDto) {
    return modelMapper.categoryDTOToCategory(categoryDto);
  }
}
