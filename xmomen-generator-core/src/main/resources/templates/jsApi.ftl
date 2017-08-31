/**
 * Created by Jeng on 2016/1/28.
 */
angular.module("App.REST").factory("${domainObjectClassName}API", ["Resource", function(Resource){
    return Resource("${restMapping}/:id", { id:"@id" });
}]);
