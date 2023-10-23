package br.ifes.dw.webservice.services;

import br.ifes.dw.webservice.codes.StatusCodes;
import br.ifes.dw.webservice.models.UsuarioModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class UserFileService<T extends UsuarioModel> implements ServiceInterface<T> {
    private static final String USUARIOS_JSON_FILE = "src/main/java/br/ifes/dw/webservice/files/users.json";
    private List<T> userList;
    private final Gson gson = new Gson();
    private long id = 1;
    private long id_end = 1;

    public UserFileService() {loadUsersFromJson();}

    @Override
    public T save(T object) {
        object.setId(id);
        object.getEndereco().setId(id_end);
        userList.add(object);
        id++;
        id_end++;
        saveUsersToJson();
        return object;
    }

    @Override
    public void delete(int id) {
        userList.removeIf(product -> product.getId() == id);
        saveUsersToJson();
    }

    @Override
    public List<T> findAll() {return userList;}

    @Override
    public Optional<T> findById(int id) {return userList.stream().filter(product -> product.getId() == id).findFirst();}

    @Override
    public void update(int id, T object) {
        Optional<T> usuarioOptional = findById(id);

        if (usuarioOptional.isPresent()) {
            T usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNome(object.getNome());
            usuarioExistente.setDataDeNascimento(object.getDataDeNascimento());
            usuarioExistente.setEndereco(object.getEndereco());
        }
    }

    private List<T> loadUsersFromJson() {
        try (FileReader reader = new FileReader(USUARIOS_JSON_FILE)) {
            return this.userList = gson.fromJson(reader, new TypeToken<List<UsuarioModel>>() {}.getType());
        } catch (IOException e) {throw new HttpClientErrorException(HttpStatus.NOT_FOUND, StatusCodes.NOT_FOUND.getCode());}
    }

    private void saveUsersToJson() {
        try (FileWriter writer = new FileWriter(USUARIOS_JSON_FILE)) {
            gson.toJson(userList, writer);
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, StatusCodes.NOT_SAVED.getCode());
        }
    }

}



