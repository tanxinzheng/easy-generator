<div class="hbox hbox-auto-xs hbox-auto-sm" ng-init="
    app.settings.asideFolded = false;
    app.settings.asideDock = false;
  " >
  <!-- main -->
  <div class="col">
    <!-- main header -->
    <div class="bg-light lter b-b wrapper-md">
      <div class="row">
        <div class="col-sm-6 col-xs-12">
          <h1 class="m-n font-thin h3 text-black">${tableComment}</h1>
        </div>
      </div>
    </div>
    <!-- / main header -->
    <div class="wrapper-md">
      <div class="panel panel-default">
        <div class="panel-heading">
        ${tableComment}
        </div>
        <div class="row wrapper">
          <div class="col-sm-3">
            <div class="input-group">
              <input type="text" ng-model="queryParam.keyword" data-ng-keyup="get${domainObjectClassName}List()" class="input-sm form-control" placeholder="请输入关键字进行模糊查询">
              <span class="input-group-btn">
                <button class="btn btn-sm btn-default" data-ng-click="get${domainObjectClassName}List()" type="button">查询</button>
                <button class="btn btn-sm btn-default" data-ng-click="add()" type="button">新增</button>
              </span>
            </div>
          </div>
        </div>
        <div class="table">
          <table class="table table-striped b-t b-light">
            <thead>
            <tr>
              <th>
                <label class="i-checks m-b-none">
                  <input type="checkbox"
                         name="checkAll"
                         data-ng-change="checkAll()"
                         ng-model="pageSetting.checkAll"><i></i>
                </label>
              </th>
            <#if columns?exists>
              <#list columns as field>
              <#if !field.primaryKey>
              <th>${field['columnComment']}</th>
              </#if>
              </#list>
            </#if>
              <th class="action">操作</th>
            </tr>
            </thead>
            <tbody>
              <tr data-ng-repeat="item in ${domainObjectName}List">
                <td>
                  <label class="i-checks m-b-none">
                    <input type="checkbox"
                           ng-model="item.checked"
                           data-ng-checked="item.checked"
                           data-ng-true-value="true"
                           data-ng-false-value="false"><i></i>
                  </label>
                </td>
            <#if columns?exists>
              <#list columns as field>
                <#if !field.primaryKey>
                <td>
                  <a ng-bind="item.${field['columnName']}"></a>
                </td>
                </#if>
              </#list>
            </#if>
                <td class="action">
                  <div class="btn-group open" dropdown="">
                    <button type="button" class="btn btn-sm btn-default" data-ng-click="view($index)">查看</button>
                    <button type="button" class="btn btn-sm btn-default dropdown-toggle" dropdown-toggle>
                      <span class="caret"></span>
                      <span class="sr-only">Split button!</span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                      <li><a href="javascript:void(0)" data-ng-click="view($index)">查看</a></li>
                      <li><a href="javascript:void(0)" data-ng-click="update($index)">修改</a></li>
                      <li class="divider"></li>
                      <li><a href="javascript:void(0)" data-ng-click="delete($index)">删除</a></li>
                      <li><a href="javascript:void(0)" data-ng-click="remove($index)">作废</a></li>
                    </ul>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <footer class="panel-footer">
          <div class="row">
            <div class="col-sm-6">
              <label class="i-checks m-b-none">
                <input type="checkbox"
                       name="checkAll"
                       data-ng-checked="checkAll()"
                       ng-model="pageSetting.checkAll"
                       data-ng-true-value="true"
                       data-ng-false-value="false"><i></i>
              </label>
              <div class="btn-group open" dropdown="">
                <button type="button" class="btn btn-sm btn-default dropdown-toggle" dropdown-toggle>
                  批量操作
                  <span class="caret"></span>
                  <span class="sr-only">Split button!</span>
                </button>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="javascript:void(0)" data-ng-click="batchExport($index)"><i class="fa fa-download">&nbsp;&nbsp;批量导出</i></a></li>
                  <li class="divider"></li>
                  <li><a href="javascript:void(0)" data-ng-click="batchDelete($index)"><i class="fa fa-minus">&nbsp;&nbsp;批量删除</i></a></li>
                  <li><a href="javascript:void(0)" data-ng-click="batchRemove($index)"><i class="fa fa-trash">&nbsp;&nbsp;批量作废</i></a></li>
                </ul>
              </div>
            </div>
            <div class="col-sm-6 text-right text-center-xs">
              <ug-pagination page-info="pageInfoSetting"></ug-pagination>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
  <!-- / main -->
</div>
<script type="text/ng-template" id="${domainObjectName}_detail.html">
  <div class="modal-header">
    <h3 class="modal-title">${tableComment}</h3>
  </div>
  <div class="modal-body">
    <div class="widget-body">

      <form class="form-horizontal" ug-validate="${domainObjectName}DetailForm" name="${domainObjectName}DetailFormName">
        <fieldset>
        <#if columns?exists>
          <#list columns as field>
          <#if !field.primaryKey>
          <div class="form-group">
              <label class="col-md-2 control-label">${field['columnComment']}<span class="vaild">*</span></label>
              <div class="col-md-10">
                  <#if field['javaType'] = 'Boolean'>
                      <label class="i-switch bg-primary m-t-xs m-r">
                          <input type="checkbox" name="${field['columnName']}"
                                 data-ng-true-value="true"
                                 data-ng-false-value="false"
                                 ng-disabled="pageSetting.formDisabled"
                            <#if !field.nullable>
                                 required="true"
                            </#if>
                                 ng-model="${domainObjectName}.${field['columnName']}">
                          <i></i>
                      </label>
                  <#else>
                      <input class="form-control" type="text" name="${field['columnName']}" placeholder="请输入${field['columnComment']}"
                             ng-disabled="pageSetting.formDisabled"
                        <#if !field.nullable>
                             required="true"
                        </#if>
                             data-rule-maxlength="${field.maxLength}"
                             ng-model="${domainObjectName}.${field['columnName']}">
                  </#if>
              </div>
          </div>
          </#if>
          </#list>
        </#if>

          <div class="form-group" ng-if="errors">
            <label class="col-md-2 control-label"></label>
            <div class="col-md-10 error">
              {{errors.message}}
            </div>
          </div>
        </fieldset>
      </form>
    </div>
  </div>
  <div class="modal-footer">
    <button class="btn btn-primary" ng-disabled="pageSetting.formDisabled" ng-if="!pageSetting.formDisabled" type="submit" data-ng-click="save${domainObjectClassName}()">
      <i class="fa fa-save"></i>
      保存
    </button>
    <button class="btn btn-default" ng-click="cancel()">
      <i class="icon icon-close"></i>
      关闭</button>
  </div>
</script>
