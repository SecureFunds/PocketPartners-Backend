package b4u.pocketpartners.backend.groups.application.internal.commandservices;

import b4u.pocketpartners.backend.groups.domain.exceptions.GroupMemberNotFoundException;
import b4u.pocketpartners.backend.groups.domain.exceptions.GroupNotFoundException;
import b4u.pocketpartners.backend.groups.domain.exceptions.ReceiptSaveException;
import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.aggregates.Receipt;
import b4u.pocketpartners.backend.groups.domain.model.commands.CreateReceiptCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.services.ReceiptCommandService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupMemberRepository;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupRepository;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.ReceiptRepository;
import b4u.pocketpartners.backend.operations.domain.exceptions.UserNotFoundException;
import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@Service
public class ReceiptCommandServiceImpl implements ReceiptCommandService {
    private final ReceiptRepository receiptRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    public ReceiptCommandServiceImpl(ReceiptRepository receiptRepository, GroupRepository groupRepository, UserRepository userRepository, GroupMemberRepository groupMemberRepository) {
        this.receiptRepository = receiptRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    /**
     * Handles the creation of a receipt.
     * @param command the command to create a receipt.
     * @return the id of the created receipt.
     */
    @Override
    public Long handle(CreateReceiptCommand command) {
        // Verificar si el grupo existe
        Group group = groupRepository
                .findById(command.groupId()).orElseThrow(()
                        -> new GroupNotFoundException(command.groupId()));

        // Verificar si el usuario existe
        User user = userRepository
                .findById(command.userId()).orElseThrow(()
                        -> new UserNotFoundException(command.userId()));

        // Buscar el GroupMember correspondiente
        GroupMember groupMember = groupMemberRepository
                .findByGroupIdAndUserInformationId(group.getId(), user.getId())
                .orElseThrow(() -> new GroupMemberNotFoundException(group.getId(), user.getId()));

        // Crear y guardar el recibo
        Receipt receipt = new Receipt(command, groupMember);

        try {
            receiptRepository.save(receipt);
        } catch (DataIntegrityViolationException e) {
            throw new ReceiptSaveException("Data integrity violation while saving receipt", e);
        } catch (Exception e) {
            throw new ReceiptSaveException("Error while saving receipt", e);
        }

        return receipt.getId();
    }

}
