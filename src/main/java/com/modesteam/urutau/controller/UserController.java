package com.modesteam.urutau.controller;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.modesteam.urutau.controller.model.User;
import com.modesteam.urutau.controller.dao.UserDAO;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

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
		
		@Get
		@Path("/showSignInSucess")
		public void showSignInSucess(){
			
		}
		
		@Post
		@Path("/register")
		public boolean register(User user) {
			if(user.getEmail() == null || user.getLogin() == null || user.getName() == null || user.getPasswordVerify() == null){
				result.include("mensagem", "Campos obrigatórios nao preenchidos!");
				result.redirectTo(IndexController.class).index();
			}else{
				if(userDAO.verifyUser(user)==1){
					result.include("mensagem", "Login já utilizado");
					result.redirectTo(IndexController.class).index();
					return true;
				}else if(userDAO.verifyUser(user)==2){
					result.include("mensagem", "Email já utilizado");
					result.redirectTo(IndexController.class).index();
					return true;
				}else{
					if(user.getPassword().equalsIgnoreCase(user.getPasswordVerify())==true){
						user.setPasswordVerify(null);
						userDAO.add(user);
						result.redirectTo(UserController.class).showSignInSucess();
					}else{
						result.include("mensagem", "As senhas não são compatíveis!");
						result.redirectTo(IndexController.class).index();
					}
				}
				}
			return false;
		}
}
