package BonApp.BonApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.UnauthorizedException;
import BonApp.BonApp.payload.LoginSuccessfullPayload;
import BonApp.BonApp.payload.NewIndirizzoPayload;
import BonApp.BonApp.payload.NewUserPayload;
import BonApp.BonApp.payload.RegistrationPayload;
import BonApp.BonApp.payload.UserLoginPayload;
import BonApp.BonApp.service.UsersService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UsersService userService;

	@Autowired
	JWTTools jwtTools;

	@Autowired
	PasswordEncoder bcrypt;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Valid RegistrationPayload registrationPayload) {
		registrationPayload.getNewUserPayload().setPassword(bcrypt.encode(registrationPayload.getNewUserPayload().getPassword()));
	    User created = userService.save(registrationPayload);
	    return created;
	}

	@PostMapping("/login")
	public LoginSuccessfullPayload login(@RequestBody UserLoginPayload body) {

		User user = userService.findByEmail(body.getEmail());

		if (bcrypt.matches(body.getPassword(), user.getPassword())) {
			String token = jwtTools.createToken(user);
			return new LoginSuccessfullPayload(token);
		} else {
			throw new UnauthorizedException("Credenziali non valide");
		}
	}
}
