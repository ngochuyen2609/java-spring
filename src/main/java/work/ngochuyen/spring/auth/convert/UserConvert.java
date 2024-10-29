package work.ngochuyen.spring.auth.convert;

import org.springframework.stereotype.Component;
import work.ngochuyen.spring.auth.dto.UserDTO;
import work.ngochuyen.spring.auth.dto.UserInfoDTO;
import work.ngochuyen.spring.auth.entity.User;

@Component
public class UserConvert {
    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRefreshToken(user.getRefreshToken());
        userDTO.setRefreshTokenExpired(user.getRefreshTokenExpired());
        return userDTO;
    }

    public UserInfoDTO toUserInfoDTO(User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(user.getUserId());
        userInfoDTO.setUsername(user.getUsername());
        return userInfoDTO;
    }
}