package br.ifes.dw.webservice.repositories;

import br.ifes.dw.webservice.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository<T> extends JpaRepository<UsuarioModel, Long> { }
