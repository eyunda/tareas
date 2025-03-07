package Tareas.tareas.ControllerTest;

import Tareas.tareas.Controller.StateController;
import Tareas.tareas.DTO.StateDTO;
import Tareas.tareas.Service.StateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StateController.class)
public class StateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StateService stateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllStates() throws Exception {
        StateDTO state1 = new StateDTO(1, "Por Hacer");
        StateDTO state2 = new StateDTO(2, "En Progreso");

        Mockito.when(stateService.getAllStates()).thenReturn(Arrays.asList(state1, state2));

        mockMvc.perform(get("/api/states"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
