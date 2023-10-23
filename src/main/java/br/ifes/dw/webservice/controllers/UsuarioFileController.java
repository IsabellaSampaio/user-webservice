package br.ifes.dw.webservice.controllers;

import br.ifes.dw.webservice.codes.StatusCodes;
import br.ifes.dw.webservice.dtos.UsuarioInputDTO;
import br.ifes.dw.webservice.models.EnderecoModel;
import br.ifes.dw.webservice.models.UsuarioModel;
import br.ifes.dw.webservice.services.EnderecoFileService;
import br.ifes.dw.webservice.services.UserFileService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wbs/file/usuario")
public class UsuarioFileController {
    @Autowired
    private UserFileService<UsuarioModel> fileService;

    @Autowired
    private EnderecoFileService<EnderecoModel> enderecoFileService;

    @PostMapping
    public ResponseEntity<Object> createUserFile(@RequestBody @Valid UsuarioInputDTO usuarioInputDTO){

        var usuarioModel = new UsuarioModel();
        var enderecoModel = new EnderecoModel();
        BeanUtils.copyProperties(usuarioInputDTO, usuarioModel);

        enderecoModel.setLogradouro(usuarioInputDTO.endereco().logradouro());
        enderecoModel.setBairro(usuarioInputDTO.endereco().bairro());
        enderecoModel.setCidade(usuarioInputDTO.endereco().cidade());
        enderecoModel.setEstado(usuarioInputDTO.endereco().estado());
        enderecoModel.setNumero(usuarioInputDTO.endereco().numero());

        usuarioModel.setEndereco(enderecoModel);
        enderecoFileService.save(enderecoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.save(usuarioModel));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getAllUsersFromFile() {return ResponseEntity.status(HttpStatus.OK).body(fileService.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id){
        Optional<UsuarioModel> usuarioModelOptional = (fileService.findById(id));
        return usuarioModelOptional.<ResponseEntity<Object>>map(usuarioModel ->
                ResponseEntity.status(HttpStatus.OK).body(usuarioModel)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.USER_NOT_FOUND.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable int id){
        Optional<UsuarioModel> usuarioModelOptional = fileService.findById(id);
        if(usuarioModelOptional.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.USER_NOT_FOUND.getCode());}
        fileService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(StatusCodes.USER_REMOVED.getCode());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable int id, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO){
        Optional<UsuarioModel> usuarioModelOptional = fileService.findById(id);
        if(usuarioModelOptional.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.USER_NOT_FOUND.getCode());}

        var usuarioModel = usuarioModelOptional.get();
        var enderecoModel = new EnderecoModel();

        enderecoModel.setLogradouro(usuarioInputDTO.endereco().logradouro());
        enderecoModel.setBairro(usuarioInputDTO.endereco().bairro());
        enderecoModel.setCidade(usuarioInputDTO.endereco().cidade());
        enderecoModel.setEstado(usuarioInputDTO.endereco().estado());
        enderecoModel.setNumero(usuarioInputDTO.endereco().numero());

        BeanUtils.copyProperties(usuarioInputDTO, usuarioModel);
        usuarioModel.setEndereco(enderecoModel);

        fileService.update(id, usuarioModel);

        return ResponseEntity.status(HttpStatus.OK).body(fileService.findById(id));
    }

}