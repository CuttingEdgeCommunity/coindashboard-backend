package com.capgemini.fs.coindashboard.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CoinModelAssembler implements RepresentationModelAssembler<Coin, EntityModel<Coin>> {

  @Override
  public EntityModel<Coin> toModel(Coin coin) {

    return EntityModel.of(
        coin, //
        linkTo(methodOn(CoinController.class).one(coin.getName())).withSelfRel(),
        linkTo(methodOn(CoinController.class).all()).withRel("coins"));
  }
}
