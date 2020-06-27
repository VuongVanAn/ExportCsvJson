package com.javatechie.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javatechie.model.User;
import com.javatechie.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ServletContext context;
	
	@GetMapping("/")
	public String getUSers(Model model) {
		List<User> users = service.findAll();
		model.addAttribute("users", users);
		return "users";
	}
	
	@GetMapping("/createJSON")
	public void createJson(HttpServletRequest request, HttpServletResponse response) {
		List<User> users = service.findAll();
		boolean isFlag = service.createJson(users, context);
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "users.json");
			fileDownload(fullPath, response, "users.json");
		}
	}
	
	@GetMapping("/createCSV")
	public void createCsv(HttpServletRequest request, HttpServletResponse response) {
		List<User> users = service.findAll();
		boolean isFlag = service.createCsv(users, context);
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "users.csv");
			fileDownload(fullPath, response, "users.csv");
		}
	}

	private void fileDownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		if (file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename=" + fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

}
