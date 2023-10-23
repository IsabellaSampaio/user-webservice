package br.ifes.dw.webservice.codes;


import lombok.Getter;

@Getter
public enum StatusCodes {
    USER_NOT_FOUND("Usuário não encontrado."),
    USER_REMOVED("Usuário removido com sucesso."),
    ENDERECO_NOT_FOUND("Endereço não encontrado."),
    ENDERECO_REMOVED("Endereço removido com sucesso."),
    NOT_FOUND("Desculpe, não foi possível encontrar os seus dados"),
    NOT_SAVED("Desculpe, não foi possível salvar os seus dados");

    private final String code;
    StatusCodes(String code) {
        this.code = code;
    }

}
