package br.csi.farmasys.model.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
