package b4u.pocketpartners.backend.users.infrastructure.hashing.bcypt;

import b4u.pocketpartners.backend.users.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}

