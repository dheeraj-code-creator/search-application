package com.example.restspringbootexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restspringbootexample.entitie.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // story branch
}
