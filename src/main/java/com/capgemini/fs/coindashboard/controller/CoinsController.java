package com.capgemini.fs.coindashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoinsController {

  private final CoinsRepository repository;

  public CoinsController(CoinsRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/coins")
  List<Coins> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @GetMapping("/coins/{name}")
  Coins one(@PathVariable String name) {

    return repository.findById(name)
        .orElseThrow(() -> new CoinsNotFoundException(name));
  }

}