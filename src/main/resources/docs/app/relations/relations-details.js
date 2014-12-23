angular.module('multiroom.docs.relations.details', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('root.relations.details', {
    url: '/:name',
    templateUrl: function($stateParams) {
      return 'app/relations/' + $stateParams.name + '.tpl.html'
    }
  });

}])
;