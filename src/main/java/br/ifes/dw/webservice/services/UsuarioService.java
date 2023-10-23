package br.ifes.dw.webservice.services;

import br.ifes.dw.webservice.models.UsuarioModel;
import br.ifes.dw.webservice.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService<T extends UsuarioModel> implements ServiceInterface<T> {

    private final UsuarioRepository<T> genericRepository;

    public UsuarioService(UsuarioRepository<T> repository) {genericRepository = repository;}
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
