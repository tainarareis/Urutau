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
		private final Result result;
		private final UserDAO userDAO;
		
		public UserController() {
			this(null,null);
		}
		
		@Inject
		public UserController(Result result, UserDAO userDAO) {
			this.result = result;
			this.userDAO = userDAO;
		}
		
		@Get
		@Path("/register")
		public void register() {
		}
		
		@Post
		@Path("/register")
		public void register(User user) {
		}

}
