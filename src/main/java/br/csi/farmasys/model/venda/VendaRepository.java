package br.csi.farmasys.model.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    Venda findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
