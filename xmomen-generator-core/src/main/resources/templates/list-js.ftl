/**
 * Created by tanxinzheng on 16/7/3.
 */
define(function(){
    return ["$scope", "$modal", "${domainObjectClassName}API", "$dialog", function($scope, $modal, ${domainObjectClassName}API, $dialog){
        $scope.pageSetting = {
            checkAll : false
        };
        $scope.pageInfoSetting = {
            pageSize:10,
            pageNum:1
        };
        $scope.queryParam = {};
        // 查询列表
        $scope.get${domainObjectClassName}List = function(){
            ${domainObjectClassName}API.query({
                keyword: $scope.queryParam.keyword,
                limit: $scope.pageInfoSetting.pageSize,
                offset: $scope.pageInfoSetting.pageNum
            }, function(data){
                $scope.${domainObjectName}List = data.data;
                $scope.pageInfoSetting = data.pageInfo;
                $scope.pageInfoSetting.loadData = $scope.get${domainObjectClassName}List;
            });
        };
        // 全选
        $scope.checkAll = function(a){
            if(!$scope.${domainObjectName}List){
                return;
            }
            var num = 0;
            for (var i = 0; i < $scope.${domainObjectName}List.length; i++) {
                if($scope.${domainObjectName}List[i].checked){
                    num++;
                }
            }
            if($scope.${domainObjectName}List && $scope.${domainObjectName}List.length > 0 && num == $scope.${domainObjectName}List.length){
                $scope.pageSetting.checkAll = true;
            }else{
                $scope.pageSetting.checkAll = false;
            }
        };
        // 新增
        $scope.add = function(index){
            $scope.openModal(index, "ADD");
        };
        // 查看
        $scope.view = function(index){
            $scope.openModal(index, "VIEW");
        };
        // 修改
        $scope.update = function(index){
            $scope.openModal(index, "UPDATE");
        };
        // 弹出
        $scope.openModal = function(index, action){
            $modal.open({
                templateUrl: '${domainObjectName}_detail.html',
                modal:true,
                resolve: {
                    Params: function () {
                        var params = {
                            action: action
                        };
                        if($scope.${domainObjectName}List[index] && $scope.${domainObjectName}List[index].id){
                            params.id = $scope.${domainObjectName}List[index].id;
                        }
                        return params;
                    }
                },
                controller: ['$scope', '$modalInstance', "$modal", "${domainObjectClassName}API", "Params", function($scope, $modalInstance, $modal, ${domainObjectClassName}API, Params){
                    //$scope.${domainObjectName} = null;
                    $scope.pageSetting = {
                        formDisabled : true
                    };
                    if(Params.action == "UPDATE" || Params.action == "ADD"){
                        $scope.pageSetting.formDisabled = false;
                    }
                    if(Params && Params.id){
                        $scope.${domainObjectName} = ${domainObjectClassName}API.get({
                            id: Params.id
                        });
                    }else{
                        $scope.${domainObjectName} = new ${domainObjectClassName}API();
                    }
                    $scope.${domainObjectName}DetailForm = {};
                    $scope.save${domainObjectClassName} = function(){
                        if($scope.${domainObjectName}DetailForm.validator.form()){
                            $scope.${domainObjectName}.$save(function(){
                                $modalInstance.close();
                            });
                        }
                    };
                    $scope.cancel = function(){
                        $modalInstance.dismiss();
                    };
                }]
            }).result.then(function () {
                $scope.get${domainObjectClassName}List();
            }, function () {
                $scope.get${domainObjectClassName}List();
            });
        };
        $scope.delete = function(index){
            ${domainObjectClassName}API.delete({id:$scope.${domainObjectName}List[index].id}, function(){
                $scope.get${domainObjectClassName}List();
            });
        };
        var init = function(){
            $scope.get${domainObjectClassName}List();
        };
        init();
    }]
});