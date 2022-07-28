package com.capgemini.fs.coindashboard.controller;

import com.capgemini.fs.coindashboard.controller.Coins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinsRepository extends JpaRepository<Coins, String> {

}
