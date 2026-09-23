package br.csi.farmasys.service;

import br.csi.farmasys.model.fornecedor.Fornecedor;
import br.csi.farmasys.model.fornecedor.FornecedorRepository;
import br.csi.farmasys.model.remedio.DadosRemedio;
import br.csi.farmasys.model.remedio.Remedio;
import br.csi.farmasys.model.remedio.RemedioRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Remedio getRemedioUUID(UUID uuid) {
        Remedio remedio = this.repository.findByUuid(uuid);
        if (remedio == null) {
            throw new EntityNotFoundException();
        }
        return remedio;
    }

    @Transactional
    public Remedio salvar(DadosRemedio dados) {
        Remedio remedio = new Remedio();
        aplicarDados(remedio, dados);
        return this.repository.save(remedio);
    }

    @Transactional
    public Remedio atualizar(Long id, DadosRemedio dados) {
        Remedio remedio = getRemedio(id);
        aplicarDados(remedio, dados);
        return this.repository.save(remedio);
    }

    @Transactional
    public Remedio atualizarUUID(UUID uuid, DadosRemedio dados) {
        Remedio remedio = getRemedioUUID(uuid);
        aplicarDados(remedio, dados);
        return this.repository.save(remedio);
    }

    @Transactional
    public void excluir(Long id) {
        this.repository.delete(getRemedio(id));
    }

    @Transactional
    public void excluirUUID(UUID uuid) {
        this.repository.delete(getRemedioUUID(uuid));
    }

    private void aplicarDados(Remedio remedio, DadosRemedio dados) {
        remedio.setNome(dados.nome());
        remedio.setPrincipioAtivo(dados.principioAtivo());
        remedio.setPreco(dados.preco());
        remedio.setQuantidadeEstoque(dados.quantidadeEstoque());
        remedio.setNecessitaReceita(dados.necessitaReceita());
        remedio.setFornecedor(buscarFornecedor(dados.fornecedorId()));
    }

    private Fornecedor buscarFornecedor(Long fornecedorId) {
        if (fornecedorId == null) {
            return null;
        }
        return this.fornecedorRepository.findById(fornecedorId)
            .orElseThrow(EntityNotFoundException::new);
    }
}
