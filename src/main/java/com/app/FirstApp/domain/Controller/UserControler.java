package com.app.FirstApp.domain.Controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.io.InputStream;
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

import com.app.FirstApp.domain.Entity.*;

import com.app.FirstApp.domain.SaveMethodes.StatusConstant;
import com.app.FirstApp.domain.SaveMethodes.VerifXML;
import com.app.FirstApp.domain.Services.FileUoladService;
import com.app.FirstApp.domain.Services.HistoriqueSfImplem;
import com.app.FirstApp.domain.Services.ShipmentFileServiImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@Autowired
	private ShipmentFileServiImpl shipmentFileServiImpl;

	@Autowired
	private HistoriqueSfImplem historiqueSfImplem;

	@Autowired
	private VerifXML verifXML;

	@Autowired
	private FileUoladService fileUoladService;


	private static Logger logger = LoggerFactory.getLogger(UserControler.class);



	@GetMapping("users")
	public ResponseEntity<List<UserResp>> getAllUsers() {
		/*String RoleSerched = "consulter_users";
		Boolean Verif = userRolesService.getRoleUser(request, RoleSerched);
		System.out.println("controlller : "+Verif);
		if (!Verif)
			throw new RuntimeException("permission denied");*/
		return ResponseEntity.ok(UserServiceImpl.getUsers());

	}

	@PostMapping("/users/save")
	public ResponseEntity<UserResp> SaveUsers(@RequestBody User user) {
		System.out.println("user" + user);
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
	public ResponseEntity<UserResp> updateEmployee(HttpServletRequest request,@Validated @RequestBody User userReq) {
		return ResponseEntity.ok(UserServiceImpl.updateUser(userReq));

	}

	@DeleteMapping("users/delete/{email}")
	public Map<String, Boolean> deleteUser(
			@PathVariable(value = "email") String userEmail) {

		UserServiceImpl.deletUser(userEmail);
		Map<String, Boolean> response = new HashMap<>(); // to create that employee deleted and status true (juste
															// traja3 message deleted)
		response.put("UserDeleted", true);
		return response;
	}
	/* ================================= SHipmentFile ========================= */
	/*@PostMapping("/users/saveSF/{emailUser}")
	public String saveShipmentFile(HttpServletRequest request,
								   @RequestBody ShipmentFile shipmentFile,
								   @RequestParam("file") MultipartFile file,
								   @PathVariable (value = "emailUser") String emailUser){
		String RoleSerched = "consulter_users";
		Boolean Verif = userRolesService.getRoleUser(request, RoleSerched);
		if (!Verif)
			throw new RuntimeException("permission denied");

		ShipmentFile sf=shipmentFileServiImpl.saveShipmentFile(shipmentFile,emailUser);
		return "L'ajout du ShipmentFile est réussi";
	}*/
	@PostMapping("/users/uploadSf/{emailUser}")
	public ResponseEntity<String> uploadData(   @PathVariable (value = "emailUser") String emailUser,@RequestParam("file") MultipartFile file) throws Exception {
		if (file == null) {
			throw new RuntimeException("You must select the a file for uploading");
		}
ShipmentFile verifExist=shipmentFileServiImpl.getShipmentFile((file.getOriginalFilename()));
		if(verifExist !=null) throw new RuntimeException("Upload echoué  ShipmentFile  "+file.getOriginalFilename() +" deja existe ");
		//fileUoladService.SendToKms(file);

		fileUoladService.FirstUpload(file);
		Boolean VerifXmlFile=verifXML.validateXMLSchema("F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\ValidXsd\\ShipmentFile.xsd","F:\\Spring_boot\\traveaux\\PFE_Auth\\src\\main\\resources\\ShipmentFiles\\"+file.getOriginalFilename());
		if(!VerifXmlFile) throw new RuntimeException("xml  est invalid ");



		String originalName = file.getOriginalFilename();
		String name = file.getName();
		ShipmentFile sfadded=new ShipmentFile();
		sfadded.setName(originalName);
		sfadded.setType_compteur("Gaz");
		sfadded.setStatus(StatusConstant.Status1);

		// Do processing with uploaded file data in Service layer
		ShipmentFile sfUploaded=shipmentFileServiImpl.saveShipmentFile(sfadded, emailUser);

		HistoriqSF hsSF=new HistoriqSF();
		hsSF.setNomStatus(StatusConstant.Status2);
		hsSF.setRaison(StatusConstant.Raison2);
		HistoriqSF hsAdded =historiqueSfImplem.SaveHistoriq(hsSF,originalName);

		return ResponseEntity.ok().body("ShipmentFile : "+sfUploaded.getName()+" est ajouter avec succé ");
	}

	/* ========================= ShipmentFile ===============================*/

	/*@PostMapping("/users/addUseSf")
	public ResponseEntity<String> addUserToSf(HttpServletRequest request,@RequestBody DataAddSf dataAddSf){
		String RoleSerched = "consulter_users";
		Boolean Verif = userRolesService.getRoleUser(request, RoleSerched);
		if (!Verif)
			throw new RuntimeException("permission denied");
		System.out.println("dataAddSf :"+dataAddSf);
		return ResponseEntity.ok(shipmentFileServiImpl.addUserToShipmentFile(dataAddSf.getEmail(),dataAddSf.getName()));


	}*/

	      /* ================= historique Sf ===============*/

@GetMapping("/users/getSf/{name}")
	public ResponseEntity<ShipmentFile> getSf(@PathVariable (value = "name") String name){
		return ResponseEntity.ok(shipmentFileServiImpl.getShipmentFile(name));

}
@GetMapping("/users/getSfStat/{status}")
public ResponseEntity<List<ShipmentFile>> getSfWithStatus(@PathVariable (value = "status") String status){
	return ResponseEntity.ok(shipmentFileServiImpl.getSFWithStatus(status));
}

@GetMapping("/users/getListSf")
	public ResponseEntity<List<ShipmentFile>> getAllSfipmentFile(){
		return ResponseEntity.ok(shipmentFileServiImpl.getAllSHipmentFile());
}




	    /* ================= end  historique Sf ===============*/
	/* ================================== end Sf ===============================*/
}
