package rubberduck.org.sportinksystem.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rubberduck.org.sportinksystem.core.applicationservice.user.model.RegisterRequest;
import rubberduck.org.sportinksystem.core.applicationservice.user.model.RegisterResponse;
import rubberduck.org.sportinksystem.core.applicationservice.user.usecase.create.RegisterUsecase;

@RestController
@RequestMapping("/user")
public class UserController {
    private final RegisterUsecase registerUsecase;

    public UserController(RegisterUsecase registerUsecase) {
        this.registerUsecase = registerUsecase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(registerUsecase.execute(request), HttpStatus.CREATED);
    }
}
