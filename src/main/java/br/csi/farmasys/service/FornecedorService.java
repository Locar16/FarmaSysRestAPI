package br.csi.farmasys.service;

import br.csi.farmasys.model.endereco.DadosEndereco;
import br.csi.farmasys.model.endereco.Endereco;
import br.csi.farmasys.model.fornecedor.DadosFornecedor;
import br.csi.farmasys.model.fornecedor.Fornecedor;
import br.csi.farmasys.model.fornecedor.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Fornecedor getFornecedorUUID(UUID uuid) {
        Fornecedor fornecedor = this.repository.findByUuid(uuid);
        if (fornecedor == null) {
            throw new EntityNotFoundException();
        }
        return fornecedor;
    }

    @Transactional
    public Fornecedor salvar(DadosFornecedor dados) {
        Fornecedor fornecedor = new Fornecedor();
        aplicarDados(fornecedor, dados);
        return this.repository.save(fornecedor);
    }

    @Transactional
    public Fornecedor atualizar(Long id, DadosFornecedor dados) {
        Fornecedor fornecedor = getFornecedor(id);
        aplicarDados(fornecedor, dados);
        return this.repository.save(fornecedor);
    }

    @Transactional
    public Fornecedor atualizarUUID(UUID uuid, DadosFornecedor dados) {
        Fornecedor fornecedor = getFornecedorUUID(uuid);
        aplicarDados(fornecedor, dados);
        return this.repository.save(fornecedor);
    }

    @Transactional
    public void excluir(Long id) {
        this.repository.delete(getFornecedor(id));
    }

    @Transactional
    public void excluirUUID(UUID uuid) {
        this.repository.delete(getFornecedorUUID(uuid));
    }

    private void aplicarDados(Fornecedor fornecedor, DadosFornecedor dados) {
        fornecedor.setRazaoSocial(dados.razaoSocial());
        fornecedor.setCnpj(dados.cnpj());
        fornecedor.setEmail(dados.email());
        fornecedor.setTelefone(dados.telefone());
        fornecedor.setEndereco(converterEndereco(dados.endereco()));
    }

    private Endereco converterEndereco(DadosEndereco dados) {
        if (dados == null) {
            return null;
        }
        return new Endereco(dados.complemento(), dados.bairro(), dados.cep(),
            dados.numero(), dados.cidade(), dados.uf());
    }
}
