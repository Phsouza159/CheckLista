var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
let loadModules = (self) => __awaiter(this, void 0, void 0, function* () {
    if (self.modulesJson == null) {
        return self;
    }
    let url = self.modulesJson;
    let modules = fetch(url);
    yield modules
        .then((data) => data.json())
        .then((data) => {
        let urlBase = data.urlPathModule;
        let modules = data.modules;
        let body = document.getElementsByTagName('body')[0];
        modules.map(mod => {
            let script = document.createElement('script');
            script.src = `${urlBase}${mod.path}`;
            //self.logView(`atlas load modules '${mod.Name}' ok `);
            body.appendChild(script);
        });
        return self;
    })
        .catch((err) => console.warn(err));
    return self;
});
let loadRouts = (self) => __awaiter(this, void 0, void 0, function* () {
    let rout = self.rout;
    let length = self.rout.length;
    let i = 0;
    rout.map(item => {
        self.sys.onGet(item.url)
            .then((data) => {
            //item.data = data;
            //self.sys.view.add(item);
            i++;
            if (i == length)
                return true;
            return false;
        })
            .then((data) => {
            let index = null;
            if (data) {
                //index = self.core.rout(self.index);
                //self.core.setRoutView(index);
            }
        })
            .catch((data) => console.warn(data));
    });
});
export { loadModules };
