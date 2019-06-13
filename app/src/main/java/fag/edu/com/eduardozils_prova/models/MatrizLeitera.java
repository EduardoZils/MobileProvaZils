package fag.edu.com.eduardozils_prova.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;
import java.util.Date;

public class MatrizLeitera extends SugarRecord implements Serializable {
    @Unique
    private int codigo;
    private String descricao;
    private int idade;
    private Date dataUltParto;

    public MatrizLeitera(int codigo, String descricao, int idade, Date dataUltParto) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.idade = idade;
        this.dataUltParto = dataUltParto;
    }

    public MatrizLeitera() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Date getDataUltParto() {
        return dataUltParto;
    }

    public void setDataUltParto(Date dataUltParto) {
        this.dataUltParto = dataUltParto;
    }

    @Override
    public String toString() {
        return codigo +
                " - " + descricao;
    }
}
