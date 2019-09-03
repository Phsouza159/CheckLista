/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.entidades;

import domain.interfaces.IEntityBasic;
import domain.objectValues.Notification;

/**
 *
 * @author paulo-pc
 */
public abstract class EntityBasic extends Notification implements IEntityBasic {
    
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}
