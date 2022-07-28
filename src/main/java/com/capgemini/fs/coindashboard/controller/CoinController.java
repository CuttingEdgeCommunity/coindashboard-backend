package com.capgemini.fs.coindashboard.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class CoinController {

  private final CoinRepository repository;
  private final CoinModelAssembler assembler;

  public CoinController(CoinRepository repository, CoinModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/coins")
  CollectionModel<EntityModel<Coin>> all() {

    List<EntityModel<Coin>> employees = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(employees, linkTo(methodOn(CoinController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @GetMapping("/coins/{id}")
  EntityModel<Coin> one(@PathVariable String name) {

    Coin coin = repository.findById(name) //
        .orElseThrow(() -> new CoinNotFoundException(name));

    return assembler.toModel(coin);
  }

}