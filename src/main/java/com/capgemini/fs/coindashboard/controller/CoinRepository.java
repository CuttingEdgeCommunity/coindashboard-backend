package com.capgemini.fs.coindashboard.controller;

import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// test to throw away
@Repository
public interface CoinRepository extends JpaRepository<Coin, String> {}
