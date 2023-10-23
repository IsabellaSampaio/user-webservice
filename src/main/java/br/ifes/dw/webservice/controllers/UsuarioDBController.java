package br.ifes.dw.webservice.controllers;

import br.ifes.dw.webservice.codes.StatusCodes;
import br.ifes.dw.webservice.dtos.UsuarioInputDTO;
import br.ifes.dw.webservice.models.EnderecoModel;
import br.ifes.dw.webservice.models.UsuarioModel;
import br.ifes.dw.webservice.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wbs/db/usuario")
public class UsuarioDBController {
    @Autowired
    private UsuarioService<UsuarioModel> usuarioService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {

        var usuarioModel = new UsuarioModel();
        var enderecoModel = new EnderecoModel();
        BeanUtils.copyProperties(usuarioInputDTO, usuarioModel);

        enderecoModel.setLogradouro(usuarioInputDTO.endereco().logradouro());
        enderecoModel.setBairro(usuarioInputDTO.endereco().bairro());
        enderecoModel.setCidade(usuarioInputDTO.endereco().cidade());
        enderecoModel.setEstado(usuarioInputDTO.endereco().estado());
        enderecoModel.setNumero(usuarioInputDTO.endereco().numero());

        usuarioModel.setEndereco(enderecoModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioModel));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getAllUsers() {return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id){
        Optional<UsuarioModel> usuarioModelOptional = (usuarioService.findById(id));
        return usuarioModelOptional.<ResponseEntity<Object>>map(usuarioModel ->
                ResponseEntity.status(HttpStatus.OK).body(usuarioModel)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.USER_NOT_FOUND.getCode()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable int id){
        Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(id);
        if(usuarioModelOptional.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.USER_NOT_FOUND.getCode());}
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(StatusCodes.USER_REMOVED.getCode());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable int id, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO){
        Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(id);
        if(usuarioModelOptional.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.USER_NOT_FOUND.getCode());}

        var usuarioModel = usuarioModelOptional.get();
        BeanUtils.copyProperties(usuarioInputDTO, usuarioModel);
        usuarioModel.setId(usuarioModelOptional.get().getId());

        var enderecoModel = new EnderecoModel();

        enderecoModel.setLogradouro(usuarioInputDTO.endereco().logradouro());
        enderecoModel.setBairro(usuarioInputDTO.endereco().bairro());
        enderecoModel.setCidade(usuarioInputDTO.endereco().cidade());
        enderecoModel.setEstado(usuarioInputDTO.endereco().estado());
        enderecoModel.setNumero(usuarioInputDTO.endereco().numero());

        usuarioModel.setEndereco(enderecoModel);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.save(usuarioModel));
    }

}
