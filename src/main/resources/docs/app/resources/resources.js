angular.module('multiroom.docs.resources', ['ui.router', 'multiroom.docs.resources.details'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('root.resources', {
    abstract: true,
    url: '/resources',
    template: '<div ui-view></div>'
  });
}])
;