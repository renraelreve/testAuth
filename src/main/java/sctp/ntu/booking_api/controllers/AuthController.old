package sctp.ntu.booking_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @GetMapping("/login")
  public ResponseEntity<?> authenticateUser(@RequestHeader("Authorization") String authHeader) {
    try {
      // Extract the Basic Auth credentials from the header
      String[] credentials = extractAndDecodeHeader(authHeader);
      String username = credentials[0];
      String password = credentials[1];

      // Authenticate the user using the AuthenticationManager
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      // Set the authentication in the SecurityContext
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Return a success response
      return ResponseEntity.ok("User authenticated successfully");

    } catch (Exception e) {
      // Return an unauthorized response if authentication fails
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }

  private String[] extractAndDecodeHeader(String authHeader) {
    byte[] base64Token = authHeader.substring(6).getBytes();
    byte[] decoded = Base64.getDecoder().decode(base64Token);
    String token = new String(decoded);
    String[] parts = token.split(":");
    if (parts.length != 2) {
      throw new RuntimeException("Invalid basic authentication token");
    }
    return parts;
  }
}
