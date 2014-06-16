package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.tools.Hasher;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/user/register")
	public void registerUser() {
		String hashed = Hasher.hashString("matthew");
		System.out.println(hashed);
	}
}
