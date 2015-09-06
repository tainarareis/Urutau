package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.controller.model.User;
import com.modesteam.urutau.controller.model.system.EmailManager;
import com.modesteam.urutau.controller.dao.UserDAO;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class UserController {
	
		private static final Logger logger = LoggerFactory.getLogger(UserController.class);
		@Inject
		private Result result;
		@Inject
		private UserDAO userDAO;
		@Inject
		private EmailManager emailManager;
		
		@Get
		@Path("/register")
		public void register() {
		}
		
		@Get
		@Path("/showSignInSucess")
		public void showSignInSucess() {	
		}
		
		/**
		 * Method to confirm user based on email
		 * @param confirmCode is a code
		 * @param userId is an id of user
		 * @throws NoSuchAlgorithmException
		 * @throws UnsupportedEncodingException
		 */
		@Get
		@Path("/confirmEmail/{userId}/{confirmCode}")
		public void confirmEmail(String confirmCode, long userId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
			logger.info("Entering in confirmEmail method");
			User user = new User();
			user.setId(userId);
			user=userDAO.find(user);
			String loginEncrypted = codeOfConfirmation(user.getLogin());
			if(user.isConfirmed()){
				result.redirectTo(IndexController.class).index();
			} else {
				if(confirmCode.equalsIgnoreCase(loginEncrypted)) {
					user.setConfirmed(true);
					userDAO.update(user);
				} else {
					
				}
			}
		}
		/**
		 * Method to register another user in system.
		 * @param user is an user of model class.
		 * @return
		 * @throws EmailException
		 * @throws NoSuchAlgorithmException
		 * @throws UnsupportedEncodingException
		 */
		@Post
		@Path("/register")
		public boolean register(User user) throws EmailException, NoSuchAlgorithmException, UnsupportedEncodingException {
			if(user.getEmail() == null || user.getLogin() == null || user.getName() == null || user.getPasswordVerify() == null) {
				result.include("mensagem", "Campos obrigatórios nao preenchidos!");
				result.redirectTo(IndexController.class).index();
			}else{
				if(userDAO.verifyUser(user)==1) {
					result.include("mensagem", "Login já utilizado");
					result.redirectTo(IndexController.class).index();
					return true;
				}else if(userDAO.verifyUser(user)==2){
					result.include("mensagem", "Email já utilizado");
					result.redirectTo(IndexController.class).index();
					return true;
				}else{
					if(user.getPassword().equalsIgnoreCase(user.getPasswordVerify())==true) {
						user.setPasswordVerify(null);
						String message = "localhost:8080/Urutau/confirmEmail/"+user.getId()+"/"+codeOfConfirmation(user.getLogin());
						emailManager.newEmail("Cadastro do urutau", user, message);
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
		/**
		 * Simple encrypt
		 * 
		 * @param user
		 * @return
		 * @throws NoSuchAlgorithmException 
		 * @throws UnsupportedEncodingException 
		 */
		private String codeOfConfirmation(String toEncrypt) throws NoSuchAlgorithmException, 
			UnsupportedEncodingException {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(toEncrypt.getBytes("UTF-8"));
			 
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			String password = hexString.toString();
			return password;
		}
}
