(function () {
    var app = angular.module("gplay");

    app.run([
            'defaultErrorMessageResolver',
            function (defaultErrorMessageResolver) {
                defaultErrorMessageResolver.setI18nFileRootPath('/public/bower_components/angular-auto-validate/dist/lang');
                defaultErrorMessageResolver.setCulture('pt-BR');
            }
    ]);

    app.config(['$resourceProvider', function($resourceProvider) {
        $resourceProvider.defaults.stripTrailingSlashes = false;
    }]);


    app.factory("AnalistaFactory", function ($resource) {
        return $resource("/analistas/:id/", {id: '@id'}, {
            update: {
                method: 'PUT'
            },
            query: { method: 'GET', isArray: false }
        });
    });

    app.controller("AnalistasController", ["$scope", "AnalistaFactory", "toaster", "$resource",
        function($scope, AnalistaFactory, toaster, $resource) {
            var me = this;

            me.loginLoading = false;

            me.analista = {};
            me.isIncluir = false;
            me.isListar = true;
            me.listaAnalistas = {};
            me.cargos = {};

            me.adicionar = function(){
                me.analista = {};
                me.isIncluir = true;
                me.isListar = false;
                me.carregarCargos();
            };

            me.carregarCargos = function(){
                var Cargos = $resource('/cargos/', {}, {query: { method: 'GET', isArray: false }});
                Cargos.query(function (data) {
                    me.cargos = data.objeto;
                });
            }

            me.voltar = function(){
                me.isIncluir = false;
                me.isListar = true;
            };

            me.editar = function (analista) {
                me.carregarCargos();
                me.analista = {};
                me.analista.id = analista.id;
                me.analista.nome = analista.nome;
                me.analista.especialidade = analista.especialidade;
                me.analista.cargo = {};
                me.analista.cargo.id = analista.cargo.id;
                me.isIncluir = true;
                me.isListar = false;
            };

            me.carregar = function carregar(){
                AnalistaFactory.query(function(data) {
                    me.listaAnalistas = data.objeto;
                });
            };

            me.salvar = function(){
                me.loginLoading = true;

                if(me.analista.id == null) {
                    AnalistaFactory.save(me.analista).$promise.then(function (data) {
                        me.resetarForm(data);
                    });
                }else{
                    AnalistaFactory.update(me.analista).$promise.then(function (data) {
                        me.resetarForm(data);
                    });
                };

            };

            me.resetarForm = function(data){
                me.exibirToast(data);
                me.isIncluir = false;
                me.isListar = true;
                me.carregar();
                me.analista = {};
                me.loginLoading = false;

            };

            me.excluir = function(analista){
                AnalistaFactory.delete(analista).$promise.then(function (data) {
                    me.carregar();
                    me.exibirToast(data);
                });
            };

            me.carregar();

            me.exibirToast = function(data){
                var type = data.isError == true ? "error" : "success" ;
                toaster.pop({
                    type: type,
                    title: 'Analista',
                    body: data.mensagem,
                    showCloseButton: true
                });
            }

        }
    ]);

}());