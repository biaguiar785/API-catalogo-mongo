package com.catalogoprodutos.model;

public class Erro {

    private Integer status_code;
    private String message;

    
    public Erro(Integer status_code) {
        this.status_code = status_code;
        this.message = "Requisição invalida";
    }
    public Integer getStatus_code() {
        return status_code;
    }
    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    
}
