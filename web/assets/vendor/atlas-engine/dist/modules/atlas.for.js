(function(AtlasApp){

    var lacoForOf = (value , element) => {

    }

    AtlasApp.core.importModules('for', function (value, element) {

        let self = globalThis.AtlasApp;

        if(value.includes(';'))
        {
            var lacoFor = ( value , element , self) => {
                let par = value.split(';');
                
                let agr1 = par[0];
        
                if(agr1.trim().includes('var') || agr1.trim().includes('let') )
                {
                   arg1 = agr1.replace('var' , '').trim();
                    
                   let variavel = arg1.includes('=') ? arg1.split('=')[0] : arg1;
                   let val = arg1.includes('=') ? arg1.split('=')[1] : null;
        
                   try{
                     val = parseInt(val);
                     if(val == NaN) throw 'invalide';
        
                   }catch(e) {
                     val = eval(val);
                   }
        
                   if(val != null){
                    globalThis[variavel.trim()] = val;
                   }
                   else {
                    globalThis[variavel.trim()] = variavel;
                   }
                }
        
                let arg2 = par[1];
                let arg3 = par[2];
        
                let html = element.innerHTML;
                let newHtml = '';
        
                while(eval(arg2))
                {
                    newHtml += self.sys.queryParametres(html.trim()) 
                    eval(arg3);
                }
        
                element.innerHTML = newHtml;
            };

            lacoFor(value , element , self);
        }


    } 
    , function() {
        let self = globalThis.AtlasApp;

        self.dataModules.add({
            name: '[data-for]',
            dataset: 'for',
            method: 'mod.for.{data}',
            type: 'onload'
        });
    });

})(AtlasApp);