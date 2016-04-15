var app = angular.module("gplay");

app.config(['$resourceProvider', function($resourceProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
}]);


app.factory("CargoFactory", function ($resource) {
    return $resource("/cargos/:id/", {id: '@id'}, {
        update: {
            method: 'PUT'
        },
        query: { method: 'GET', isArray: false }
    });
});

app.controller("CargosController", ["$scope", "CargoFactory", "toaster",
    function($scope, CargoFactory, toaster) {
        var me = this;

        me.cargo = {};
        me.isIncluir = false;
        me.isListar = true;

        me.loginLoading = false;

        me.adicionarCargo = function(){
            me.isIncluir = true;
            me.isListar = false;
        };

        me.voltar = function(){
            me.isIncluir = false;
            me.isListar = true;
        };

        me.editarCargo = function (cargo) {
            me.cargo = {};
            me.cargo.id = cargo.id;
            me.cargo.nome = cargo.nome;
            me.isIncluir = true;
            me.isListar = false;
        };

        me.cargos = null;

        me.carregarCargos = function carregar(){
            CargoFactory.query(function(data) {
                me.cargos = data.objeto;
            });
        };

        me.salvar = function(){
            me.loginLoading = true;

            if(me.cargo.id == null) {
                CargoFactory.save(me.cargo).$promise.then(function (data) {
                    me.resetarForm(data);
                });
            }else{
                CargoFactory.update(me.cargo).$promise.then(function (data) {
                    me.resetarForm(data);
                });
            };

        };

        me.resetarForm = function(data){
            me.exibirToast(data);
            me.isIncluir = false;
            me.isListar = true;
            me.carregarCargos();
            me.cargo = {};
            me.loginLoading = false;
        };

        me.excluir = function(cargo){
            CargoFactory.delete(cargo).$promise.then(function (data) {
                me.carregarCargos();
                me.exibirToast(data);
            });
        };

        me.carregarCargos();

        me.exibirToast = function(data){
            var type = data.isError == true ? "error" : "success" ;
            toaster.pop({
                type: type,
                title: 'Cargo',
                body: data.mensagem,
                showCloseButton: true
            });
        }

    }
]);