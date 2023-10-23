package br.ifes.dw.webservice.repositories;

import br.ifes.dw.webservice.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository<T> extends JpaRepository<EnderecoModel, Long> { }
