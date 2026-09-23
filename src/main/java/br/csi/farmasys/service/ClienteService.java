package br.csi.farmasys.service;

import br.csi.farmasys.model.cliente.Cliente;
import br.csi.farmasys.model.cliente.ClienteRepository;
import br.csi.farmasys.model.cliente.DadosCliente;
import br.csi.farmasys.model.endereco.DadosEndereco;
import br.csi.farmasys.model.endereco.Endereco;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return this.repository.findAll();
    }

    public Cliente getCliente(Long id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Cliente getClienteUUID(UUID uuid) {
        Cliente cliente = this.repository.findByUuid(uuid);
        if (cliente == null) {
            throw new EntityNotFoundException();
        }
        return cliente;
    }

    @Transactional
    public Cliente salvar(DadosCliente dados) {
        Cliente cliente = new Cliente();
        aplicarDados(cliente, dados);
        return this.repository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(Long id, DadosCliente dados) {
        Cliente cliente = getCliente(id);
        aplicarDados(cliente, dados);
        return this.repository.save(cliente);
    }

    @Transactional
    public Cliente atualizarUUID(UUID uuid, DadosCliente dados) {
        Cliente cliente = getClienteUUID(uuid);
        aplicarDados(cliente, dados);
        return this.repository.save(cliente);
    }

    @Transactional
    public void excluir(Long id) {
        this.repository.delete(getCliente(id));
    }

    @Transactional
    public void excluirUUID(UUID uuid) {
        this.repository.delete(getClienteUUID(uuid));
    }

    private void aplicarDados(Cliente cliente, DadosCliente dados) {
        cliente.setNome(dados.nome());
        cliente.setCpf(dados.cpf());
        cliente.setEmail(dados.email());
        cliente.setTelefone(dados.telefone());
        cliente.setDataNascimento(dados.dataNascimento());
        cliente.setEndereco(converterEndereco(dados.endereco()));
    }

    private Endereco converterEndereco(DadosEndereco dados) {
        if (dados == null) {
            return null;
        }
        return new Endereco(dados.complemento(), dados.bairro(), dados.cep(),
            dados.numero(), dados.cidade(), dados.uf());
    }
}
