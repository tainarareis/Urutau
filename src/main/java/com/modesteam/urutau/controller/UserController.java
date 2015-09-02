package com.modesteam.urutau.controller;

import javax.inject.Inject;

import com.modesteam.urutau.controller.model.User;
import com.modesteam.urutau.controller.dao.UserDAO;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
@Controller
public class UserController {

		@Inject
		private Result result;
		@Inject
		private UserDAO userDAO;
		
		@Get
		@Path("/register")
		public void register() {
		}
		
		@Post
		@Path("/register")
		public void register(User user) {
			if(user.getPassword().equalsIgnoreCase(user.getPasswordVerify())){
				result.forwardTo(IndexController.class).getClass();
				userDAO.add(user);
			}else{
				result.forwardTo(UserController.class).register();
				
			}
			
		}

}
