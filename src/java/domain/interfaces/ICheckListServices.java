/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.entidades.CheckLista;
import java.util.List;

/**
 *
 * @author paulo-pc
 */
public interface ICheckListServices extends INotification{
    ICheckLista Deserialize(String json);
    String Serialize(CheckLista checklista); 
    void AddCheckLista(ICheckLista checkLista);
    List<CheckLista> GetAll();
    CheckLista GetById(int id);
    boolean InativarCheckListaById(int id);
    void Clear();
}
