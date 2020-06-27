package com.javatechie.service;

import java.util.List;

import javax.servlet.ServletContext;

import com.javatechie.model.User;

public interface UserService {

	List<User> findAll();

	boolean createJson(List<User> users, ServletContext context);

	boolean createCsv(List<User> users, ServletContext context);

}
