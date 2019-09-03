/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.com;

import com.google.gson.Gson;
import domain.enums.ehttp;
import domain.interfaces.INotification;
import domain.objectValues.Notification;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author paulo-pc
 */
public abstract class ServeletBasic extends HttpServlet{

    private Gson _gson;
    
    public ServeletBasic() {
        this._gson = new Gson();
    }
    
    public void Ok(HttpServletResponse response , Object object){
        
        String result = this._gson.toJson(object);
        OutResult(response , result);
    }
    
    public void Ok(HttpServletResponse response , String result) {
        
        OutResult(response , result);
    }
    
    
    public void ErroInterno(HttpServletResponse response , INotification notificacoes){
        response.setStatus(ehttp.ERROINTERNO.getStatus());
        
        String erros = this._gson.toJson(notificacoes.getNotificacoes());
        OutResult(response , erros);
    }
    
     public void ErroInterno(HttpServletResponse response , Exception ex){
        
        response.setStatus(ehttp.ERROINTERNO.getStatus());
        
        Object err = new Object(){ 
            String pilha    = "Erro interno"; 
            String mensagem = ex.getMessage(); 
            String localize = ex.getLocalizedMessage();
        };
        
        String erros = this._gson.toJson( err );
        OutResult(response , erros);
    }
     
    protected void BadRequest(HttpServletResponse response , String result){
        
        response.setStatus(ehttp.BADREQUEST.getStatus());
        
        String erros = this._gson.toJson( result );
        OutResult(response , erros);
    }
    
    protected void View( HttpServletRequest request, HttpServletResponse response, String view){
        try {
            
            RequestDispatcher rd = request.getRequestDispatcher(view);
            rd.forward(request, response);
        } catch (ServletException ex) {
 
            Logger.getLogger(ServeletBasic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
         
            Logger.getLogger(ServeletBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void OutResult(HttpServletResponse response , String result){
        try {
            PrintWriter outResult = response.getWriter();
            
            outResult.print(result);
        } catch (IOException ex) {
            
            Logger.getLogger(ServeletBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
