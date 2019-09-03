/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.objectValues;

import domain.interfaces.INotification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author paulo-pc
 */
public class Notification implements INotification {
    
    private boolean _isValid = true;
    private List<Notificacoes> _notificacoes;

    public Notification() {
        this._notificacoes = new ArrayList<Notificacoes>();
    }
    
    public List<Notificacoes> getNotificacoes() {
        return this._notificacoes;
    }
    
    public void NotifiacationClear() {
        this._notificacoes = new ArrayList<Notificacoes>();
        this._isValid = true;
    }

    public void AddNotification(String key , String notification){
        
        this._notificacoes.add(new Notificacoes(key,notification));
        this._isValid = false;
    }
    
    public void AddNotification(Notification notification){
        this._isValid = notification.isValid();
        this._notificacoes = notification.getNotificacoes();
    }
    
    
    public boolean isValid() {
        
        return this._isValid;
    }
    
    public boolean isInValid(){
        
        return !this._isValid;
    }
}
