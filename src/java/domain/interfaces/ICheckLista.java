/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.entidades.SubObjetivo;
import java.util.List;

/**
 *
 * @author paulo-pc
 */
public interface ICheckLista extends IEntityBasic {
    String getTextoObjetivo();
    void setTextoObjetivo(String TextoObjetivo);
    List<SubObjetivo> getSubObjetivos();
    void setSubObjetivos(List<SubObjetivo> SubObjetivos);
}
