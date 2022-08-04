package com.capgemini.fs.coindashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {
    @GetMapping("/HelloWorld")
    String helloWorld(){
      return "Hello World!";

  }
}
