package com.javatechie.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.model.User;
import com.javatechie.repository.UserRepository;
import com.opencsv.CSVWriter;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repo;

	@Override
	public List<User> findAll() {
		return (List<User>) repo.findAll();
	}

	@Override
	public boolean createJson(List<User> users, ServletContext context) {
		String filePath = context.getRealPath("/resources/reports");
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}
		
		File file = new File(filePath + "/" + File.separator + "users.json");
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(file, users);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	public boolean createCsv(List<User> users, ServletContext context) {
		String filePath = context.getRealPath("/resources/reports");
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}
		
		File file = new File(filePath + "/" + File.separator + "users.csv");
		try {
			FileWriter fileWriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fileWriter);
			List<String[]> data = new ArrayList<>();
			data.add(new String[] {"First Name", "Last Name", "Email", "Phone Number"});
			for (User user : users) {
				data.add(new String[] {user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber()});
			}
			writer.writeAll(data);
			writer.close();
			return true;
			
		} catch (Exception e) {
			return false;
		}		
	}

}
