package com.vedruna.simbad.security;

import com.vedruna.simbad.jwt.JwtService;
import com.vedruna.simbad.persistence.model.Refuge;
import com.vedruna.simbad.persistence.model.User;
import com.vedruna.simbad.persistence.repository.RefugeRepository;
import com.vedruna.simbad.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefugeRepository refugeRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            return null; // Retorna null cuando las credenciales son incorrectas
        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        Optional<Refuge> refugeOptional = refugeRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            UserDetails userDetails = userOptional.get();
            String token = jwtService.getTokenUser(userDetails);
            return new AuthResponse(token, "Inicio de sesión exitoso.", "user"); // Tipo de usuario: user
        } else if (refugeOptional.isPresent()) {
            UserDetails userDetails = refugeOptional.get();
            String token = jwtService.getTokenRefuge(userDetails);
            return new AuthResponse(token, "Inicio de sesión exitoso.", "refuge"); // Tipo de usuario: refuge
        } else {
            return null; // Retorna null si el usuario o refugio no se encuentra
        }
    }

    public AuthResponse createUser(String name, String surname, String gender, String province,
                                   String postCode, String email, String phone, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }

        User user = User.builder()
                .name(name)
                .surname(surname)
                .gender(gender)
                .province(province)
                .postCode(postCode)
                .email(email)
                .phone(phone)
                .password(passwordEncoder.encode(password))
                .creationDate(LocalDate.now())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getTokenUser(user))
                .build();
    }

    public AuthResponse createRefuge(String name, String typeRefuge, String cif, String street, String province,
                                     String postCode, String phone, String email, String password) {
        if (refugeRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }

        Refuge refuge = Refuge.builder()
                .name(name)
                .typeRefuge(typeRefuge)
                .cif(cif)
                .street(street)
                .province(province)
                .postCode(postCode)
                .phone(phone)
                .email(email)
                .password(passwordEncoder.encode(password))
                .creationDate(LocalDate.now())
                .build();

        refugeRepository.save(refuge);

        return AuthResponse.builder()
                .token(jwtService.getTokenRefuge(refuge))
                .build();
    }
}
