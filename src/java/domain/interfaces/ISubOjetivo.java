/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

/**
 *
 * @author paulo-pc
 */
public interface ISubOjetivo extends IEntityBasic {
    boolean isIsCheck();
    void setIsCheck(boolean IsCheck);
    String getTextoSubObjetivo();
    void setTextoSubObjetivo(String TextoSubObjetivo);
}
