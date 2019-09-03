/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra.repositorios;

import domain.entidades.CheckLista;
import domain.entidades.EntityBasic;
import domain.interfaces.ICheckListaRepository;
import infra.contexto.Contexto;
import static java.lang.System.out;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulo-pc
 */
public class CheckListaRepository extends RepositoryBasic<CheckLista> implements ICheckListaRepository {

    public CheckListaRepository(){
    
    }

    @Override
    protected CheckLista MappingResultToTEntity(ResultSet resultQuery) {
        
        CheckLista element = new CheckLista();
        
        try{
        
            element.setId(/****************/resultQuery.getInt/*******/("id"));
            element.setTextoObjetivo(/*****/resultQuery.getNString/***/("objetivo"));
            element.setAtivo(/*************/resultQuery.getBoolean/***/("ativo"));
            element.setDataInclusao(/******/resultQuery.getDate/******/("dth_inclusao"));
            element.setDataAlteracao(/*****/resultQuery.getDate/******/("dth_alteracao"));
            element.setDataConclusao(/*****/resultQuery.getDate/******/("dth_conclusao"));
            element.setDataInativacao(/****/resultQuery.getDate/******/("dth_inativacao"));
            
        }catch(Exception ex){
        
            this.AddNotification("Exception :: MappingResultToTEntity() ", ex.getMessage());
        }   
        
        return element;
    }
    
    @Override
    public CheckLista GetById(Contexto _con , int id) {
       
        CheckLista element = new CheckLista();
        
        try{
            
            String _query = "SELECT * FROM CKL_001_OBJETIVO WHERE ID = ? AND ATIVO = TRUE";

            _con._pst = (PreparedStatement) _con._connection.prepareStatement(_query);
            _con._pst.setInt(1, id);
            
            _con._result = _con._pst.executeQuery();
            
            if(_con._result.next())
                element = this.MappingResultToTEntity(_con._result);
            else {
                element.AddNotification("Result query","Nenhum valor encontrado para id " + id);
            }
            
            _con._result = null;
            return element;
            
        }catch(Exception ex){
            this.AddNotification("Exception :: GetById()" , ex.getMessage());
            Logger.getLogger(CheckListaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return element;
    }
    
    @Override
    public List<CheckLista> GetAll(Contexto _con) {
        try{
            List<CheckLista> listReponse = new ArrayList<CheckLista>();
            
            String _query = "SELECT * FROM CKL_001_OBJETIVO WHERE ATIVO = TRUE";
            _con._pst = (PreparedStatement) _con._connection.prepareStatement(_query);
            
            _con._result = _con._pst.executeQuery();
            
            while(_con._result.next()) {
                
                listReponse.add(this.MappingResultToTEntity(_con._result));
            }
            
            _con._result = null;
            return listReponse;
            
        }catch(Exception ex){
            this.AddNotification("Exception :: GetAll()" , ex.getMessage());
            Logger.getLogger(CheckListaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList<CheckLista>();
    }

    @Override
    public void Add(Contexto _con ,CheckLista tentity) {
        try {
            
            String _query = "INSERT INTO CKL_001_OBJETIVO(ID, OBJETIVO,ATIVO,DTH_INCLUSAO) VALUES(NULL,?,?,?)";
            _con._pst = (PreparedStatement) _con._connection.prepareStatement(_query ,  Statement.RETURN_GENERATED_KEYS );
            
            _con._pst.setString(1 , tentity.getTextoObjetivo());
            _con._pst.setBoolean(2, true);
            _con._pst.setDate(3, new java.sql.Date(Calendar.getInstance().getTimeInMillis())); 
                    
            _con._pst.execute();
            
            _con._result = _con._pst.getGeneratedKeys();
            
            if(_con._result.next())
                tentity.setId(_con._result.getInt(1));
            
        } catch (Exception ex) {
            this.AddNotification("Exception" , ex.getMessage());
            Logger.getLogger(CheckListaRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public boolean Update(Contexto _con ,CheckLista tentity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean InativateById(Contexto _con ,int id) {
        try {
            
                String _query = "UPDATE CKL_001_OBJETIVO SET ATIVO = ? , DTH_INATIVACAO = ? WHERE ID = ?";
            _con._pst = (PreparedStatement) _con._connection.prepareStatement(_query);
            
            
            _con._pst.setBoolean(1, false);
            _con._pst.setDate(2, new java.sql.Date(Calendar.getInstance().getTimeInMillis())); 
            _con._pst.setInt(3, id);
            _con._pst.executeUpdate();
            
            return true;
            
        } catch (Exception ex) {
            this.AddNotification("Exception" , ex.getMessage());
            Logger.getLogger(CheckListaRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
}
