(function () {
    var app = angular.module("gplay");


    app.factory("ProjetosFactory", function ($resource) {
        return $resource("/projetos/:id/", {id: '@id'}, {
            update: {
                method: 'PUT'
            },
            query: { method: 'GET', isArray: false }
        });
    });

    app.controller("ProjetosController", ["$scope", "ProjetosFactory", "toaster", "$resource",
        function($scope, ProjetosFactory, toaster, $resource) {
            var me = this;

            me.loginLoading = false;

            $scope.example14settings = {
                displayProp: 'nome',
                idProp: 'id',
            };

            me.translate = {checkAll:'Todos', uncheckAll:'Desmarcar Todos', buttonDefaultText:'Selecione', dynamicButtonTextSuffix:'Selecionado(s)'};


            me.projeto = {analistas : [], requisitos: []};

            me.isIncluir = false;
            me.isListar = true;
            me.listaProjetos = [];
            me.analistas = [];

            me.adicionar = function(){
                me.projeto = {analistas : [], requisitos: []};
                me.isIncluir = true;
                me.isListar = false;
                me.carregarAnalistas();
            };

            me.carregarAnalistas = function(){
                var Analistas = $resource('/analistas/', {}, {query: { method: 'GET', isArray: false }});
                Analistas.query(function (data) {
                    me.analistas = data.objeto;
                });
            }

            me.voltar = function(){
                me.isIncluir = false;
                me.isListar = true;
            };

            me.editar = function (projeto) {
                me.analistas = [];
                me.projeto = {analistas : [], requisitos: []};
                me.carregarAnalistas();
                me.projeto.id = projeto.id;
                me.projeto.nome = projeto.nome;
                me.projeto.descricao = projeto.descricao;
                me.projeto.dataInicio = projeto.dataInicio;
                me.projeto.dataFim = projeto.dataFim;
                me.projeto.analistas = projeto.analistas;
                me.isIncluir = true;
                me.isListar = false;
            };

            me.carregar = function (){
                ProjetosFactory.query(function(data) {
                    me.listaProjetos = data.objeto;
                });
            };

            me.salvar = function(){

                //console.log(me.projeto);
                me.loginLoading = true;
                if(me.projeto.id == null) {
                    ProjetosFactory.save(me.projeto).$promise.then(function (data) {
                        me.resetarForm(data);
                    });
                }else{
                    ProjetosFactory.update(me.projeto).$promise.then(function (data) {
                        me.resetarForm(data);
                    });
                };

            };

            me.resetarForm = function(data){
                me.exibirToast(data);
                me.isIncluir = false;
                me.isListar = true;
                me.carregar();
                me.projeto = {};
                me.loginLoading = false;
            };

            me.excluir = function(projeto){
                ProjetosFactory.delete(projeto).$promise.then(function (data) {
                    me.carregar();
                    me.exibirToast(data);
                });
            };

            me.carregar();

            me.exibirToast = function(data){
                var type = data.isError == true ? "error" : "success" ;
                toaster.pop({
                    type: type,
                    title: 'Projeto',
                    body: data.mensagem,
                    showCloseButton: true
                });
            }

        }
    ]);

}());