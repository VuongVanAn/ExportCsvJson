package com.javatechie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javatechie.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
