/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.entidades.SubObjetivo;
import infra.contexto.Contexto;
import java.util.List;

/**
 *
 * @author laboratorio
 */
public interface ISubObjetivoRepository extends IRepositoryBasic<SubObjetivo>{
    List<SubObjetivo> GetCollection(Contexto _con, int idObjetivoPrinciapal);
}
