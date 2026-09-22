package br.csi.farmasys.service;

import br.csi.farmasys.model.remedio.Remedio;
import br.csi.farmasys.model.remedio.RemedioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RemedioService {

    private final RemedioRepository repository;

    public RemedioService(RemedioRepository repository) {
        this.repository = repository;
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
        this.repository.save(remedio);
    }

    @Transactional
    public void atualizar(Remedio remedio) {
        this.repository.save(remedio);
    }

    @Transactional
    public void atualizarUUID(Remedio remedio) {
        Remedio existente = this.repository.findByUuid(remedio.getUuid());
        if (existente != null) {
            remedio.setId(existente.getId());
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
}
