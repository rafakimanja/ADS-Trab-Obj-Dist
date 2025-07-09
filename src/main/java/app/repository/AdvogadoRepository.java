package app.repository;

import app.model.Advogado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvogadoRepository extends JpaRepository<Advogado, Long> {
    Optional<Advogado> findByNumeroOAB(String numeroOAB);
}
