/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.entidades.EntityBasic;
import infra.contexto.Contexto;
import java.util.List;

/**
 *
 * @author paulo-pc
 * @param <TEntity>
 */
public interface IRepositoryBasic<TEntity extends EntityBasic> extends INotification{
    abstract TEntity GetById(Contexto _con , int id);
    abstract List<TEntity> GetAll(Contexto _con);
    void Add(Contexto _con,TEntity tentity);
    boolean Update(Contexto _con,TEntity tentity);
    boolean InativateById(Contexto _con,int id);
}
