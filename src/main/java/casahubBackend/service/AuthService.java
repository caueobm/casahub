package casahubBackend.service;

import casahubBackend.dto.LoginDto;
import casahubBackend.dto.SignupDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String signup(SignupDto signupDto);
}