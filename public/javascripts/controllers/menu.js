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

app.controller("MenuController", function($scope, $location) {
    $scope.menuClass = function(page) {

        var current = $location.$$absUrl.split("/")[$location.$$absUrl.split("/").length-1];
        return page === current ? "active" : "";
    };
});

