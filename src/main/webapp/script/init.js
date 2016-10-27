angular.module("appModule",
		["ui.router", 'ngResource', 'avalon.ui', 'ControllerModule', 'ConstantModule', 'highcharts-ng',
		'ui.grid', 'ui.grid.edit', 'ui.grid.resizeColumns', 'ui.grid.pagination', 'ui.grid.selection', 
		'my.ui.grid.autoResize', 'ngResource', 'ui.bootstrap', 'ngclipboard']).
config(function($stateProvider,$urlRouterProvider){
	$urlRouterProvider.otherwise("/main");
	$stateProvider.state("login" ,{
		url : "/login",
		templateUrl: "./templates/loginView.html",
		controller: 'loginController'
	}).state("main" ,{
		url : "/main",
		templateUrl: "./templates/main.html",
		controller: 'mainController'
	}).state("main.monitor" ,{
		url : "/monitor",
		templateUrl: "./templates/monitor.html",
		controller: 'apiMonitorController'
	}).state("main.monitor.monitor-apiWatch",{//01
		url : "/monitor-apiWatch",
		params: {"apiName":null},
		templateUrl: "./templates/monitor-apiWatch.html",
		controller: 'monitorApiWatchController'
	}).state("main.monitor.monitor-userWatch",{//02
		url : "/monitor-userWatch",
		templateUrl: "./templates/monitor-userWatch.html",
		controller: 'monitorUserWatchController'
	}).state("main.monitor.monitor-errorWatch",{//03
		url : "/monitor-errorWatch",
		templateUrl: "./templates/monitor-errorWatch.html",
		controller: 'monitorErrorWatchController'
	}).state("main.apilist" ,{
		url : "/apilist",
		templateUrl: "./templates/apiList.html",
		controller: 'apiListController'
	}).state("main.userinfo" ,{
		url : "/userinfo",
		templateUrl: "./templates/userinfo.html",
		controller: 'userInfoController'
	})
});
