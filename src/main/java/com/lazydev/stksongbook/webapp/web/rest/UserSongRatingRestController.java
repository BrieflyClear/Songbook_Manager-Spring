package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.service.UserSongRatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/ratings")
@AllArgsConstructor
public class UserSongRatingRestController {

  private UserSongRatingMapper mapper;
  private UserSongRatingService service;

  @GetMapping("/user/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserSongRatingDTO> getByUserId(@PathVariable("id") Long userId) {
    return service.findByUserId(userId)
        .stream().map(mapper::map)
        .collect(Collectors.toList());
  }

  @GetMapping("/song/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserSongRatingDTO> getBySongId(@PathVariable("id") Long songId) {
    return service.findBySongId(songId).stream()
        .map(mapper::map)
        .collect(Collectors.toList());
  }

  @GetMapping("/user_song/{userId}/{songId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserSongRatingDTO getByUserIdAndSongId(
      @PathVariable("userId") Long userId, @PathVariable("songId") Long songId) {
    return service.findByUserIdAndSongId(userId, songId)
        .map(mapper::map)
        .orElse(null);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserSongRatingDTO> getAll(
      @RequestParam(value = "greaterThanEqual", required = false) Double greaterValue,
      @RequestParam(value = "lessThanEqual", required = false) Double lessValue,
      @RequestParam(value = "equal", required = false) Double value) {
    List<UserSongRating> list;
    if(greaterValue != null) {
      list = service.findByRatingGreaterThanEqual(greaterValue);
    } else if(lessValue != null) {
      list = service.findByRatingLessThanEqual(lessValue);
    } else if(value != null) {
      list = service.findByRating(value);
    } else {
      list = service.findAll();
    }
    return list.stream()
        .map(mapper::map)
        .collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserSongRatingDTO create(@RequestBody UserSongRatingDTO dto) {
    UserSongRating user = mapper.map(dto);
    var saved = service.save(user);
    return mapper.map(saved);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody UserSongRatingDTO dto) {
    UserSongRating user = mapper.map(dto);
    service.save(user);
  }

  @DeleteMapping
  public void delete(@RequestBody UserSongRatingDTO dto) {
    var obj = mapper.map(dto);
    service.delete(obj);
  }
}