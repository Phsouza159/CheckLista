/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.objectValues.Notificacoes;
import domain.objectValues.Notification;
import java.util.List;
/**
 *
 * @author paulo-pc
 */
public interface INotification {
    List<Notificacoes> getNotificacoes();
    void AddNotification(String key , String notification);
    void AddNotification(Notification notification);
    boolean isValid();
    boolean isInValid();
    void NotifiacationClear();
}
