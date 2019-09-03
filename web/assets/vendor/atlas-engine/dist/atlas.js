(function () {

    var AtlasApp;
    /**
     * 
     * Construtor
     */
    function AtlasCtor(args) {
        // try{
        AtlasApp = new atlas(args);

        window.AtlasApp = AtlasApp

        AtlasApp.start(AtlasApp);
        // }catch(e)
        //  {
        //     new atlas().sys.pagErroCodView(500 , 'Erro interno no servidor :: ' + e);
        //}
    };

    /**
     * Class
     * @param {object} args 
     */
    function atlas(args) {
        if (args != null) {
            if (typeof args != 'object')
                this.log('args não é um objeto', erros.fatal);

            this.logView('atlas init!');

            this.core.vars(args, this);
        }
        else {
            console.warn('Atlas is start. configuracao is null')
        }
        return this;
    };

    /**
     * 
     */
    atlas.prototype.mod = {};

    /**
     * 
     */
    atlas.prototype.start = function (e = this) {
        let self = e;

        let init = new Promise((resolve, reject) => {
            console.log('Atlas is start!');

            resolve(self);
        });

        self.core.init = init;

        self.core.init
            .then((self) => {
                return self.core.loadModules(self);
            })
            .then((self) => {
                return self.core.partialViews(self);
            })
            .then((self) => {
                // partialViews retornar um array
                self = self[0];
                return self.core.loadLinks(self);
            })
            .then((self) => {
                return self.core.loadScripts(self);
            })
            .then((self) => {
                return self.core.loadRouts(self)
            })
            .catch((err) => console.warn(err));
    };


    /**
     * @param text texto do erro
     * @param cod cod do erro 
     */
    atlas.prototype.log = function (text, cod = erros.info) {

        if (cod == erros.fatal) { throw `Erro: ${text}` }
        else if (cod == erros.warn) { console.warn(text); }
        else if (cod == erros.info) { console.info(text); }
    };


    /**
     * @param text texto para gerar na tela view
     */
    atlas.prototype.logView = function (text) {
        let id = 'atlas-log', element, p;

        element = document.getElementById('atlas-log');
        p = document.createElement('p');

        //p.innerText = text;
        console.log(text);
        // element.appendChild(p);
    }

    /**
     * 
     * @param args {} objec com parametros construtor
     * @param self this da atlas
     */
    atlas.prototype.core = function () {
        this.init = {}
    }

    /**
     * configurar variaveis 
     * @param ob {} objec com parametros construtor
     * @param self this da atlas
     */
    atlas.prototype.core.vars = function (ob, self) {

        self.urls = ob.hasOwnProperty('url') ? (Array.isArray(ob.url) ? ob.url : [ob.url]) : ['default'];
        self.id = ob.hasOwnProperty('id') ? ob.id : '#load-html';
        self.index = ob.hasOwnProperty('index') ? ob.index : 'index';
        self.keysLinks = ob.hasOwnProperty('links') ? (Array.isArray(ob.links) ? ob.links : ['index']) : ['index'];
        self.urlName = ob.hasOwnProperty('urlName') ? (Array.isArray(ob.urlName) ? ob.urlName : ['index']) : ['index'];
        self.root = ob.hasOwnProperty('root') ? ob.root : window.location.href;
        self.rout = ob.hasOwnProperty('rout') ? ob.rout : self.log('Rout paramentros invalido', erros.fatal);

        //self.self = ob.hasOwnProperty('exec') ? (typeof ob.exec === "function" ? ob.exec : function () { }) : function () { };
        self.scripts = ob.hasOwnProperty('required') ? (Array.isArray(ob.required.scripts) ? ob.required.scripts : [ob.required.scripts]) : new Array();
        self.links = ob.hasOwnProperty('required') ? (Array.isArray(ob.required.links) ? ob.required.links : [ob.required.links]) : new Array();

        self.porcentagemItem = parseInt(100 / (self.scripts.length + self.links.length + self.urls.length)) + 1;
        self.squardItem = parseInt(280 / self.porcentagemItem) + 1;

        self.modulesJson = ob.hasOwnProperty('modulesJson') ? ob.modulesJson : null;

        self.logView('atlas config var ok!');

        self.dataModules = new Set();

        self.partialViews = {};
        self.partialViews.views = [];
        self.partialViews.objs = [...(ob.hasOwnProperty('required') ? (ob.required.hasOwnProperty('partialViews') ? ob.required.partialViews : []) : [])];

        self.cache = ob;

        return self.core;
    };

    /**
     * Carregar rotas
     */
    atlas.prototype.core.loadRouts = function (self) {

        let rout = self.rout;
        let length = self.rout.length;
        let i = 0;

        rout.map(item => {
            self.sys.OnGet(item.url)
                .then((data) => {

                    item.data = data;

                    self.sys.view.add(item);

                    i++;
                    if (i == length) return true;

                    return false;
                })
                .then((data) => {
                    let index = null;

                    if (data) {
                        index = self.core.rout(self.index);
                        self.core.setRoutView(index);
                    }
                })
                .catch((data) => console.warn(data));
        });
    }
    /**
     * Recuperar Rout
     * @param {string} path da url da rora
     */
    atlas.prototype.core.rout = function (path) {
        let self = globalThis.AtlasApp;
        let response = null;

        for (let r of self.sys.view) {
            if (r.path == path) {
                response = r;
            }
        }

        return response;
    }

    /**
     * Gerar rout na tela
     * @param {objec} rout
     */
    atlas.prototype.core.setRoutView = function (rout, callBack = null) {
        let self = globalThis.AtlasApp;
        let element = document.getElementById(self.id);
        let title = document.getElementsByTagName('title')[0];
        let urlParamtres = window.location.search;

        if (typeof rout != 'object')
            rout = self.core.rout(rout);

        if (typeof callBack == 'object' || callBack == null)
            callBack = self.core.onloadRout;

        if (rout.title != null)
            title.innerHTML = rout.title;

        element.innerHTML = rout.data;

        window.history.pushState("object or string", rout.title, `${rout.path}${urlParamtres}`);

        self.logView(`rota '${rout.name}' carregada com sucesso.`);

        try {
            if (typeof callBack == 'function')
                callBack();

        } catch (e) { console.warn(e) }
    }


    atlas.prototype.core.onloadRout = async function () {


        let init = new Promise((resolve, reject) => {
            let self = globalThis.AtlasApp;

            self.sys.apiQuery();

            resolve(self);
        })

        setTimeout(() => {
            let self = globalThis.AtlasApp;
            self.sys.apiQuery();

        } , 1000);

        return await Promise.all([init]);
    }

    /**
     * Carregar modulos
     */
    atlas.prototype.core.loadModules = async function (self) {
        if (self.modulesJson == null) return self.core;

        let url = self.modulesJson;
        let modules = fetch(url);

        self.logView('atlas load modules init.');

        await modules
            .then((data) => data.json())
            .then((data) => {

                let urlBase = data.urlPathModule;
                let modules = data.modules;

                let body = document.getElementsByTagName('body')[0];

                modules.map(mod => {
                    let script = document.createElement('script');
                    script.src = `${urlBase}${mod.path}`;

                    self.logView(`atlas load modules '${mod.Name}' ok `);

                    body.appendChild(script);
                });
                return self;
            })
            .catch((err) => console.warn(err));

        return self;
    }

    // ** ===================================================== ** //
    // **                       NATIVES                         ** //
    // ** ===================================================== ** //
    /**
     * Carregar links css/less no head
     * @param self :: this object 
     */
    atlas.prototype.core.loadLinks = function (self) {

        let head = document.getElementsByTagName('head')[0];

        self.links.map(link => {

            let linkcChild = document.createElement('link');

            if (link.includes('.less')) {
                linkcChild.rel = rel = 'stylesheet/less';
            }
            else if (link.includes('.css')) {
                linkcChild.rel = rel = 'stylesheet';
            }
            else {
                self.log(`${link} aparentemente não é um link/arquivo css`, erros.warn)
            }
            linkcChild.type = 'text/css';
            linkcChild.href = `${link}`;

            head.appendChild(linkcChild);

            let name = link.split('/');
            name = name[name.length - 1];

            self.logView(`atlas load link css '${name}' ok.`);
        })

        return self;
    };

    /**
     * carregar scripts js no body
     * @param self :: this object
     */
    atlas.prototype.core.loadScripts = function (self) {

        let body = document.getElementsByTagName('body')[0];

        self.scripts.map(script => {
            let scriptChid = document.createElement('script');
            scriptChid.src = `${script}`;

            body.appendChild(scriptChid);


            let name = script.split('/');
            name = name[name.length - 1];

            self.logView(`atlas load script '${name}' ok.`);
        });

        return self;
    }

    /**
     * Importar modulos para app
     * @param {string} name nome do mudulo
     * @param {function} newModule funcao do modulo
     */
    atlas.prototype.core.importModules = function (name, newModule, callBack = '') {
        let self = globalThis.AtlasApp;

        self.mod[name] = newModule;

        if (typeof callBack == 'function') {
            callBack();
        }
    }

    atlas.prototype.core.partialViews = async function (self) {
        //let self = globalThis.AtlasApp;
        let objs = self.partialViews.objs;
        let e = document.getElementById(self.id);

        let promise = new Promise((resolve, reject) => {
            try {
                objs.map(obj => {

                    self.sys.OnGet(obj.view)
                        .then((data) => {

                            let ids = obj.ids;
                            e.innerHTML = data;

                            ids.map(id => {
                                obj.data = document.getElementById(id);

                                self.partialViews.views[`${obj.name}.${id}`] = obj.data;
                            })


                        });
                });
                resolve(self);
                e.innerHTML = null;
            }
            catch (e) { reject(e) }
        });

        promise
            .then((data) => data)
            .catch((data) => {
                console.log(data);
                return globalThis.AtlasApp;
            })

        return await Promise.all([promise]);
    }

    atlas.prototype.core.getPartial = function (value, element) {
        let self = globalThis.AtlasApp;
        let name = value;

        let data = self.partialViews.views[name];

        if (data == null) {
            return console.warn(`partial view : ${name} não encontrada.`);
        }

        element.appendChild(data);
    }

    // ** ===================================================== ** //
    // **                       SYSTEM                          ** //
    // ** ===================================================== ** //

    atlas.prototype.sys = function (self = globalThis.AtlasApp) {

        self.sys.cache = {}
    }

    atlas.prototype.sys.view = new Set();

    atlas.prototype.sys.apiQuery = async function () {

        let self = globalThis.AtlasApp;
        let modules = self.dataModules;

        let natives = [
            { name: '[data-view]', dataset: 'view', method: 'core.getPartial.{data}', type: 'onload' },
            { name: '[data-route]', dataset: 'route', method: 'core.setRoutView.{data}', type: 'onclick' },
        ]

        let args = [...natives, ...modules]

        args.forEach(arg => {

            let elementes = document.querySelectorAll(arg.name);
            let param = arg.method.split('.');
            let fn = self;

            param.map(e => {
                if (!e.includes('{'))
                    fn = fn[e]
            })

            elementes.forEach(element => {
                let value = element.dataset[arg.dataset];
                let runat = element.dataset['runat'];

                if (runat != 'ok') {
                    if (arg.type == 'onload') {

                        fn(value, element);
                    }
                    else {
                        element[arg.type] = function () {
                            fn(value, element);
                        }
                    }

                    element.dataset['runat'] = 'ok';
                }
            });
        });
    }

    /**
     * Requisicao ajax GET
     * @param {string} url da requisicao
     */
    atlas.prototype.sys.OnGet = async function (url) {

        var get = new Promise((resolve, reject) => {

            var ajax = new XMLHttpRequest();

            ajax.open(httpRequest.get, url, true);

            ajax.send()

            ajax.onreadystatechange = function () {
                if (ajax.readyState == 4 && ajax.status == 200) {
                    var data = ajax.responseText;
                    resolve(data);
                }
                else if (ajax.readyState == 4) {
                    reject(ajax.status);
                }
            }
        });

        return await Promise.all([get]);
    }
    /**
     * Requisicao ajax POST
     * @param {string} url
     * @param {objec} data
     */
    atlas.prototype.sys.OnPost = function (url, data) {

        var post = new Promise((resolve, reject) => {

            var ajax = new XMLHttpRequest();

            ajax.open(httpRequest.post, url, true);
            ajax.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

            ajax.send(data);

            ajax.onreadystatechange = function () {

                if (ajax.readyState == 4 && ajax.status == 200) {
                    var data = ajax.responseText;

                    resolve(data);
                }
                else if (ajax.readyState == 4) {
                    reject(ajax.status);
                }
            }
        });
        return post;
    }

    atlas.prototype.sys.queryParametres = function (text) {
        let self = globalThis.AtlasApp;
        let right, left, p1, p2, arg, response;

        if (text.includes('{{') && text.includes('}}')) {
            p1 = text.indexOf('{{');
            p2 = text.indexOf('}}') + 2;

            left = text.substring(0, p1);
            right = text.substring(p2, text.length);
            arg = text.substring(p1, p2);

            response = self.sys.queryParametres.text(arg);

            text = left + response + right;
        }

        if (text.includes('{{') && text.includes('}}')) {
            text = self.sys.queryParametres(text);
        }

        return text;
    }

    atlas.prototype.sys.queryParametres.text = function (text) {
        let self, prop, x, y, f, func, response;
        self = globalThis.AtlasApp;


        if (text.includes('{{') && text.includes('}}')) {
            x = text.indexOf('{{');
            y = text.indexOf('}}');
            f = text.substring(x + 2, y);

            f = self.sys.queryParametres.validarText(f);

            if (typeof f == 'object') {
                return f.response;
            }

            if (f.includes('.')) {
                return self.sys.queryParametres.prop(f);
            }
            else if (typeof f == 'function' || f.includes('(') || f.includes(')')) {
                return self.sys.queryParametres.function(f);
            }
            else {
                prop = f;
            }
        }
        else {
            prop = text;
        }

        response = globalThis[prop];

        if (response == null) {
            return self.log(`QueryParametres :: propriedad '${prop}' undefined.`, erros.warn);
        }
        else if (typeof response == 'function') {
            return self.sys.queryParametres.function(response);
        }
        return response;
    }

    atlas.prototype.sys.queryParametres.validarText = function (text) {
        text = text.toLowerCase();
        if (text == 'false')
            return { response: false };

        else if (text == 'true')
            return { response: true };

        try {
            // let value = parseInt(text);
            // 
            //  if(value == NaN) 
            throw 'invalide';
            //
            // return {response: value};
        }
        catch (e) {
            return text;
        }
    }

    atlas.prototype.sys.queryParametres.prop = function (params) {
        let args, self, e, l, i, r;

        self = globalThis.AtlasApp;

        args = params.split('.');
        e = globalThis[args[0]];

        args[0] = null;

        l = args.length - 1;
        i = 0;
        r = null;

        args.map(arg => {
            if (arg != null) {
                i++
                if (i == l) {
                    if (typeof e[arg] == 'function' || arg.includes('(') || arg.includes(')')) {
                        r = self.sys.queryParametres.function(e[arg])
                    }
                }
                else {
                    e = e[arg];
                }
            }
        })

        return r != null ? r : e;
    }

    atlas.prototype.sys.queryParametres.function = function (params) {
        let self, prop, fun, response;
        self = globalThis.AtlasApp;

        if (typeof params != 'function' && params.includes('(') && params.includes(')')) {
            prop = params.split('(')[0];
            fun = params.split('(')[1];
            fun = fun.split(')')[0];

            response = eval(('prop(' + fun + ')'));

        }
        else {
            response = params();
        }

        if (response == null) {
            self.log(`sys.queryParametres.function :: função sem retorno`, erros.warn);
            return '';
        }

        return response;
    }

    atlas.prototype.sys.pagErroCodView = function (cod, text) {
        let self = globalThis.AtlasApp;
        let e = document.getElementsByTagName('body')[0];

        let data = document.createElement('div');
        let tipo = document.createElement('h1');
        let descricao = document.createElement('h5');
        let section = document.createElement('section');

        tipo.innerText = cod;
        descricao.innerText = text;
        // section.innerHTML = `<button data-rout=\'${self.index}\'>home</button>`
        data.appendChild(tipo)
        data.appendChild(descricao);
        data.appendChild(section);

        e.innerHTML = null;
        e.appendChild(data);

    }

    /**
     * Enums
     */
    var erros = {
        info: 100,
        warn: 300,
        fatal: 500
    }

    var httpRequest = {
        get: "GET",
        post: "POST"
    }

    window.AtlasCtor = AtlasCtor;
    window.atlas = atlas;
})();