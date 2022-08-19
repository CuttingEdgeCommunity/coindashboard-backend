package com.capgemini.fs.coindashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.capgemini.fs.coindashboard.cacheService.CacheService;
import com.capgemini.fs.coindashboard.controller.exceptionHandler.CoinNotFoundException;
import com.capgemini.fs.coindashboard.controller.exceptionHandler.ServerIsNotRespondingException;
import com.capgemini.fs.coindashboard.controller.utils.ValidTimestamp;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Validated
@RequestMapping("/api")
public class CoinController {
  @Autowired(required = false)
  private final CacheService cacheService;

  public CoinController(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @GetMapping("/coins")
  // future validation required
  ResponseEntity<String> all(
      @RequestParam(defaultValue = "0", required = false)
          @Min(value = 0, message = "Take has to be bigger or equal to 0")
          @Max(value = 300, message = "Take cannot be more than 300")
          int take,
      @RequestParam(defaultValue = "1", required = false)
          @Min(value = 0, message = "Page has to be bigger or equal to 0")
          int page) {
    String coinInfo =
        cacheService.getCoinInfo(take, page).orElseThrow(ServerIsNotRespondingException::new);
    String location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("coins").build().toUriString();
    return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).body(coinInfo);
  }

  @GetMapping("/coins/{name}/marketdata")
  ResponseEntity<String> marketData(
      @PathVariable
          @NotBlank(message = "name cannot be blank")
          @Size(max = 50, message = "name cannot be longer than 50 characters")
          String name,
      @RequestParam(defaultValue = "usd", required = false)
          @NotBlank(message = "vs_currency cannot be blank")
          @Size(max = 10, message = "vs_currency cannot be longer than 10 characters")
          String vs_currency) {
    String coinMarketData =
        cacheService
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
  ResponseEntity<String> details(
      @PathVariable
          @NotBlank(message = "name cannot be blank")
          @Size(max = 50, message = "name cannot be longer than 50 characters")
          String name) {
    String coinDetails =
        cacheService.getCoinDetails(name).orElseThrow(() -> new CoinNotFoundException(name));
    String location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("coins/{name}").build().toUriString();
    return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).body(coinDetails);
  }

  @GetMapping("coins/{name}/chart")
  ResponseEntity<String> chart(
      @PathVariable @NotBlank @Size(max = 50) String name,
      @RequestParam(defaultValue = "0", required = false) @ValidTimestamp long chart_from,
      @RequestParam(defaultValue = "0", required = false) @ValidTimestamp long chart_to) {
    if (!(chart_from < chart_to)) {
      return ResponseEntity.badRequest().body("chart_to cannot be before chart_from");
    }
    String chart =
        cacheService
            .getChart(name, chart_from, chart_to)
            .orElseThrow(() -> new CoinNotFoundException(name));
    String location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("coins/{name}/chart")
            .build()
            .toUriString();
    return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).body(chart);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    return new ResponseEntity<>(
        "not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
