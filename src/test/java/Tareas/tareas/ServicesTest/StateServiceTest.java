package Tareas.tareas.ServicesTest;


import Tareas.tareas.DTO.StateDTO;
import Tareas.tareas.Entity.State;
import Tareas.tareas.Repository.StateRepository;
import Tareas.tareas.Service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateService stateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStates() {
        State state1 = new State("Por Hacer");
        state1.setId(1);
        State state2 = new State("En Progreso");
        state2.setId(2);

        when(stateRepository.findAll()).thenReturn(Arrays.asList(state1, state2));

        List<StateDTO> states = stateService.getAllStates();

        assertEquals(2, states.size());
        verify(stateRepository, times(1)).findAll();
    }
}
