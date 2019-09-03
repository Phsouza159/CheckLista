"use strict";
import { eLogs } from './enums/atlas.enum';
import { loadModules } from './modules/altas.natives.modules';
class AtlasApp {
    constructor(args) {
        this.core.loadModules = loadModules;
    }
    start(self) {
        let ini = new Promise((resolve, reject) => resolve);
        self.core.ini = ini;
    }
    log(cod, text) {
        let log = [
            /* 000 - log */ (text) => { console.log(text); },
            /* 001 - log */ (text) => { console.info(text); },
            /* 002 - log */ (text) => { console.warn(text); },
            /* 003 - log */ (text) => { console.error(text); },
        ];
        try {
            log[cod](text);
        }
        catch (e) {
            log[eLogs.log](text);
        }
    }
    logView(text) {
        //let id = 'atlas-log', element, p;
        //element = document.getElementById('atlas-log');
        //p = document.createElement('p');
        //p.innerText = text;
        //console.log(text);
        // element.appendChild(p);
    }
}
var get = new Promise((resolve, reject) => {
    var ajax = new XMLHttpRequest();
    ajax.open(httpRequest.get, url, true);
    ajax.send();
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4 && ajax.status == 200) {
            var data = ajax.responseText;
            resolve(data);
        }
        else if (ajax.readyState == 4) {
            reject(ajax.status);
        }
    };
});
return yield Promise.all([get]);
;
onPost: (url) => {
};
;
