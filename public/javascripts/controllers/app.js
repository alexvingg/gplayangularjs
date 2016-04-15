var app = angular.module("gplay", [
    'ngResource',
    'toaster',
    'ngAnimate',
    'jcs-autoValidate',
    'angularjs-dropdown-multiselect',
    'ui.router',
    'angular-ladda'
]);

app.run([
    'defaultErrorMessageResolver',
    function (defaultErrorMessageResolver) {
        defaultErrorMessageResolver.setI18nFileRootPath('/public/bower_components/angular-auto-validate/dist/lang');
        defaultErrorMessageResolver.setCulture('pt-BR');
    }
]);


app.config(function($resourceProvider, $stateProvider, $urlRouterProvider, laddaProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;

    //
    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/cargos");
    //
    // Now set up the states
    $stateProvider
        .state('cargos', {
            url: "/cargos",
            templateUrl: "/public/templates/cargos/index.html"
        })
        .state('analistas', {
            url: "/analistas",
            templateUrl: "/public/templates/analistas/index.html"
        })
        .state('projetos', {
            url: "/projetos",
            templateUrl: "/public/templates/projetos/index.html"
        });

    //laddaProvider.setOption({ /* optional */
    //    style: 'expand-left',
    //    spinnerSize: 50
    //});
});