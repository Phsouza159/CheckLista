/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra.contexto;

import domain.objectValues.Notification;
import infra.repositorios.RepositoryBasic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laboratorio
 */
public class Contexto extends Notification {

    public Connection _connection;
    public ResultSet _result;
    public PreparedStatement _pst;
    
     protected boolean OpenConnect() throws SQLException  {
        try {
            Class.forName("com.mysql.jdbc.Driver");//classe para utilização do arquivo com configurções do serivdor mysql
        
            String url = "jdbc:mysql://127.0.0.1:3306/bd_checklista";// drive servidor e banco de dados a serem utilizados e indicação do banco a ser utilizado
            String user ="root";// usuario do banco de dados
            String senha ="";//senha do usuario do banco de dados    
        
            this._connection =(Connection) DriverManager.getConnection(url,user,senha);//metodo que usa os parametros para conectar com o banco
            System.out.println("Conectado ao banco de dados ");
            
            return !this._connection.isClosed();
        } catch (Exception ex) {
            
            this.AddNotification( "Exception :: failed openConnect", ex.getMessage() );
            Logger.getLogger(RepositoryBasic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
     
    public void Transation(){
        try {
            if(this.OpenConnect()){
                this._connection.setAutoCommit(false);
                return;
            }
            throw new SQLException("Failed openConnect from Transation");    
        } catch (SQLException ex) {
            
            this.AddNotification( "SQLException :: open Transation", ex.getMessage() );
            Logger.getLogger(RepositoryBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Rollback(){
        try {
            this._connection.rollback();
            this.CloseConnect();
        } catch (Exception ex) {
            
            this.AddNotification( "Exception", ex.getMessage());
            Logger.getLogger(RepositoryBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CloseConnect() throws Exception{//criando metodo que fechao conexão com o banco
        try{
            if (this._pst!= null) { //limpando os dados de conexão   
                this._pst.close();//fechando o ambiente de conexão
            }
        }catch(Exception ex){
            
            this.AddNotification("Exception :: CloseConnect()", ex.getMessage());
        }
    }
    
    public void Commit(){
        try {
            this._connection.commit();
            this.CloseConnect();
        } catch (Exception ex) {
            
            this.AddNotification( "Exception", ex.getMessage());
            Logger.getLogger(RepositoryBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
