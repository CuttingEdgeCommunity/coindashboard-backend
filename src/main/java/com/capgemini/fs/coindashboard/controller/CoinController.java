package com.capgemini.fs.coindashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.capgemini.fs.coindashboard.controller.exceptionHandler.CoinNotFoundException;
import com.capgemini.fs.coindashboard.controller.exceptionHandler.ServerIsNotRespondingException;
import com.capgemini.fs.coindashboard.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CoinController {
  @Autowired(required = false)
  private final CoinService coinService;

  public CoinController(CoinService coinService) {
    this.coinService = coinService;
  }

  @GetMapping("/coins")
  // future validation required
  ResponseEntity<String> all(
      @RequestParam(defaultValue = "0", required = false) int take,
      @RequestParam(defaultValue = "1", required = false) int page) {
    String coinInfo =
        coinService.getCoinInfo(take, page).orElseThrow(ServerIsNotRespondingException::new);
    String location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("coins").build().toUriString();
    return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).body(coinInfo);
  }

  @GetMapping("/coins/{name}/marketdata")
  ResponseEntity<String> marketData(
      @PathVariable String name,
      @RequestParam(defaultValue = "usd", required = false) String vs_currency) {
    String coinMarketData =
        coinService
            .getCoinMarketData(name, vs_currency) //
            .orElseThrow(() -> new CoinNotFoundException(name));
    String location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("coins/{name}/marketdata")
            .build()
            .toUriString();
    return ResponseEntity.status(CREATED)
        .header(HttpHeaders.LOCATION, location)
        .body(coinMarketData);
  }

  @GetMapping("/coins/{name}")
  ResponseEntity<String> details(@PathVariable String name) {
    String coinDetails =
        coinService.getCoinDetails(name).orElseThrow(() -> new CoinNotFoundException(name));
    String location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("coins/{name}").build().toUriString();
    return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).body(coinDetails);
  }

  @GetMapping("coins/{name}/chart")
  ResponseEntity<String> chart(
      @PathVariable String name,
      @RequestParam(defaultValue = "0", required = false) long chart_from,
      @RequestParam(defaultValue = "o", required = false) long chart_to) {
    String chart =
        coinService
            .getChart(name, chart_from, chart_to)
            .orElseThrow(() -> new CoinNotFoundException(name));
    String location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("coins/{name}/chart")
            .build()
            .toUriString();
    return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).body(chart);
  }
}
