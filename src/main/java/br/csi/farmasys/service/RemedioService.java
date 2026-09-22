package br.csi.farmasys.service;

import br.csi.farmasys.model.fornecedor.FornecedorRepository;
import br.csi.farmasys.model.remedio.Remedio;
import br.csi.farmasys.model.remedio.RemedioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RemedioService {

    private final RemedioRepository repository;
    private final FornecedorRepository fornecedorRepository;

    public RemedioService(RemedioRepository repository, FornecedorRepository fornecedorRepository) {
        this.repository = repository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<Remedio> listar() {
        return this.repository.findAll();
    }

    public Remedio getRemedio(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public Remedio getRemedioUUID(String uuid) {
        return this.repository.findByUuid(UUID.fromString(uuid));
    }

    @Transactional
    public void salvar(Remedio remedio) {
        vincularFornecedor(remedio);
        this.repository.save(remedio);
    }

    @Transactional
    public void atualizar(Remedio remedio) {
        vincularFornecedor(remedio);
        this.repository.save(remedio);
    }

    @Transactional
    public void atualizarUUID(Remedio remedio) {
        Remedio existente = this.repository.findByUuid(remedio.getUuid());
        if (existente != null) {
            remedio.setId(existente.getId());
            vincularFornecedor(remedio);
            this.repository.save(remedio);
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

    private void vincularFornecedor(Remedio remedio) {
        if (remedio.getFornecedor() != null && remedio.getFornecedor().getId() != null) {
            remedio.setFornecedor(
                this.fornecedorRepository.findById(remedio.getFornecedor().getId()).orElseThrow()
            );
        } else {
            remedio.setFornecedor(null);
        }
    }
}
