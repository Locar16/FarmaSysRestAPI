package br.csi.farmasys.service;

import br.csi.farmasys.model.cliente.Cliente;
import br.csi.farmasys.model.cliente.ClienteRepository;
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
        return this.repository.findById(id).orElse(null);
    }

    public Cliente getClienteUUID(String uuid) {
        return this.repository.findByUuid(UUID.fromString(uuid));
    }

    @Transactional
    public void salvar(Cliente cliente) {
        this.repository.save(cliente);
    }

    @Transactional
    public void atualizar(Cliente cliente) {
        this.repository.save(cliente);
    }

    @Transactional
    public void atualizarUUID(Cliente cliente) {
        Cliente existente = this.repository.findByUuid(cliente.getUuid());
        if (existente != null) {
            cliente.setId(existente.getId());
            this.repository.save(cliente);
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
