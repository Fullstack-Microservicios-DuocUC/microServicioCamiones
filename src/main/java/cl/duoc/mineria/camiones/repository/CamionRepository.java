package cl.duoc.mineria.camiones.repository;

import cl.duoc.mineria.camiones.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CamionRepository extends JpaRepository<Camion, Long> {
    Optional<Camion> findByPatente(String patente);
}