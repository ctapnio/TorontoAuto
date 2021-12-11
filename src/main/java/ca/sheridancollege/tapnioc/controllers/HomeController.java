package ca.sheridancollege.tapnioc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.tapnioc.beans.Car;
import ca.sheridancollege.tapnioc.beans.Manufacturer;
import ca.sheridancollege.tapnioc.database.DatabaseAccess;

@Controller
public class HomeController {
//	@Lazy
	@Autowired
	private DatabaseAccess da;

	@GetMapping("/")
	public String goHome(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		m.addAttribute("manufacturer", new Manufacturer());
		m.addAttribute("manufacturerList", da.getManufacturers());
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String password) {
		da.addUser(username, password);
		Long userId = da.findUserAccount(username).getUserId();
		da.addRole(userId, Long.valueOf(1));
		da.addRole(userId, Long.valueOf(2));
		return "redirect:/";
	}
	
	@GetMapping("/cars/view-all")
	public String viewAllCars(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		m.addAttribute("manufacturer", new Manufacturer());
		m.addAttribute("manufacturerList", da.getManufacturers());
		return "cars/view-all";
	}
	
	@GetMapping("/user/enlist")
	public String secureIsert(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		m.addAttribute("manufacturer", new Manufacturer());
		m.addAttribute("manufacturerList", da.getManufacturers());
		return "cars/user/enlist";
	}
	
	@GetMapping("/user/update")
	public String secureUpdate(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		m.addAttribute("manufacturer", new Manufacturer());
		m.addAttribute("manufacturerList", da.getManufacturers());
		return "cars/user/update";
	}
	
	@GetMapping("admin/delete")
	public String secureDelete(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		m.addAttribute("manufacturer", new Manufacturer());
		m.addAttribute("manufacturerList", da.getManufacturers());
		return "cars/admin/delete";
	}
	
	@GetMapping("/enlist")
	public String insert(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("manufacturer", new Manufacturer());
		m.addAttribute("manufacturerList", da.getManufacturers());
		// m.addAttribute("carList", da.getCars());
		return "cars/user/enlist";
	}

	@PostMapping("/enlistCar")
	public String insertCar(Model m, @RequestParam int manufacturerID, @RequestParam String model,
			@RequestParam Long year, @RequestParam String color, @RequestParam Double price) {
		da.insertCar(manufacturerID, model, year, color, price);
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		return "cars/user/enlist";
	}

	@GetMapping("/update")
	public String update(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		return "cars/user/update";
	}

	@GetMapping("/editCar/{carID}")
	public String editCar(Model m, @PathVariable int carID) {
		Car car = da.getCarByCarID(carID).get(0);
		m.addAttribute("car", car);
		m.addAttribute("carList", da.getCars());
		return "cars/user/update";
	}

	@PostMapping("/updateCar")
	public String updateCar(Model m, @ModelAttribute Car car) {
		da.updateCar(car.getCarID(), car.getModel(), car.getPrice());
		m.addAttribute("Car", new Car());
		m.addAttribute("carList", da.getCars());
		return "cars/user/update";
	}

	@GetMapping("/delete")
	public String delete(Model m) {
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		return "cars/admin/delete";
	}

	@PostMapping("/deleteCar")
	public String deleteCar(Model m, @RequestParam int carID) {
		da.deleteCar(carID);
		m.addAttribute("car", new Car());
		m.addAttribute("carList", da.getCars());
		return "cars/admin/delete";
	}
	
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "error/permission-denied";
	}
	
	

}



