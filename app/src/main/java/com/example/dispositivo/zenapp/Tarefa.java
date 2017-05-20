package com.example.dispositivo.zenapp;

/**
 * Created by aluno on 20/04/17.
 */

public class Tarefa {
    private String id;
    private String titulo;
    private String descricao;
    private String tag;


    public Tarefa() {


    }

    public Tarefa(String id,String titulo, String descricao, String tag) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tag = tag;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
