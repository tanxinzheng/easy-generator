/**
 * Created by Jeng on 2016/1/28.
 */
define(function () {
    return angular.module("${domainObjectClassName}.REST",[
        "ngResource"
    ]).factory("${domainObjectClassName}API", ["Resource", function(Resource){
        return Resource("/${restMapping}/:id", { id:"@id" });
    }]);
});
