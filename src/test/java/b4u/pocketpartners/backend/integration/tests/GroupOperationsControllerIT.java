package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.GroupOperation;
import b4u.pocketpartners.backend.groups.domain.model.commands.AddGroupOperationCommand;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupOperationsByGroupIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupOperationsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupOperationByGroupIdAndExpenseIdAndPaymentId;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupOperationByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupOperationCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupOperationQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.AddGroupOperationResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GroupOperationsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupOperationCommandService groupOperationCommandService;

    @MockBean
    private GroupOperationQueryService groupOperationQueryService;

    private GroupOperation operation;

    @BeforeEach
    void setUp() throws Exception {
        operation = new GroupOperation();
        setField(operation, "id", 1L); // Asignar ID usando reflexión
    }

    @Test
    void shouldAddGroupOperationSuccessfully() throws Exception {
        // Arrange
        AddGroupOperationResource resource = new AddGroupOperationResource(1L, 2L, 3L);

        when(groupOperationCommandService.handle(any(AddGroupOperationCommand.class))).thenReturn(1L);
        when(groupOperationQueryService.handle(any(GetGroupOperationByGroupIdAndExpenseIdAndPaymentId.class)))
                .thenReturn(Optional.of(operation));

        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJNYXJjbzEwIiwiaWF0IjoxNzMxODAxODQwLCJleHAiOjE3MzI0MDY2NDB9.LJjzQ2T5vkWea9O85vqjrNQieQBTNXMnGw7BWTOEBeacesBXi0jjM_b1uCqdKtgN";

        // Act & Assert
        mockMvc.perform(post("/api/v1/groupOperations")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldGetAllGroupOperationsSuccessfully() throws Exception {
        // Arrange
        when(groupOperationQueryService.handle(any(GetAllGroupOperationsQuery.class)))
                .thenReturn(List.of(operation));

        // Act & Assert
        mockMvc.perform(get("/api/v1/groupOperations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void shouldGetGroupOperationByIdSuccessfully() throws Exception {
        // Arrange
        GroupOperation operationMock = Mockito.mock(GroupOperation.class);
        when(operationMock.getId()).thenReturn(1L);

        GetGroupOperationByIdQuery query = new GetGroupOperationByIdQuery(1L);

        when(groupOperationQueryService.handle(Mockito.eq(query)))
                .thenReturn(Optional.of(operationMock));

        // Act & Assert
        mockMvc.perform(get("/api/v1/groupOperations/{groupOperationId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldGetGroupOperationsByGroupIdSuccessfully() throws Exception {
        // Arrange
        GroupOperation operationMock = Mockito.mock(GroupOperation.class);
        when(operationMock.getId()).thenReturn(1L);

        GetAllGroupOperationsByGroupIdQuery query = new GetAllGroupOperationsByGroupIdQuery(1L);

        when(groupOperationQueryService.handle(Mockito.eq(query)))
                .thenReturn(List.of(operationMock));

        // Act & Assert
        mockMvc.perform(get("/api/v1/groupOperations/groupId/{groupId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void shouldReturnBadRequestIfGroupOperationNotFound() throws Exception {
        // Arrange
        GetGroupOperationByIdQuery query = new GetGroupOperationByIdQuery(1L);
        when(groupOperationQueryService.handle(Mockito.eq(query))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/groupOperations/{groupOperationId}", 1L))
                .andExpect(status().isBadRequest());
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Class<?> targetClass = target.getClass();
        Field field = null;

        // Buscar el campo en la jerarquía de clases
        while (targetClass != null) {
            try {
                field = targetClass.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                targetClass = targetClass.getSuperclass(); // Continuar con la clase padre
            }
        }

        if (field == null) {
            throw new NoSuchFieldException("Field " + fieldName + " not found in class hierarchy.");
        }

        field.setAccessible(true);
        field.set(target, value);
    }
}