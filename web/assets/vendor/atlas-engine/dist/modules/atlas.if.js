(function (AtlasApp) {

    AtlasApp.core.importModules('if', function (value, element) {

        let self = globalThis.AtlasApp;
        let arg = value;
        let vdd = self.sys.queryParametres.text(`{{${arg}}}`);

        if (vdd) {
            element.hidden = false;
        }
        else {
            element.hidden = true;
            element.innerHTML = null;
        }

    }, function () {

        let self = globalThis.AtlasApp;

        self.dataModules.add({
            name: '[data-if]',
            dataset: 'if',
            method: 'mod.if.{data}',
            type: 'onload'
        });

    })

})(AtlasApp);

