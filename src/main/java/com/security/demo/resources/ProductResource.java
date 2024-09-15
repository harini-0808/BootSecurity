package com.security.demo.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.security.demo.MyUserDetailsService;
import com.security.demo.models.AuthenticationRequest;
import com.security.demo.models.AuthenticationResponse;
import com.security.demo.models.DeleteResponse;
import com.security.demo.models.Product;
import com.security.demo.services.ProductService;
import com.security.demo.util.JwtUtil;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:4200")
public class ProductResource {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	JwtUtil jwtutil;
	
	@Autowired
	ProductService service;
	
	//*******************security/authentication part ***************************************
	
	@GetMapping("/authenticate")
	public String hey() {
		return "hey?";
	}
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		System.out.println(authenticationRequest.getUsername() + authenticationRequest.getPassword());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {

			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtutil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}


	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestBody Product product){
		return ResponseEntity.status(201).body(service.save(product));
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> getProducts(){
		return ResponseEntity.status(200).body(service.getProducts());
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable long id){
		Product product = service.getProductByID(id);
		if(product==null)
		return ResponseEntity.status(404).body("no such product :( ");
		return ResponseEntity.status(200).body(product);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProductByID(@PathVariable long id){
		if(service.deleteProductByID(id))
			return ResponseEntity.status(200).body(new DeleteResponse(true));
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteResponse(false));
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable long id,@RequestBody Product product){
		product.setProductID(id);
		if(service.updateProduct(id, product)==null)
			return ResponseEntity.status(404).body("no such product");
		return ResponseEntity.status(200).body(product);
	}


	

}