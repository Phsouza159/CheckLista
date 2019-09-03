/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra.repositorios;

import domain.entidades.SubObjetivo;
import infra.contexto.Contexto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laboratorio
 */
public class SubObjetivoRepository extends RepositoryBasic<SubObjetivo>  {

    @Override
    protected SubObjetivo MappingResultToTEntity(ResultSet resultQuery) {
        
        SubObjetivo element = new SubObjetivo();
        
        try{
            element.setId(/****************/resultQuery.getInt/*******/("id"));
            element.setIdObjetivo(/********/resultQuery.getInt/*******/("ID_CKL_OBJETIVO"));
            element.setTextoSubObjetivo(/**/resultQuery.getNString/***/("objetivo"));
            element.setIsCheck(/***********/resultQuery.getBoolean/***/("CHECK"));
            element.setDataInclusao(/******/resultQuery.getDate/******/("dth_inclusao"));
            element.setDataAlteracao(/*****/resultQuery.getDate/******/("dth_alteracao"));
            element.setDataConclusao(/*****/resultQuery.getDate/******/("dth_conclusao"));
            element.setDataInativacao(/****/resultQuery.getDate/******/("dth_inativacao"));
       
        }catch(Exception ex){
            this.AddNotification("Exception :: MappingResultToTEntity()" , ex.getMessage());
            Logger.getLogger(CheckListaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return element;
    }

    @Override
    public SubObjetivo GetById(Contexto _con ,int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SubObjetivo> GetAll(Contexto _con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Add(Contexto _con ,SubObjetivo tentity) {
        try{
            
             String _query = "INSERT INTO CKL_002_SUBOBJETIVOS(ID,ID_CKL_OBJETIVO,OBJETIVO,`CHECK`,DTH_INCLUSAO) VALUES( NULL , ? ,? ,? , ?)";
            
             _con._pst = (PreparedStatement)_con._connection.prepareStatement(_query);
             
             _con._pst.setInt(1, tentity.getIdObjetivo());
             _con._pst.setString(2, tentity.getTextoSubObjetivo());
             _con._pst.setBoolean(3, false);
             _con._pst.setDate(4, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
                    
             _con._pst.execute();
            
        }catch(Exception ex){
             this.AddNotification("Exception" , ex.getMessage());
            Logger.getLogger(CheckListaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean Update(Contexto _con ,SubObjetivo tentity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean InativateById(Contexto _con ,int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<SubObjetivo> GetCollection(Contexto _con, int idObjetivoPrinciapal) {
        
        List<SubObjetivo> listaSubObjetivo = new ArrayList<SubObjetivo>();
        try{
            String _query = "SELECT * FROM CKL_002_SUBOBJETIVOS WHERE ID_CKL_OBJETIVO = ?";
            
            _con._pst = (PreparedStatement)_con._connection.prepareStatement(_query);
            _con._pst.setInt(1, idObjetivoPrinciapal);
            
            _con._result = _con._pst.executeQuery();
            
            while(_con._result.next()){
                SubObjetivo item = this.MappingResultToTEntity(_con._result);
                listaSubObjetivo.add(item);
            }
            _con._result = null;
            
        }catch(Exception ex){
             this.AddNotification("Exception" , ex.getMessage());
            Logger.getLogger(CheckListaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaSubObjetivo;
    }
}
