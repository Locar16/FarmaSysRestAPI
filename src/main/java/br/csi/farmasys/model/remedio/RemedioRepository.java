package br.csi.farmasys.model.remedio;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RemedioRepository extends JpaRepository<Remedio, Long> {
    Remedio findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
