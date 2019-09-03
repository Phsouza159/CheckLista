/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra.services;

import com.google.gson.Gson;
import domain.entidades.CheckLista;
import domain.entidades.SubObjetivo;
import domain.interfaces.ICheckListServices;
import domain.interfaces.ICheckLista;
import domain.objectValues.Notification;
import infra.contexto.Contexto;
import infra.repositorios.CheckListaRepository;
import infra.repositorios.RepositoryBasic;
import infra.repositorios.SubObjetivoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulo-pc
 */
public class CheckListServices extends Notification implements ICheckListServices{
    
    private Gson _gson;
    private CheckListaRepository _checkListaRepository;
    private SubObjetivoRepository _subObjetivoRepository;
    private Contexto _Contexto;
      
    public CheckListServices() {
        this._gson = new Gson();
        this._checkListaRepository = new CheckListaRepository();
        this._subObjetivoRepository = new SubObjetivoRepository();
        this._Contexto = new Contexto();
    }
    
    public ICheckLista Deserialize(String json){    
        CheckLista response = this._gson.fromJson(json , CheckLista.class);
        
        return response;
    }
    
    public String Serialize(CheckLista checklista) {
        String response = this._gson.toJson(checklista);
        
        return response;
    }
    
    public void AddCheckLista(ICheckLista checkLista){
        try {
            this._Contexto.Transation();
        
            this._checkListaRepository.Add(this._Contexto , (CheckLista)checkLista);
        
            List<SubObjetivo> subObjetivos = checkLista.getSubObjetivos();
            
            for(int i = 0 ; i < subObjetivos.size() ; i++){
                SubObjetivo item = subObjetivos.get(i);
                item.setIdObjetivo(checkLista.getId());
                
                this._subObjetivoRepository.Add(this._Contexto , item);
            }
            
            this.AddNotification(this._checkListaRepository);
            this.AddNotification(this._subObjetivoRepository);
            
            if(this.isValid()){
                this._Contexto.Commit();
            }
            
        }catch(Exception ex){
            
            this.AddNotification(this._checkListaRepository);
            this.AddNotification("Exception", ex.getMessage());
            Logger.getLogger(CheckListServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<CheckLista> GetAll(){
        try{
            this._Contexto.Transation();
            
            List<CheckLista> collectionCheckList = this._checkListaRepository.GetAll(this._Contexto);
            
            for(int i = 0; i < collectionCheckList.size() ; i++)
            {
                CheckLista item = collectionCheckList.get(i);
                item.setSubObjetivos(this._subObjetivoRepository.GetCollection(this._Contexto, item.getId()));
            }
            
            this._Contexto.CloseConnect();
            
            if(this._checkListaRepository.isInValid());
                this.AddNotification(this._checkListaRepository);
            
            return collectionCheckList;
            
        }catch(Exception ex){
            this.AddNotification(this._checkListaRepository);
            this.AddNotification("Exception :: getAll()", ex.getMessage());
            Logger.getLogger(CheckListServices.class.getName()).log(Level.SEVERE, null, ex);
            
            return new ArrayList<CheckLista>();
        }
    }
    
    @Override
    public CheckLista GetById(int id) {
        
        try{
             this._Contexto.Transation();
            
            CheckLista element = this._checkListaRepository.GetById( this._Contexto, id);
            
            element.setSubObjetivos(this._subObjetivoRepository.GetCollection(this._Contexto, element.getId()));
            
            this._Contexto.CloseConnect();
            
            if(this._checkListaRepository.isInValid());
                this.AddNotification(this._checkListaRepository);
            return element;
            
        }catch(Exception ex){
            this.AddNotification("Exception :: GetByID()", ex.getMessage());
            Logger.getLogger(CheckListServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new CheckLista();
    }
    
    @Override
    public boolean InativarCheckListaById(int id) {
        try{
           this._Contexto.Transation();
           
           boolean result = this._checkListaRepository.InativateById(this._Contexto,id);
           
           if(this._checkListaRepository.isInValid()){
               
               this.AddNotification(this._checkListaRepository);
               this._Contexto.CloseConnect();
               return false;
           }
            
           if(!result)
               this.AddNotification("InativarCheckListaById", "Não foi possível excluir o registro!");
           
           this._Contexto.Commit();
           
           return result;
        }catch(Exception ex ){
            
            this.AddNotification("InativarCheckListaById", "Não foi possível excluir o registro!");
            this.AddNotification("Exception :: InativarCheckListaById()", ex.getMessage());
            Logger.getLogger(CheckListServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    public void Clear() {
        
        this._checkListaRepository.NotifiacationClear();
        this._subObjetivoRepository.NotifiacationClear();
        this.NotifiacationClear();
    }
}
