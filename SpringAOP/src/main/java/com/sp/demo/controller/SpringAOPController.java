package com.sp.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sp.demo.annotation.LogTimeExecution;

@RestController
public class SpringAOPController {
	
	
	@RequestMapping(value = "/name",method = RequestMethod.GET)
	@LogTimeExecution
	public ResponseEntity<?> home(@RequestParam ("name") String name)
	{
		return ResponseEntity.ok("Welcome "+name);
	}
	
	@GetMapping("/demo")
	@LogTimeExecution
	public ResponseEntity<?>demo(@RequestParam("name")String name){
		
		return ResponseEntity.ok("bye "+" "+name);
	}
}


