angular.module('multiroom.docs.resources.details', ['ui.router'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('root.resources.details', {
    url: '/:name',
    templateUrl: function($stateParams) {
      return 'app/resources/' + $stateParams.name + '.tpl.html'
    }
  });

}])
;