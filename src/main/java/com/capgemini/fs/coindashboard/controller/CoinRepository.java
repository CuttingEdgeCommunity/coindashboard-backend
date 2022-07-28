package com.capgemini.fs.coindashboard.controller;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
