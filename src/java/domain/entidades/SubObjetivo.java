/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import domain.interfaces.ISubOjetivo;
import java.sql.Date;

/**
 *
 * @author paulo-pc
 */
public class SubObjetivo extends EntityBasic implements ISubOjetivo {
    
    @SerializedName("isCheck")
    @Expose
    private boolean IsCheck;
    
    @SerializedName("textoSubObjetivo")
    @Expose
    private String TextoSubObjetivo;
    private int IdObjetivo;
    private Date DataInclusao;
    private Date DataConclusao;
    private Date DataAlteracao;
    private Date DataInativacao;
    
    public SubObjetivo() {
    
    }
    
    public SubObjetivo(boolean IsCheck, String TextoSubObjetivo) {
        this.IsCheck = IsCheck;
        this.TextoSubObjetivo = TextoSubObjetivo;
    }
    
    public boolean isIsCheck() {
        return IsCheck;
    }

    public void setIsCheck(boolean IsCheck) {
        this.IsCheck = IsCheck;
    }

    public String getTextoSubObjetivo() {
        return TextoSubObjetivo;
    }

    public void setTextoSubObjetivo(String TextoSubObjetivo) {
        this.TextoSubObjetivo = TextoSubObjetivo;
    }
    
    
    public int getIdObjetivo() {
        return IdObjetivo;
    }

    public void setIdObjetivo(int IdObjetivo) {
        this.IdObjetivo = IdObjetivo;
    }

    public Date getDataInclusao() {
        return DataInclusao;
    }

    public void setDataInclusao(Date DataInclusao) {
        this.DataInclusao = DataInclusao;
    }

    public Date getDataConclusao() {
        return DataConclusao;
    }

    public void setDataConclusao(Date DataConclusao) {
        this.DataConclusao = DataConclusao;
    }

    public Date getDataAlteracao() {
        return DataAlteracao;
    }

    public void setDataAlteracao(Date DataAlteracao) {
        this.DataAlteracao = DataAlteracao;
    }

    public Date getDataInativacao() {
        return DataInativacao;
    }

    public void setDataInativacao(Date DataInativacao) {
        this.DataInativacao = DataInativacao;
    }
}
