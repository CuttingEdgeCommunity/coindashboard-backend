package com.capgemini.fs.coindashboard.controller;

import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

// test to throw away
public interface CoinRepository extends JpaRepository<Coin, String> {}
