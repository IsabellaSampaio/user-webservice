package br.ifes.dw.webservice.services;

import br.ifes.dw.webservice.codes.StatusCodes;
import br.ifes.dw.webservice.models.EnderecoModel;
import br.ifes.dw.webservice.models.UsuarioModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoFileService<T extends EnderecoModel> implements ServiceInterface<T> {
    private static final String ENDERECOS_JSON_FILE = "src/main/java/br/ifes/dw/webservice/files/enderecos.json";
    private List<T> enderecosList;
    private final Gson gson = new Gson();
    private long id = 1;

    public EnderecoFileService() {
        loadAddressesFromJson();}

    @Override
    public T save(T object) {
        object.setId(id);
        enderecosList.add(object);
        id++;
        saveAddressesToJson();
        return object;
    }

    @Override
    public void delete(int id) {
        enderecosList.removeIf(product -> product.getId() == id);
        saveAddressesToJson();
    }

    @Override
    public List<T> findAll() {return enderecosList;}

    @Override
    public Optional<T> findById(int id) {return enderecosList.stream().filter(product -> product.getId() == id).findFirst();}

    @Override
    public void update(int id, T object) {
        Optional<T> enderecoOptional = findById(id);

        if (enderecoOptional.isPresent()) {
            T enderecoExistente = enderecoOptional.get();
            enderecoExistente.setLogradouro(object.getLogradouro());
            enderecoExistente.setBairro(object.getBairro());
            enderecoExistente.setCidade(object.getCidade());
            enderecoExistente.setEstado(object.getEstado());
        }
    }

    private List<T> loadAddressesFromJson() {
        try (FileReader reader = new FileReader(ENDERECOS_JSON_FILE)) {
            return this.enderecosList = gson.fromJson(reader, new TypeToken<List<EnderecoModel>>() {}.getType());
        } catch (IOException e) {throw new HttpClientErrorException(HttpStatus.NOT_FOUND, StatusCodes.NOT_FOUND.getCode());}
    }

    private void saveAddressesToJson() {
        try (FileWriter writer = new FileWriter(ENDERECOS_JSON_FILE)) {
            gson.toJson(enderecosList, writer);
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, StatusCodes.NOT_SAVED.getCode());
        }
    }
}
