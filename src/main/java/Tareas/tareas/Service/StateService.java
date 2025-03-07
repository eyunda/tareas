package Tareas.tareas.Service;

import Tareas.tareas.DTO.StateDTO;
import Tareas.tareas.Repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<StateDTO> getAllStates() {
        return stateRepository.findAll().stream()
                .map(state -> new StateDTO(state.getId(), state.getName()))
                .collect(Collectors.toList());
    }
}
