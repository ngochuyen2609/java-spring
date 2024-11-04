package work.ngochuyen.spring.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import work.ngochuyen.spring.auth.dto.request.LoginRequest;
import work.ngochuyen.spring.auth.dto.request.RegisterRequest;
import work.ngochuyen.spring.auth.dto.reponse.UserDTO;
import work.ngochuyen.spring.auth.entity.User;
import work.ngochuyen.spring.auth.mapper.UserMapper;
import work.ngochuyen.spring.auth.repository.UserRepository;
import work.ngochuyen.spring.common.dto.BaseResponse;
import work.ngochuyen.spring.common.dto.ErrorResponse;
import work.ngochuyen.spring.common.security.JwtService;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${token.refresh.expiration}") // Load token expiration from config
    private long refreshTokenExpirationTime;

    @Autowired
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public BaseResponse<?> login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if(!userOptional.isPresent()){
            return new BaseResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "invalid_params",
                    new ErrorResponse(10, "invalid_username")
            );
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(),
                    "invalid_params", new ErrorResponse(
                    11, "invalid_password"
            ));
        }
        user.setRefreshToken(generateRefreshToken());
        user.setRefreshTokenExpired(
                Instant.now()
                        .plusSeconds(refreshTokenExpirationTime)
                        .toEpochMilli()
        );
        userRepository.save(user);
        UserDTO userDTO = userMapper.toUserDTO(user);
        jwtService.generateToken(userDTO);
        return new BaseResponse<>(HttpStatus.OK.value(), "Login Success", userDTO);
    }

    public BaseResponse<?> register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return new BaseResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "invalid_params",
                    new ErrorResponse(10, "duplicate_username")
            );
        }
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRefreshToken(generateRefreshToken());
        newUser.setRefreshTokenExpired(
                Instant.now()
                        .plusSeconds(refreshTokenExpirationTime)
                        .toEpochMilli()
        );
        userRepository.save(newUser);
        UserDTO userDTO = userMapper.toUserDTO(newUser);
        jwtService.generateToken(userDTO);
        return new BaseResponse<>(HttpStatus.OK.value(), "Registration successful", userDTO);
    }
    public String generateRefreshToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[64]; // 64 bytes = 512 bits
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    public BaseResponse<?> get(String userId) {
        Long id = Long.parseLong(userId);
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return new BaseResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "invalid_params",
                    new ErrorResponse(10, "user_not_found")
            );
        }
        User newUser = userOptional.get();
        UserDTO userInfo = userMapper.toUserDTO(newUser);
        return new BaseResponse<>(HttpStatus.OK.value(), "successful", userInfo);
    }
}
