package br.com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "endereco")
public class Endereco {

    @Id
    private long id;
    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;

    /**
     *
     */
    public Endereco() {
    }

    /**
     *
     * @param id
     * @param rua
     * @param numero
     * @param bairro
     * @param cidade
     * @param estado
     */
    @PersistenceConstructor
    public Endereco(long id, String rua, int numero, String bairro, String cidade, String estado) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public Endereco setId(long id) {
        this.id = id;
        return this;
    }

    public String getRua() {
        return rua;
    }

    public Endereco setRua(String rua) {
        this.rua = rua;
        return this;
    }

    public int getNumero() {
        return numero;
    }

    public Endereco setNumero(int numero) {
        this.numero = numero;
        return this;
    }

    public String getBairro() {
        return bairro;
    }

    public Endereco setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public String getCidade() {
        return cidade;
    }

    public Endereco setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public String getEstado() {
        return estado;
    }

    public Endereco setEstado(String estado) {
        this.estado = estado;
        return this;
    }
}
