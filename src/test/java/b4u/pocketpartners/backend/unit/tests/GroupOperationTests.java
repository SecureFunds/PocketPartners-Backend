package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.aggregates.GroupOperation;
import b4u.pocketpartners.backend.groups.domain.model.entities.Currency;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Currencies;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class GroupOperationTests {

    @Mock
    private Group mockGroup;

    @Mock
    private Expense mockExpense;

    @Mock
    private Payment mockPayment;

    @Mock
    private UserInformation mockUserInformation;

    @InjectMocks
    private GroupOperation groupOperation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar los mocks
        when(mockGroup.getName()).thenReturn("Test Group");
        when(mockGroup.getCurrencies()).thenReturn(Set.of(new Currency(Currencies.USD)));

        // Crear instancia de GroupOperation usando mocks
        groupOperation = new GroupOperation(mockGroup, mockExpense, mockPayment);
    }

    @Test
    void groupOperationInitializationWithAttributes() {
        // Verificar que los atributos se asignan correctamente al usar el constructor
        assertEquals(mockGroup, groupOperation.getGroup());
        assertEquals(mockExpense, groupOperation.getExpense());
        assertEquals(mockPayment, groupOperation.getPayment());
    }

    @Test
    void groupOperationInitializationWithDefaultConstructor() {
        // Crear instancia de GroupOperation usando el constructor predeterminado
        GroupOperation defaultGroupOperation = new GroupOperation();

        // Verificar que los atributos están en null por defecto
        assertNull(defaultGroupOperation.getGroup());
        assertNull(defaultGroupOperation.getExpense());
        assertNull(defaultGroupOperation.getPayment());
    }

    @Test
    void groupOperationWithGroupMemberAndCurrency() {
        // Crear mocks adicionales para usuario y miembro del grupo
        User mockUser = new User("username", "password");
        GroupMember groupMember = new GroupMember(mockGroup, mockUserInformation);

        // Configuración del mock de UserInformation para devolver el mockUser
        when(mockUserInformation.getUser()).thenReturn(mockUser);

        // Verificar que el usuario pertenece al grupo y que la moneda predeterminada es USD
        assertEquals("Test Group", groupMember.getGroup().getName());
        assertEquals("USD", mockGroup.getCurrencies().iterator().next().getCode().toString());
    }
}