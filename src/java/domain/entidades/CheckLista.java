/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import domain.interfaces.ICheckLista;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author paulo-pc
 */
public class CheckLista extends EntityBasic implements ICheckLista {

    @SerializedName("textoObjetivo")
    @Expose
    private String TextoObjetivo;
    
    @SerializedName("subObjetivos")
    @Expose
    private List<SubObjetivo> SubObjetivos;
    
    private boolean Ativo;
    private Date DataInclusao;
    private Date DataAlteracao;
    private Date DataConclusao;
    private Date DataInativacao;
    
    public boolean isAtivo() {
        return this.Ativo;
    }
    
    public CheckLista() {
    
    }
    
    public CheckLista(String TextoObjetivo, List<SubObjetivo> SubObjetivos) {
        this.TextoObjetivo = TextoObjetivo;
        this.SubObjetivos  = SubObjetivos;
    }
    
    public String getTextoObjetivo() {
        return TextoObjetivo;
    }

    public void setTextoObjetivo(String TextoObjetivo) {
        this.TextoObjetivo = TextoObjetivo;
    }

    public List<SubObjetivo> getSubObjetivos() {
        return SubObjetivos;
    }

    public void setSubObjetivos(List<SubObjetivo> SubObjetivos) {
        this.SubObjetivos = SubObjetivos;
    }
    
    public void setAtivo(boolean Ativo) {
        this.Ativo = Ativo;
    }

    public Date getDataInclusao() {
        return DataInclusao;
    }

    public void setDataInclusao(Date DataInclusao) {
        this.DataInclusao = DataInclusao;
    }

    public Date getDataAlteracao() {
        return DataAlteracao;
    }

    public void setDataAlteracao(Date DataAlteracao) {
        this.DataAlteracao = DataAlteracao;
    }

    public Date getDataConclusao() {
        return DataConclusao;
    }

    public void setDataConclusao(Date DataConclusao) {
        this.DataConclusao = DataConclusao;
    }

    public Date getDataInativacao() {
        return DataInativacao;
    }

    public void setDataInativacao(Date DataInativacao) {
        this.DataInativacao = DataInativacao;
    }
}
