/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra.repositorios;

import domain.entidades.EntityBasic;
import domain.interfaces.IRepositoryBasic;
import domain.objectValues.Notification;
import infra.contexto.Contexto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author paulo-pc
 * @param <TEntity>
 */
public abstract class RepositoryBasic<TEntity extends EntityBasic> extends Notification implements IRepositoryBasic<TEntity> {
    
    public RepositoryBasic() {   
    
    }
    
    protected abstract TEntity MappingResultToTEntity(ResultSet resultQuery);
    
    public abstract TEntity GetById(Contexto _con ,int id);
    public abstract List<TEntity> GetAll(Contexto _con);
    public abstract void Add(Contexto _con,TEntity tentity);
    public abstract boolean Update(Contexto _con,TEntity tentity);
    public abstract boolean InativateById(Contexto _con,int id);
    
}
