package Tareas.tareas.Repository;

import Tareas.tareas.Entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Integer> {
    Optional<State> findByName(String name);
}
