/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.enums;

/**
 *
 * @author paulo-pc
 */
public enum ehttp {

    SUCESSO(200),
    CREATED(201),
    BADREQUEST(400),
    ERROINTERNO(500);
    
    
    
    public int Status;
    
    ehttp(int valor){
        Status = valor;
    }
    
    public int getStatus() {
        return this.Status;
    }
}
