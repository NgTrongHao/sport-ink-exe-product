package rubberduck.org.sportinksystem.core.applicationservice.user.usecase.create;

import rubberduck.org.sportinksystem.core.applicationservice.InputBoundary;
import rubberduck.org.sportinksystem.core.applicationservice.user.model.RegisterRequest;
import rubberduck.org.sportinksystem.core.applicationservice.user.model.RegisterResponse;

public interface RegisterUsecase extends InputBoundary<RegisterRequest, RegisterResponse> {
}
