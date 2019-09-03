

package facade;

import domain.interfaces.ICheckListServices;
import infra.services.CheckListServices;
import java.io.BufferedReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author paulo-pc
 */
public class Facade {

    public static String getJsonRequestByReader(BufferedReader bufferRequest) throws IOException {
        StringBuilder json = new StringBuilder();
        String linha;
        
        while((linha = bufferRequest.readLine()) != null) {
            json.append(linha);  
        }
        
        return json.toString();
    }
    
    public static ICheckListServices getCheckListServices(){
        
        return new CheckListServices();
    }
}
