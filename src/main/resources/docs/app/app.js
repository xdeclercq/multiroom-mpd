angular.module('multiroom.docs', ['ui.router', 'multiroom.docs.resources', 'multiroom.docs.relations'])
.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
  $stateProvider.state('root', {
    abstract: true,
    templateUrl: 'app/app.tpl.html'
  });
  $urlRouterProvider.otherwise('/resources/zones');

}])
;