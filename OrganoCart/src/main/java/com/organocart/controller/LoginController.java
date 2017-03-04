package com.organocart.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.organocart.DAO.CartDAOServices;
import com.organocart.DAO.ProductDAO;
import com.organocart.DAO.Userservice;
import com.organocart.model.Cart;
import com.organocart.model.UserRegModel;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@Controller
public class LoginController
{
	@Autowired
	Userservice user;
	
	@Autowired
	ProductDAO pdao;
	
	@Autowired
	CartDAOServices cartdao;
	
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	
	@RequestMapping("/pLogin")
	public String login(HttpSession session) 
	{
		logger.info("Inside login method in login controller");
		session.setAttribute("pId","0");
		logger.info("end of login method in login controller");
		return "Login";
	}
	
	@RequestMapping("/perlogcheck")
	public String prologin(HttpSession session,@RequestParam("getProductId")String id) 
	{
		logger.info("Inside prologin method in login controller");
		session.setAttribute("pId", id);
		logger.info("end of prologin method in login controller");
		return "Login";
	}

	public int getgrandquantity(ArrayList<Cart> items) {
		
		logger.info("Inside getgrandquantity method in login controller");
		int grandquant = 0;
		ListIterator<Cart> itr = items.listIterator();
		while (itr.hasNext()) {
			Cart obj = (Cart) itr.next();
			grandquant = grandquant+obj.getQty();	
		}
		logger.info("end of getgrandquantity method in login controller");
		return grandquant;
	}
	
	
	@RequestMapping("/loginsuccess")
	public String loginsuccess(HttpSession session,Model M) 
	{
		logger.info("Inside login success method in login controller");
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegModel us=user.viewUser(userid);
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String page = "";
		//String productId = " ";
		String role = "ROLE_USER";
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(role)) {
				session.setAttribute("UserLoggedIn", us.getUsername());
				session.setAttribute("UserId", userid);
				int id = cartdao.getCartId(userid);
				//System.out.println("the cart id for "+userid+" is "+id);
				session.setAttribute("Cartid", id);
				String productslist =  pdao.viewProduct();
				ArrayList<Cart> cartitems = cartdao.viewItemsInCart(id);
				session.setAttribute("grandquantity", getgrandquantity(cartitems));
				session.setAttribute("usercart", cartitems);
				if(session.getAttribute("pId").toString().equals("0"))
					page ="redirect:/buyproductpage";
				else
					page="redirect:/addtocart?getProductId="+session.getAttribute("pId").toString();
			}
			else
			{
				session.setAttribute("AdminLoggedIn", us.getUsername());
				session.setAttribute("AdminId", userid);
				page = "redirect:/index";
			}
		}
		logger.info("end of prologin method in login controller");
		return page;
	}
	
}
