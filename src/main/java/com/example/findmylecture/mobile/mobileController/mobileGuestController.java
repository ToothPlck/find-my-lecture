package com.example.findmylecture.mobile.mobileController;

import com.example.findmylecture.dto.RoleDto;
import com.example.findmylecture.dto.UserDto;
import com.example.findmylecture.mobile.mobiledto.MobileRoleDto;
import com.example.findmylecture.mobile.mobiledto.MobileUserDto;
import com.example.findmylecture.model.Role;
import com.example.findmylecture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mobile/")
public class mobileGuestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(value = "login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody MobileUserDto mobileUserDto) throws Exception {
        authenticate(mobileUserDto.getUsername(), mobileUserDto.getPassword());
        UserDto userDto = userService.findByUsername(mobileUserDto.getUsername());

        MobileUserDto userResponse = new MobileUserDto();

        //convert role into roleDto
        MobileRoleDto mobileRoleDto = new MobileRoleDto();
        Role role = userDto.getRole();
        mobileRoleDto.setRoleId(role.getRoleId());
        mobileRoleDto.setRoleName(role.getRoleName());

        userResponse.setUsername(userDto.getUsername());
        userResponse.setMobileRoleDto(mobileRoleDto);

        return ResponseEntity.ok(userResponse);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException badCredentialsException) {
            throw new Exception("Invalid username and password");
        } catch (Exception exception) {
            throw new Exception("An error occurred while authenticating!");
        }
    }

}
