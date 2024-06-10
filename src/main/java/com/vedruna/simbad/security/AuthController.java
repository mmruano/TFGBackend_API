package com.vedruna.simbad.security;

import com.vedruna.simbad.persistence.model.Refuge;
import com.vedruna.simbad.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/SimbadAPI/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        if (response == null || "Email o contraseña incorrectos.".equals(response.getMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(response);
    }

    // Manejo global de la excepción personalizada
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /** POST **/
    //Crear usuario:
    @PostMapping(value = "createUser")
    private ResponseEntity<AuthResponse> createUser(@RequestBody User user) {
        AuthResponse response = authService.createUser(
                user.getName(), user.getSurname(), user.getGender(), user.getProvince(),
                user.getPostCode(), user.getEmail(), user.getPhone(), user.getPassword());
        return ResponseEntity.ok(response);
    }

    //Crear refugio:
    @PostMapping(value = "createRefuge")
    private ResponseEntity<AuthResponse> createRefuge(@RequestBody Refuge request) {
        AuthResponse response = authService.createRefuge(
                request.getName(), request.getTypeRefuge(), request.getCif(),
                request.getStreet(), request.getProvince(), request.getPostCode(),
                request.getPhone(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}
