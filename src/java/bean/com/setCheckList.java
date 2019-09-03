/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.com;

import domain.entidades.CheckLista;
import domain.enums.ehttp;
import domain.interfaces.ICheckListServices;
import domain.interfaces.ICheckLista;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import facade.Facade;
import java.util.List;

/**
 *
 * @author paulo-pc
 */
@WebServlet(name = "setCheckList", urlPatterns = {"/addItem"})
public class setCheckList extends ServeletBasic {

    private ICheckListServices _checkListServices;
    
    public setCheckList() {
        super();
        
        this._checkListServices = Facade.getCheckListServices();
    }
    
    // <editor-fold defaultstate="collapsed" desc="REST REQUEST TYPE GET | GET ALL , GET BY ID(INT)">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        this._checkListServices.Clear();
        
        try{
            
           String id = request.getParameter("id");
           
           if(id == null){
               
               this.RequestGetALL(response);
           }
           else {
               
               this.RequestGetById(response, id);
           }
        }catch(Exception ex){
            
            this.ErroInterno(response, ex);
        }
    }
    
    private void RequestGetALL(HttpServletResponse response) {
        List<CheckLista> collectionCheckLista = this._checkListServices.GetAll();
        
        if(_checkListServices.isValid())
            this.Ok(response , collectionCheckLista );

        else
            this.ErroInterno(response, this._checkListServices);
    }
    
    private void RequestGetById(HttpServletResponse response, String id) {
        try{
            ICheckLista checkLista = this._checkListServices.GetById(Integer.parseInt(id));
            if(_checkListServices.isValid())
                this.Ok(response , checkLista );

            else
                this.ErroInterno(response, this._checkListServices);
        
        }catch(NumberFormatException ex){
                       
            this.BadRequest( response , ex.getMessage() );
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="REST REQUEST TYPE POST | NEW CHECKLIST">
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        this._checkListServices.Clear();
        
        try{
            
            String json         = Facade.getJsonRequestByReader(request.getReader());
            ICheckLista lista   = this._checkListServices.Deserialize(json);

            this._checkListServices.AddCheckLista(lista);
            
            if(this._checkListServices.isValid()){
                
                response.setStatus(ehttp.CREATED.getStatus());
                this.Ok(response , lista);
            }
            else
               this.ErroInterno(response, this._checkListServices );
            
        }catch(Exception ex){
            
            this.ErroInterno(response, ex);
        }
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="REST REQUEST TYPE DELET BY ID(INT)">
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setContentType("application/json;charset=UTF-8");
            this._checkListServices.Clear();
            
            String id = request.getParameter("id");
            
            if(id == null)
                this.BadRequest(response, "Paramentro id é necessário");
            
            else {
                
                this.RequestDELETCheckLista(response , id);
            }
            
        }catch(Exception ex) {
            
            this.ErroInterno(response, ex);
        }
    }
    
    private void RequestDELETCheckLista(HttpServletResponse response , String id){
        try{
            int _id = Integer.parseInt(id);
            this._checkListServices.InativarCheckListaById(_id);
            
            if(_checkListServices.isValid())
                this.Ok( response , "{ \"status\" : \"Ok\"}");   
            
            else {
                this.ErroInterno(response, _checkListServices);
            }
    
        }catch(NumberFormatException ex){
            this.BadRequest(response, "O Paramentro id deve ser do tipo inteiro :: valor passado \"" + id + "\"");
        }
    }
    
    // </editor-fold>
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
