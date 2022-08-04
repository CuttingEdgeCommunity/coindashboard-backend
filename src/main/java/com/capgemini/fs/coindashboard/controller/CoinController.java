package com.capgemini.fs.coindashboard.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.capgemini.fs.coindashboard.controller.exceptionHandler.CoinNotFoundException;
import com.capgemini.fs.coindashboard.service.CoinService;
import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoinController {

  private final CoinRepository repository;
  private final CoinModelAssembler assembler;

  @Autowired(required = false)
  private final CoinService coinService;

  public CoinController(
      CoinRepository repository, CoinModelAssembler assembler, CoinService coinService) {
    this.repository = repository;
    this.assembler = assembler;
    this.coinService = coinService;
  }

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/coins")
  CollectionModel<EntityModel<Coin>> all() {

    List<EntityModel<Coin>> coins =
        coinService.getCoins().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

    return CollectionModel.of(coins, linkTo(methodOn(CoinController.class).all()).withSelfRel());
    // return CoinService.getCoins();
  }
  // end::get-aggregate-root[]
  @PostMapping("/coins")
  Coin newCoin(@RequestBody Coin newCoin) {
    return repository.save(newCoin);
  }

  @GetMapping("/coins/{name}")
  EntityModel<Coin> one(@PathVariable String name) {
    // cache entry
    Coin coin =
        coinService
            .getCoinByName(name) //
            .orElseThrow(() -> new CoinNotFoundException(name));

    return assembler.toModel(coin);
  }

  @PutMapping("/coins/{name}")
  Coin replaceCoin(@RequestBody Coin newCoin, @PathVariable String name) {
    return repository
        .findById(name)
        .map(
            coin -> {
              coin.setName(newCoin.getName());
              coin.setHistoricalData(newCoin.getHistoricalData());
              return repository.save(coin);
            })
        .orElseGet(
            () -> {
              newCoin.setName(name);
              return repository.save(newCoin);
            });
  }

  @DeleteMapping("/coins/{name}")
  void deleteCoin(@PathVariable String name) {
    repository.deleteById(name);
  }
}
