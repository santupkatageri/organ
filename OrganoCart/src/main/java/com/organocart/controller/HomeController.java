package com.organocart.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.organocart.DAO.CartDAOServices;
import com.organocart.DAO.ProductDAO;
import com.organocart.model.Cart;
import com.organocart.model.UserRegCred;

@Controller
public class HomeController 
{
	@Autowired
	ProductDAO pdao;
	
	@Autowired
	CartDAOServices cartdao;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
	@RequestMapping(value={"/","/index"})
	public ModelAndView landingPage(HttpSession httpSession)
	{
		logger.info("Inside Landingpage in home controller");
		String productlist = pdao.viewProduct();
		ModelAndView mv=new ModelAndView("index");
		mv.addObject("productlist", productlist);
		logger.info("End of Landingpage in home controller");
		return mv;
	}
	@RequestMapping("/Contactus")
	public ModelAndView contactUsPage()
	{
		logger.info("Inside contact us page in home controller");
		ModelAndView mv=new ModelAndView("Contactus");
		mv.addObject("success",null);
		mv.addObject("fail",null);
		logger.info("End of contact us page in home controller");
        return mv;
    }
	@RequestMapping("/Aboutus")
	public String aboutUsPage()
	{
		logger.info("Inside About us page in home controller");
		return "Aboutus";	
    }
	
}
