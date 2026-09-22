package br.csi.farmasys.model.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Fornecedor findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
