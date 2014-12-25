angular.module('multiroom.docs.relations', ['ui.router', 'multiroom.docs.relations.details'])
.config(['$stateProvider', function($stateProvider) {
  $stateProvider.state('root.relations', {
    abstract: true,
    url: '/relations',
    template: '<div ui-view></div>'
  });
}])
;