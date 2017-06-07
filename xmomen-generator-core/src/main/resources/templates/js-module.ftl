'use strict';

<#include "header.ftl">
define([
    "angularAMD",
    "./${domainObjectUnderlineName}.api",
    "./${domainObjectUnderlineName}"
],function(angularAMD, ${domainObjectClassName}Rest, ${domainObjectName}){
    angular.module('${domainObjectUnderlineName}.module',[
        "${domainObjectClassName}.REST"
    ]).config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider,   $urlRouterProvider) {

            var states = [];

            states.push({
                title: "${tableComment}",
                name: 'app.${domainObjectName}',
                url: '/${restMapping}',
                views: {
                    '${domainObjectName}': angularAMD.route({
                        controller: ${domainObjectName},
                        //controllerUrl: "${moduleName}/${domainObjectUnderlineName}.js",
                        templateUrl: 'modules/${moduleName}/${domainObjectUnderlineName}.html'
                    })
                },
                sticky: true
            });

            angular.forEach(states, function(state){
                $stateProvider.state(state.name, angularAMD.route(state));
            });
        }
    ]);
});