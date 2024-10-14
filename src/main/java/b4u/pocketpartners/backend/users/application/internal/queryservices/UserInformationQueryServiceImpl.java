package b4u.pocketpartners.backend.users.application.internal.queryservices;

import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import b4u.pocketpartners.backend.users.domain.model.queries.GetAllUsersInformationQuery;
import b4u.pocketpartners.backend.users.domain.model.queries.GetUserInformationByIdQuery;
import b4u.pocketpartners.backend.users.domain.model.queries.GetUserInformationByUserIdQuery;
import b4u.pocketpartners.backend.users.domain.services.UserInformationQueryService;
import b4u.pocketpartners.backend.users.infrastructure.persistence.jpa.repositories.UserInformationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInformationQueryServiceImpl implements UserInformationQueryService {

    private final UserInformationRepository userInformationRepository;

    public UserInformationQueryServiceImpl(UserInformationRepository userInformationRepository) {
        this.userInformationRepository = userInformationRepository;
    }

    @Override
    public List<UserInformation> handle(GetAllUsersInformationQuery query) {
        return userInformationRepository.findAll();
    }

    @Override
    public Optional<UserInformation> handle(GetUserInformationByIdQuery query) {
        return userInformationRepository.findById(query.userId());
    }

    @Override
    public Optional<UserInformation> handle(GetUserInformationByUserIdQuery query) {
        return userInformationRepository.findByUserId(query.id());
    }
}
