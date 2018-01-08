package com.application.web;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.application.model.Vehicle;
import com.application.model.Service;

@Controller
public class FrontendController {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(HttpServletRequest request, Model model) {
 
        String name = request.getParameter("name");
        if(name!= null) {
        	model.addAttribute("name",name);
        }else {
        	model.addAttribute("name","User");
        }
        model.addAttribute("appName", appName);
        return "home";
    }
    
    @PostMapping("/")
    public String homePost(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
    	
    	String type = request.getParameter("type");
    	String search = request.getParameter("search");
    	
        if(search!= null &&  type != null) {
        	//System.out.println(type);
        	//System.out.println(search);
        	//Perform search of type
        	switch(type) {
        		case "service":{
        			//System.out.println("Service search");
        			response.sendRedirect("/service/?search="+search);
        			break;
        		}
        		case "mot":{
        			System.out.println("MOT search");
        			response.sendRedirect("/mot/?search="+search);
        			break;
        		}
        		case "insurance":{
        			System.out.println("Insurance search");
        			response.sendRedirect("/insurance/?search="+search);
        			break;
        		}
        		case "breakdown":{
        			System.out.println("Breakdown search");
        			response.sendRedirect("/breakdown/?search="+search);
        			break;
        		}
        		case "garages":{
        			System.out.println("Garages search");
        			response.sendRedirect("/garages/?search="+search);
        			break;
        		}
        		default: 
        			response.sendRedirect("/");
        	}
        }
        model.addAttribute("appName", appName);
        return "home";
    }
    @GetMapping("/service")
    public String getService(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		if(search != null) {
			String v_url = "http://localhost:8080/api/vehicle/"+ search;
			String s_url = "http://localhost:8080/api/service";
			//System.out.println("http://localhost:8080/api/vehicle/"+ search);
			RestTemplate template = new RestTemplate();
			Vehicle v = template.getForObject(v_url, Vehicle.class);
			Service[] s = template.getForObject(s_url, Service[].class);
			for (Service x : s) {
				System.out.print(x.toString());
			}
			
			if(v != null) {
				System.out.println(v.toString());
				model.addAttribute("make", v.getMake());
				model.addAttribute("model", v.getModel());
				model.addAttribute("service", new SimpleDateFormat("dd/MM/yyy").format(v.getLast_service()));
				model.addAttribute("mot", v.getLast_mot());
			}else {
				model.addAttribute("not_found", "Sorry there was no vehicle found with:");
			}
		}else {
			model.addAttribute("not_found", "Please Enter a search criteria and try again");
		}  
		model.addAttribute("reg", search.toUpperCase());
		return "results";
    }
    
    @GetMapping("/mot")
    public String getMOT(HttpServletRequest request, Model model) {
    	String search = request.getParameter("search");
		if(search != null) {
			String url = "http://localhost:8080/api/vehicle/"+ search;
			//System.out.println("http://localhost:8080/api/vehicle/"+ search);
			RestTemplate template = new RestTemplate();
			Vehicle v = template.getForObject(url, Vehicle.class);
			if(v != null) {
				System.out.println(v.toString());
				model.addAttribute("make", v.getMake());
				model.addAttribute("model", v.getModel());
				model.addAttribute("service", v.getLast_service());
				model.addAttribute("mot", v.getLast_mot());
			}else {
				model.addAttribute("not_found", "Sorry there was no vehicle found with:");
			}
		}else {
			model.addAttribute("not_found", "Please Enter a search criteria and try again");
		}  
		String reg = search.toUpperCase();
		model.addAttribute("reg", reg);
		return "results";
    }
    
    @GetMapping("/insurance")
    public String getInsurance(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		if(search != null) {
			System.out.println("/insurance/?search=" + search);
		}else {
			model.addAttribute("search", "please enter a value");
		}
    	
		return "home";
    }
    
    @GetMapping("/breakdown")
    public String getBreakdown(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		if(search != null) {
			System.out.println("/breakdown/?search=" + search);
		}else {
			model.addAttribute("search", "please enter a value");
		}
    	
		return "home";
    }
    @GetMapping("/garages")
    public String getGarages(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		if(search != null) {
			System.out.println("/garages/?search=" + search);
		}else {
			model.addAttribute("search", "please enter a value");
		}
    	
		return "home";
    }
}