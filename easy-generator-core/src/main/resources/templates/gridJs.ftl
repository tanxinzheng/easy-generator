define(function(){
    return ["$scope",  "${domainObjectClassName}API", "uiaDialog", "$injector", "$uibModal", function($scope, ${domainObjectClassName}API, uiaDialog, $injector, $uibModal){
        $scope.gridOption = {
            id:"user",
            title:'${tableComment}',
            loadEvent: ${domainObjectClassName}API.query,
            ApiService: ${domainObjectClassName}API,
            // 过滤条件列配置
            filters:[
                { name:'keyword', title:'关键字', placeholder:'请输入关键字' }
            ],
            columns:[
            <#if columns?exists>
                <#list columns as field>
                <#if field.javaType = 'Date' >
                { name:'${field['columnName']}', title:'${field['columnComment']}', type:'date' },
                <#elseif field.javaType = 'Boolean' >
                { name:'${field['columnName']}', title:'${field['columnComment']}', type:'checkbox' },
                <#else>
                { name:'${field['columnName']}', title:'${field['columnComment']}' },
                </#if>
                </#list>
            </#if>
            ],
            boxOption : {
                ApiService: ${domainObjectClassName}API,
                columns:[
            <#if columns?exists>
                <#list columns as field>
                    <#if !field.nullable && !field.primaryKey>
                    <#if field.javaType = 'Date' >
                    { name:'${field['columnName']}', title:'${field['columnComment']}', type:'date', rules:{ required: true} },
                    <#elseif field.javaType = 'Boolean' >
                    { name:'${field['columnName']}', title:'${field['columnComment']}', type:'checkbox' },
                    <#else>
                    { name:'${field['columnName']}', title:'${field['columnComment']}', rules:{ required: true} },
                    </#if>
                    </#if>
                    <#if field.nullable >
                        <#if field.javaType = 'Date' >
                        { name:'${field['columnName']}', title:'${field['columnComment']}', type:'date' },
                        <#elseif field.javaType = 'Boolean' >
                        { name:'${field['columnName']}', title:'${field['columnComment']}', type:'checkbox' },
                        <#else>
                        { name:'${field['columnName']}', title:'${field['columnComment']}' },
                        </#if>
                    </#if>
                </#list>
            </#if>
                ]
            }
        };
    }]
});