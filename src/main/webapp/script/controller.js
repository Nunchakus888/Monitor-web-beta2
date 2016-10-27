/*loginView.html*/
angular.module('ControllerModule', [])
.controller('loginController',function ($scope,$state) {
    $scope.login = function (username, password) {
        localStorage.setItem('username', username);
        localStorage.setItem('password', password);
		$state.go("main");
    };
})./*main.html*/
controller('mainController', function ($scope, $state, $stateParams) {
	$scope.$state = $state;
    $scope.input_username = localStorage.getItem('username');
    $state.go("main.monitor");
    $scope.logout = function () {
        $state.go("login");
        localStorage.clear();
    };
})./*monitor.html*/
controller('apiMonitorController', function ($scope, $state, $http, All, $interval) {
	$scope.$state = $state;
    $state.go('main.monitor.monitor-apiWatch');
})./*monitor.html——monitor-apiWatch.html*/
controller('monitorApiWatchController', function ($scope, $state, $stateParams, $http, All, $interval,globalData) {
	$scope.currentApiName = null;
	$scope.upLock = true;
	$scope.downLock = true;
	$scope.pageIndex = 1;
	$scope.pageDown = function(){
		$scope.upLock = false;
		$scope.pageIndex++;
		$scope.currentPage = "第"+ $scope.pageIndex +"页";
		$scope.getApiDetails($scope.tempApiName,$scope.pageIndex);
	}
	$scope.pageUp = function(){
		$scope.pageIndex--;
		$scope.currentPage = "第"+ $scope.pageIndex +"页";
		if($scope.pageIndex == 1){
			$scope.upLock = true;
		}
		$scope.getApiDetails($scope.tempApiName,$scope.pageIndex);
	}
    /*gridOptionsApiFrequency*/
    $scope.gridOptionsApiFrequency = {
        enableSorting: false,
        enableFullRowSelection: true,
        enableColumnMenus: false,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        noUnselect: true,
        enableColumnResizing: true,
        columnDefs: [
            {name: 'rank', displayName: '排名', width:50},
            {name: 'apiName', displayName: 'API名称', width:'*'},
            {name: 'countApi', displayName: 'API使用频率', width:90}
        ],
        onRegisterApi: function (gridApi) {
            $scope.gridApiAM1 = gridApi;
            globalData.monitorApiWatchGrid = gridApi;
            globalData.monitorApiWatchGridRow = gridApi.grid.rows;

            /*if(localStorage.getItem('rows')){
                gridApi.selection.selectRow(gridApi.grid.rows[0]);
            }else {
                localStorage.setItem('rows', gridApi.grid.rows);
            }*/
            globalData.monitorApiWatchGridScope = $scope;
            console.log(gridApi);
            gridApi.selection.on.rowSelectionChanged($scope, function (row, d, i) {
                console.log(gridApi);
                $scope.pageIndex = 1;
            	$scope.currentPage = "第"+ $scope.pageIndex +"页";
            	$scope.upLock = true;
            	$scope.currentApiName = "(" + row.entity.apiName + ")";
            	$scope.tempApiName = row.entity.apiName;
                $scope.getApiDetails(row.entity.apiName,$scope.pageIndex);
                $scope.isTooltipSqlContentShow = false;
            });
        }
    };

    $scope.getApiFrequency = function () {
        $http.get('http://' + All.Ip + '/bond/apiUsageRate')
        .success(function (data) {
            if (data.length < 10) {
                for (var i = 0; i < data.length; i++) {
                    data[i].rank = i + 1;
                }
                $scope.gridOptionsApiFrequency.data = data;
            } else {
                var temp = [];
                for (var i = 0; i < 10; i++) {
                    data[i].rank = i + 1;
                    temp.push(data[i]);
                }
                $scope.gridOptionsApiFrequency.data = temp;
            }
        });
    };

    $scope.getApiFrequency();

    /*gridOptionsApiDelay*/
    $scope.gridOptionsApiDelay = {
        enableSorting: false,
        enableFullRowSelection: true,
        enableColumnMenus: false,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        noUnselect: true,
        enableColumnResizing: true,
        columnDefs: [
            {name: 'rank', displayName: '排名', width:50},
            {name: 'apiName', displayName: 'API名称', width:'*'},
            {name: 'takeTime', displayName: 'API平均延迟/ms', width:120}
        ],
        onRegisterApi: function (gridApi) {
            $scope.gridApiAM2 = gridApi;
            gridApi.selection.on.rowSelectionChanged($scope, function (row, d, i) {
            	$scope.pageIndex = 1;
            	$scope.currentPage = "第"+ $scope.pageIndex +"页";
            	$scope.upLock = true;
            	$scope.currentApiName = "(" + row.entity.apiName + ")";
            	$scope.tempApiName = row.entity.apiName;
                $scope.getApiDetails(row.entity.apiName,$scope.pageIndex);
                $scope.isTooltipSqlContentShow = false;
            });
        }
    };

    $scope.getApiDelay = function () {
        $http.get("http://" + All.Ip + "/bond/sqlTaketime")
        .success(function (data) {
            if (data.length < 10) {
                for (var i = 0; i < data.length; i++) {
                    data[i].rank = i + 1;
                }
                $scope.gridOptionsApiDelay.data = data;
            } else {
                var temp = [];
                for (var i = 0; i < 10; i++) {
                    data[i].rank = i + 1;
                    temp.push(data[i]);
                }
                $scope.gridOptionsApiDelay.data = temp;
            }
        });

    };
    $scope.getApiDelay();

    /*gridOptionsApiDetails*/
    $scope.gridOptionsApiDetails = {
        enableSorting: true,
        enableFullRowSelection: true,
        enableColumnMenus: false,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        noUnselect: false,
        enableColumnResizing: true,
        columnDefs: [
            {name: 'userName', displayName: '用户名', width:90},
            {name: 'msgDetail', displayName: 'sql语句', width:'*'},
            {name: 'countSql', displayName: 'SQL执行成功统计', width:140},
            {name: 'countError', displayName: 'SQL Error统计', width:120},
            {name: 'sqlTakeTime', displayName: 'sql执行平均耗时', width:130},

            /* { name: 'classname', displayName: '类名' },
             { name: 'method', displayName: '方法名' },
             { name: 'msg', displayName: '日志信息' },
             { name: 'sqlcolcount', displayName: 'sql执行查询列数' },
             { name: 'datasource', displayName: '数据源ID' },
             { name: 'sqlrowcount', displayName: 'sql执行查询条数' },*/
        ],
        onRegisterApi: function (gridApi) {
            $scope.gridApiAM3 = gridApi;
            var preRow = undefined;
            gridApi.selection.on.rowSelectionChanged($scope, function (row, d, i) {
                if(row === preRow){
                    $scope.isTooltipSqlContentShow = !$scope.isTooltipSqlContentShow;
                }else {
                    $scope.isTooltipSqlContentShow = true;
                    $scope.tooltipSqlContent = row.entity.msgDetail;
                    preRow = row;
                }

            });
        }
    };

    $scope.getApiDetails = function (apiName, page) {
//      $scope.gridOptionsApiDetails.data = [];
        $http({
            method: "POST",
            url: "http://" + All.Ip + "/bond/apiUsagedetails",
            data: {"apiName": apiName, "page": page}//apiName, page,必选参数
        }).success(function (data) {
        	if(data.length<20){
        		$scope.downLock = true;
        	}else{
                $scope.downLock = false;
        	}
            $scope.gridOptionsApiDetails.data = data;
        });
    }
    
    if($stateParams.apiName){
    	$scope.currentApiName = "(" + $stateParams.apiName + ")";
    	$scope.pageIndex = 1;
    	$scope.currentPage = "第"+ $scope.pageIndex +"页";
    	$scope.getApiDetails($stateParams.apiName,$scope.pageIndex);
    }

})./*API监控页面——用户监控*/
controller('monitorUserWatchController', function ($scope, $state, $http, All, $interval, globalData) {
	$scope.upLock = true;
	$scope.downLock = true;
	$scope.pageIndex = 1;
	$scope.pageDown = function(){
		$scope.upLock = false;
		$scope.pageIndex++;
		$scope.currentPage = "第"+ $scope.pageIndex +"页";
		$scope.getUserDelay($scope.tempUserName,$scope.pageIndex);
		
	}
	$scope.pageUp = function(){
		$scope.pageIndex--;
        $scope.currentPage = "第"+ $scope.pageIndex +"页";
		if($scope.pageIndex == 1){
			$scope.upLock = true;
		}
		$scope.getUserDelay($scope.tempUserName,$scope.pageIndex);
	}
    /*gridOptionsUserFrequency*/
    $scope.gridOptionsUserFrequency = {
        enableSorting: false,
        enableFullRowSelection: true,
        enableColumnMenus: false,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        noUnselect: true,
        enableColumnResizing: true,
        columnDefs: [
            {name: 'rank', displayName: '排名'},
            {name: 'userName', displayName: '用户名称'},
            {name: 'apiUsageRage', displayName: '用户访问次数'}
        ],
        onRegisterApi: function (gridApi) {
            $scope.gridApiUM1 = gridApi;
            gridApi.selection.on.rowSelectionChanged($scope, function (row, d, i) {
            	$scope.pageIndex = 1;
            	$scope.currentPage = "第"+ $scope.pageIndex +"页";
            	$scope.upLock = true;
            	$scope.tempUserName = row.entity.userName;
                $scope.getUserDelay(row.entity.userName, $scope.pageIndex);
            });
        }
    };
    $scope.getApiFrequency = function () {
        $http.get("http://" + All.Ip + "/bond/userUsageApiRate")
        .success(function (data) {
            if (data.length < 10) {
                for (var i = 0; i < data.length; i++) {
                    data[i].rank = i + 1;
                }
                $scope.gridOptionsUserFrequency.data = data;
            } else {
                var temp = [];
                for (var i = 0; i < 10; i++) {
                    data[i].rank = i + 1;
                    temp.push(data[i]);
                }
                $scope.gridOptionsUserFrequency.data = temp;
            }
        });
    }
    $scope.getApiFrequency();

    /*gridOptionsUserDelay*/
    $scope.gridOptionsUserDelay = {
        enableSorting: false,
        enableFullRowSelection: true,
        enableColumnMenus: false,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        noUnselect: true,
        enableColumnResizing: true,
        columnDefs: [
            {name: 'apiName', displayName: 'API名称'},
            {name: 'apiRate', displayName: 'API访问次数'}
        ],
        onRegisterApi: function (gridApi) {
            $scope.gridApiAM2 = gridApi;
            gridApi.selection.on.rowSelectionChanged($scope, function (row, d, i) {
            	$state.go("main.monitor.monitor-apiWatch",{apiName:row.entity.apiName});
                globalData.monitorUserWatchGrid = gridApi;
                globalData.monitorUserWatchGridRow = row;

                // globalData.monitorApiWatchGrid.selection.raise.rowSelectionChanged(globalData.monitorApiWatchGridScope);
                globalData.monitorApiWatchGrid.selection.selectRow(globalData.monitorApiWatchGridRow);
                console.log(globalData.monitorApiWatchGrid);
            });
        }
    };

    $scope.getUserDelay = function (user,page) {
        $http({
            method: "POST",
            url: "http://" + All.Ip + "/bond/aUserUsageApiList",
            data: {"userName":user,"page":page}
        }).success(function (data) {
        	if(data.length<10){
        		$scope.downLock = true;
        	}else{
        		$scope.downLock = false;
        	}
            $scope.gridOptionsUserDelay.data = data;
        });
    }

    angular.element(document).ready(function () {
        console.log(globalData.monitorApiWatchGridScope);
        console.log(globalData.monitorApiWatchGrid);
        console.log(globalData.monitorApiWatchGrid.grid.element[0].childNodes[2].childNodes[2].childNodes[2].childNodes[1].childNodes[1]);
        console.log(globalData.monitorApiWatchGridRow);
        var ele = angular.element(globalData.monitorApiWatchGrid.grid.element[0].childNodes[2].childNodes[2].childNodes[2].childNodes[1].childNodes[1])
        console.log(ele[0]);
        console.log(angular.element(ele[0]).addClass('ui-grid-row ng-scope ui-grid-row-selected'));

        // ele[0].addClass('ui-grid-row ng-scope ui-grid-row-selected');
        globalData.monitorApiWatchGrid.selection.raise.rowSelectionChanged(globalData.monitorApiWatchGridRow[1]);
        //ui-grid-row ng-scope ui-grid-row-selected

    });


})./*API监控页面——错误监控*/
controller('monitorErrorWatchController', function ($scope, $state, $http, All, $interval) {
	$scope.searchBtnFlag = false;
	$scope.errorSearchFlag = false;
	$scope.upLock = true;
	$scope.downLock = true;
	$scope.pageIndex = 1;
	$scope.tempSearchJson = {"userName":"","className":"","method":"","apiName":"","dataSource":"","page":$scope.pageIndex};
	$scope.currentPage = "第"+ $scope.pageIndex +"页";
	$scope.pageDown = function(){
		$scope.upLock = false;
		$scope.pageIndex++;
        $scope.currentPage = "第"+ $scope.pageIndex +"页";
		$scope.getErrorLog($scope.pageIndex);
		
	}
	$scope.pageUp = function(){
		$scope.pageIndex--;
		$scope.currentPage = "第"+ $scope.pageIndex +"页";
		if($scope.pageIndex == 1){
			$scope.upLock = true;
		}
		$scope.getErrorLog($scope.pageIndex);
	}
	$scope.errorSearchBtn = function(){
		$scope.searchBtnFlag = true;
		$scope.errorSearchFlag = true;
	}
	$scope.errorSearchCancel = function(){
		$scope.userNameModel = "";
		$scope.classNameModel = "";
		$scope.methodModel = "";
		$scope.apiNameModel = "";
		$scope.dataSourceModel = "";
		$scope.errorSearchFlag = false;
		$scope.searchBtnFlag = false;
	}
	$scope.errorSearchSubmit = function(a,b,c,d,e){
		$scope.pageIndex = 1;
		$scope.tempSearchJson = {"userName":a,"className":b,"method":c,"apiName":d,"dataSource":e,"page":$scope.pageIndex};
		$scope.currentPage = "第"+ $scope.pageIndex +"页";
		for(var i in $scope.tempSearchJson){
		    if(!$scope.tempSearchJson[i]){
		    	$scope.tempSearchJson[i] = "";
		    }
		}
		$scope.getErrorLog($scope.pageIndex);
		$scope.searchBtnFlag = false;
		$scope.errorSearchFlag = false;
	}
    /*gridOptionsErrorLog*/
    $scope.gridOptionsErrorLog = {
        enableSorting: false,
        enableFullRowSelection: true,
        enableColumnMenus: false,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        noUnselect: true,
        enableColumnResizing: true,
        columnDefs: [
            {name: 'logId', displayName: '日志ID', width:90},
            {name: 'logType', displayName: '日志类型', width:90},
            {name: 'userName', displayName: '用户名', width:90},
            {name: 'className', displayName: '类名'},
            {name: 'method', displayName: '方法名'},
            {name: 'createTime', displayName: '日志产生时间', width:160},
//          {name: 'logLevel', displayName: '日志级别', width:90},
            {name: 'msg', displayName: '日志信息'},
            {name: 'sqlRowCount', displayName: 'sql执行查询条数'},
            {name: 'msgDetail', displayName: 'sql执行语句'},
            {name: 'apiName', displayName: '执行的API名称'},
            {name: 'sqlColcount', displayName: 'sql执行查询的列数'},
            {name: 'sqlTakeTime', displayName: 'sql执行耗时/ms'},
            {name: 'dataSource', displayName: '数据源ID'}
        ],
        onRegisterApi: function (gridApi) {
            $scope.gridApiAM3 = gridApi;
        }
    };
    $scope.getErrorLog = function (page) {
    	$scope.tempSearchJson.page = page;
        $http({
            method: "POST",
            url: "http://" + All.Ip + "/bond/error",
            data: $scope.tempSearchJson
        }).success(function (data) {
        	if(data.length<20){
        		$scope.downLock = true;
        	}else{
        		$scope.downLock = false;
        	}
            $scope.gridOptionsErrorLog.data = data;
        });
    }
    $scope.getErrorLog($scope.pageIndex);
    
}).controller('apiListController', function ($scope, $state) {

}).controller('userInfoController', function ($scope, $state) {

});

