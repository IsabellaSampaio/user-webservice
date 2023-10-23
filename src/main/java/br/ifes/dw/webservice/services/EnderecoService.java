package br.ifes.dw.webservice.services;

import br.ifes.dw.webservice.models.EnderecoModel;
import br.ifes.dw.webservice.repositories.EnderecoRepository;
import br.ifes.dw.webservice.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService<T extends EnderecoModel> implements ServiceInterface<T> {
    private final EnderecoRepository<T> genericRepository;

    public EnderecoService(EnderecoRepository<T> repository) {genericRepository = repository;}
    @Override
    public T save(T object) {return genericRepository.save(object);}

    @Override
    public void delete(int id) {genericRepository.deleteById((long) id);}

    @Override
    public List<T> findAll() {
        return (List<T>) genericRepository.findAll();
    }

    @Override
    public Optional<T> findById(int id) {return (Optional<T>) genericRepository.findById((long) id);}

    @Override
    public void update(int id, T object) {}
}
