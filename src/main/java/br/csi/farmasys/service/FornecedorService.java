package br.csi.farmasys.service;

import br.csi.farmasys.model.fornecedor.Fornecedor;
import br.csi.farmasys.model.fornecedor.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public List<Fornecedor> listar() {
        return this.repository.findAll();
    }

    public Fornecedor getFornecedor(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public Fornecedor getFornecedorUUID(String uuid) {
        return this.repository.findByUuid(UUID.fromString(uuid));
    }

    @Transactional
    public void salvar(Fornecedor fornecedor) {
        this.repository.save(fornecedor);
    }

    @Transactional
    public void atualizar(Fornecedor fornecedor) {
        this.repository.save(fornecedor);
    }

    @Transactional
    public void atualizarUUID(Fornecedor fornecedor) {
        Fornecedor existente = this.repository.findByUuid(fornecedor.getUuid());
        if (existente != null) {
            fornecedor.setId(existente.getId());
            this.repository.save(fornecedor);
        }
    }

    @Transactional
    public void excluir(Long id) {
        this.repository.deleteById(id);
    }

    @Transactional
    public void deletarUUID(String uuid) {
        this.repository.deleteByUuid(UUID.fromString(uuid));
    }
}
