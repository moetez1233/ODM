package com.app.FirstApp.domain.Controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.app.FirstApp.domain.Entity.Role;
import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Entity.UserResp;
import com.app.FirstApp.domain.Repository.UserRepo;
import com.app.FirstApp.domain.Security.UserRolesService;
import com.app.FirstApp.domain.Services.UserServiceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserControler {
	@Autowired
	private UserServiceImpl UserServiceImpl;
@Autowired
private UserRolesService userRolesService;
	@GetMapping("users")
	public ResponseEntity<List<UserResp>> getAllUsers(HttpServletRequest request) {
		String RoleSerched="consulter_users";
		Boolean Verif =userRolesService.getRoleUser(request,RoleSerched);
		if(!Verif)throw new RuntimeException("permission denied");
		return ResponseEntity.ok(UserServiceImpl.getUsers());

	}

	@PostMapping("/users/save")
	public ResponseEntity<UserResp> SaveUsers(@RequestBody User user) {
		System.out.println("user"+user);
		URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toString()));
		return ResponseEntity.created(uri).body(UserServiceImpl.saveUser(user));

	}

	@GetMapping("token/refresh")
	public void refreshtoken(HttpServletRequest request, HttpServletResponse response)
			throws StreamWriteException, DatabindException, IOException {
		String authorisationHeader = request.getHeader(AUTHORIZATION);// Header=AUTHORIZATION
		if (authorisationHeader != null && authorisationHeader.startsWith("ODM ")) {
			try {

				String refresh_Token = authorisationHeader.substring("ODM ".length());// we just need the token without
																						// ODM
				Algorithm algotithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algotithm).build();// veif token
				DecodedJWT decodedJWT = verifier.verify(refresh_Token);// decode token
				String userName = decodedJWT.getSubject();
				User user = UserServiceImpl.getUser(userName);

				String access_token = JWT.create().withSubject(user.getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algotithm);

				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_Token", refresh_Token);
				tokens.put("userName", user.getEmail());
				// tokens.put("Roles",
				// user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).toString());

				response.setContentType("APPLICATION_JSON_VALUE");
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				System.out.println("error login : " + e.getMessage());
				response.setHeader("error ", e.getMessage());
				// response.sendError(Forbidden.value());

				Map<String, String> error_Message = new HashMap<>();
				error_Message.put("error", e.getMessage());

				response.setContentType("APPLICATION_JSON_VALUE");
				new ObjectMapper().writeValue(response.getOutputStream(), error_Message);

			}

		} else {
			throw new RuntimeException("Refresh token is missing ");

		}

	}
	
	
	// update employee
		 @PutMapping("users/update")
		    public ResponseEntity<UserResp> updateEmployee(
		         @Validated @RequestBody User userReq) {
			return ResponseEntity.ok(UserServiceImpl.updateUser(userReq));
		     
		    }
		 @DeleteMapping("users/delete/{email}")
		 public Map<String, Boolean> deleteUser (@PathVariable(value = "email") String userEmail) {
			 UserServiceImpl.deletUser(userEmail);
		        Map<String, Boolean> response = new HashMap<>(); // to create that employee deleted and status true (juste traja3  message deleted)
		        response.put("User Deleted", true);
		        return response;
		 }
}
