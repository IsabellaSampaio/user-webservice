package br.ifes.dw.webservice.controllers;

import br.ifes.dw.webservice.codes.StatusCodes;
import br.ifes.dw.webservice.dtos.EnderecoInputDTO;
import br.ifes.dw.webservice.models.EnderecoModel;
import br.ifes.dw.webservice.services.EnderecoFileService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wbs/file/endereco")
public class EnderecoFileController {
    @Autowired
    private EnderecoFileService<EnderecoModel> fileService;

    @PostMapping
    public ResponseEntity<Object> createAddress(@RequestBody @Valid EnderecoInputDTO enderecoInputDTO){
        var enderecoModel = new EnderecoModel();
        BeanUtils.copyProperties(enderecoInputDTO, enderecoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.save(enderecoModel));
    }

    @GetMapping
    public ResponseEntity<List<EnderecoModel>> getAllAddresses(){return ResponseEntity.status(HttpStatus.OK).body(fileService.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable int id){
        Optional<EnderecoModel> enderecoModelOptional = (fileService.findById(id));
        return enderecoModelOptional.<ResponseEntity<Object>>map(enderecoModel ->
                ResponseEntity.status(HttpStatus.OK).body(enderecoModel)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.ENDERECO_NOT_FOUND.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable int id){
        Optional<EnderecoModel> enderecoModelOptional = (fileService.findById(id));
        if(enderecoModelOptional.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.ENDERECO_NOT_FOUND.getCode());}
        fileService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(StatusCodes.ENDERECO_REMOVED.getCode());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable int id, @RequestBody @Valid EnderecoInputDTO enderecoInputDTO){
        Optional<EnderecoModel> enderecoModelOptional = (fileService.findById(id));
        if(enderecoModelOptional.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.ENDERECO_NOT_FOUND.getCode());}

        var enderecoModel = enderecoModelOptional.get();
        BeanUtils.copyProperties(enderecoInputDTO, enderecoModel);

        fileService.update(id, enderecoModel);

        return ResponseEntity.status(HttpStatus.OK).body(fileService.findById(id));
    }

}