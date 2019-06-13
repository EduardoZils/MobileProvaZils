package fag.edu.com.eduardozils_prova.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;

public class Ordenha extends SugarRecord {
    @Unique
    private int codigo;
    private MatrizLeitera matrizLeitera;
    private double qtLitros;
    private Date date;

    public Ordenha(int codigo, MatrizLeitera matrizLeitera, double qtLitros, Date date) {
        this.codigo = codigo;
        this.matrizLeitera = matrizLeitera;
        this.qtLitros = qtLitros;
        this.date = date;
    }

    public Ordenha() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public MatrizLeitera getMatrizLeitera() {
        return matrizLeitera;
    }

    public void setMatrizLeitera(MatrizLeitera matrizLeitera) {
        this.matrizLeitera = matrizLeitera;
    }

    public double getQtLitros() {
        return qtLitros;
    }

    public void setQtLitros(double qtLitros) {
        this.qtLitros = qtLitros;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ordenha{}";
    }
}
