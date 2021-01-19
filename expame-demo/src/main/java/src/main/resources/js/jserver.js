/*支持jserver（专门为基于jquery的easyui提供后台的服务）的js库*/

var jserver = {};

var cisUrl = window.location.href
var tempUrl = cisUrl.substring(0, cisUrl.indexOf("cis")) + "cis/";

/*
 * 通过后台服务加载数据 
 * 参数：
 * 	server:后台服务名
 *  method:配置在sql-config.xml中的id
 * 	params:传入参数，包括了各个服务的固定参数，如下：
 * 	 1.jserver
 * 	 2.tree
 * 		id:被外连的列一般为主键,结果集中有此列可不配置
 *		text:text列,结果集中有此列可不配置
 *		parentId:外连的列一般为外键,结果集中有此列可不配置
 *   3.treegrid
 *		id:被外连的列一般为主键,结果集中有此列可不配置
 *		parentId:外连的列一般为外键,结果集中有此列可不配置
 *   4.combobox
 * 		id:id列,结果集中有此列可不配置
 *		text:text列,结果集中有此列可不配置
 * 	 5.combotree:同tree
 * 	 6.combogrid:同grid
 *  callback:回调函数，包含code、msg、obj的json数据，不同的服务对应不同的obj格式
 *   {"code":1,"msg":"成功","obj":[{"id":1,"name":"李小"},{"id":2,"name":"张三"}]}
 *  index:window.top.layer创建的等待窗口的索引，如果有，错误的时候将其关闭
 */
jserver.loadData = function (server, method, params, callback, index) {
    //把server,method作为参数添加进params
    params.method = method;
    params.server = server;
    //判断并解析params是否数组
    var split = '~!';
    for (index1 in params) {
        var item = params[index1];
        if ($.isArray(item)) { //子对象如果为数组，数据库不支持，转换为字符串
            var str = '';
            for (index2 in item) {
                for (child in item[index2]) {
                    str = str + item[index2][child] + ',';
                }
                str += split;
            }
            params[index1] = str;
        }
    }
    $.post(tempUrl + "jserver", params, function (data, status, xhr) {
        //console.log(JSON.stringify(data));
        //检查服务访问
        if (status == null || status != "success") {
            if (index) window.top.layer.close(index);
            //			$.messager.alert('提示', "访问服务失败：" + JSON.stringify(xhr), 'error');
            layer.msg("访问服务失败：<br/>" + JSON.stringify(xhr), {
                time: 0, // 不自动关闭
                icon: 0,
                title: '提示',
                btnAlign: 'c',
                btn: ['确定'],
                yes: function (index) {
                    layer.close(index);
                }
            });
            return;
        }
        data = JSON.parse(data);
        // 检查执行
        if (data.code != '1') {
            if (index) window.top.layer.close(index);
            //			$.messager.alert('提示', "错误信息：" + data.msg, 'error');
            layer.msg("错误信息：<br/>" + data.msg, {
                time: 0, // 不自动关闭
                icon: 0,
                title: '提示',
                btnAlign: 'c',
                btn: ['确定'],
                yes: function (index) {
                    layer.close(index);
                }
            });
            return;
        }
        //回调函数
        if (callback)
            callback(data);
    });
};

/**
 * 多数据查询(现已支持，jserver、tree、combo 服务)
 * 示例：
 * var params = [{
    server: 'jserver',
    method: 'get职业',
    params: {值1: 'sjfljsfl', 值2: 'sjdfljsj'},
    dataName: 'zy'
    }, {
        server: 'jserver',
        method: 'get职务',
        params: {值1: 'sjfljsfl', 值2: 'sjdfljsj'},
        dataName: 'zw'
    }];
 jserver.loadMultipleData(params, function (data) {
        console.log(data)
    });
 * @param params
 * @param callback
 */
jserver.loadMultipleData = function (params, callback) {
    $.post(tempUrl + "jserver", {server: 'multiple', params: params}, function (data, status, xhr) {
        //console.log(JSON.stringify(data));
        //检查服务访问
        if (status == null || status != "success") {
            if (index) window.top.layer.close(index);
            //			$.messager.alert('提示', "访问服务失败：" + JSON.stringify(xhr), 'error');
            layer.msg("访问服务失败：<br/>" + JSON.stringify(xhr), {
                time: 0, // 不自动关闭
                icon: 0,
                title: '提示',
                btnAlign: 'c',
                btn: ['确定'],
                yes: function (index) {
                    layer.close(index);
                }
            });
            return;
        }
        data = JSON.parse(data);
        // 检查执行
        if (data.code != '1') {
            if (index) window.top.layer.close(index);
            //			$.messager.alert('提示', "错误信息：" + data.msg, 'error');
            layer.msg("错误信息：<br/>" + data.msg, {
                time: 0, // 不自动关闭
                icon: 0,
                title: '提示',
                btnAlign: 'c',
                btn: ['确定'],
                yes: function (index) {
                    layer.close(index);
                }
            });
            return;
        }
        //回调函数
        if (callback)
            callback(data);
    });
};

/*
 * 保存数据 
 * 参数： 
 * 	method:调用的方法 
 * 	params:参数 
 *  callback:回调函数
 *  text:等待/加载提示信息
 */
jserver.update = function (method, params, callback, text) {
    if (text === '' || text === undefined) text = '请稍后。。。';
    var index = window.top.layer.msg(text, {
        icon: 16,
        shade: 0.01,
        time: 0 //不自动关闭
    });
    jserver.loadData(tempUrl + 'jserver', method, params, function (data) {
        window.top.layer.close(index);
        // 检查执行
        if (data.code = '1') {
            layer.msg('保存成功', {icon: 1});
            if (callback)
                callback(data);
        } else {
            layer.msg('保存失败', {icon: 2});
        }
    }, index);
};

/*
 * 保存数据 (不带提示框) 
 * 参数： 
 * @user 谢东生
 * 	method:调用的方法 
 * 	params:参数 
 *  callback:回调函数
 */
jserver.updateNoAlert = function (method, params, callback) {
    jserver.loadData(tempUrl + 'jserver', method, params, function (data) {
        //回调函数
        if (callback)
            callback(data);
    });
};

/*
 * 删除数据 
 * 参数： 
 * 	method:调用的方法 
 * 	params:参数 
 *  callback:回调函数
 */
jserver.delete = function (method, params, callback) {
    window.top.layer.confirm('确认是否删除?', {icon: 3, title: '消息', shadeClose: true}, function (idx) {
        layer.close(idx);
        var index = window.top.layer.msg("请稍后...", {
            icon: 16,
            shade: 0.01,
            time: 0 //不自动关闭
        });
        jserver.loadData(tempUrl + 'jserver', method, params, function (data) {
            window.top.layer.close(index);
            // 检查执行
            if (data.code = '1') {
                layer.msg('删除成功!', {icon: 1});
                if (callback)
                    callback(data);
            } else {
                layer.msg('删除失败!', {icon: 2});
            }
        }, index);
    });
};


/*
 * 删除数据不带提示框
 * 参数：
 * 	method:调用的方法
 * 	params:参数
 *  callback:回调函数
 */
jserver.deleteNoAlert = function (method, params, callback) {
    jserver.loadData(tempUrl + 'jserver', method, params, function (data) {
        // 检查执行
        if (data.code = '1') {
            window.top.layer.msg('删除成功!', {icon: 1});
            if (callback)
                callback(data);
        } else {
            window.top.layer.msg('删除失败!', {icon: 2});
        }
    }, 0);
};

/*
 * 加载表格数据 
 * 参数： 
 * 	controlName:控件名，datagrid或combogrid名称
 * 	listMethod:获取列表数据的方法名称(对应sql-config.xml中的配置)
 * 	totalMethod:获取总数的方法名称(对应sql-config.xml中的配置)
 *  params:传递的参数
 *  page:当前页
 *  rows:每页行数
 *  callback:回调函数
 * 返回值：无
 */
jserver.loadDataGridData = function (controlName, listMethod, totalMethod, params, page, rows, callback) {
    //列表参数增加page与rows,在后台作为系统参数获取值
    var listParams = params;
    listParams.page = page;
    listParams.rows = rows;

    //查询列表数据
    jserver.loadData(tempUrl + 'jserver', listMethod, listParams, function (listData) {
        //查询总数
        jserver.loadData(tempUrl + 'jserver', totalMethod, params, function (totalData) {
            var data = {
                total: jserver.getMsgObjFirstValue(totalData),
                rows: listData.obj
            };
            //根据datagrid与combogrid来判断方法
            var pager;
            if (controlName == "" || controlName == null) {
                if (callback) {
                    callback(data);
                }
                return;
            }
            if ($("#" + controlName).attr("class").indexOf('combogrid') > 0) {
                $("#" + controlName).combogrid('grid').datagrid("loadData", data);
                pager = $("#" + controlName).combogrid('grid').datagrid('getPager');
            } else {
                $("#" + controlName).datagrid("loadData", data);
                pager = $("#" + controlName).datagrid('getPager');
            }
            //设置分页事件
            if (pager) {
                $(pager).pagination({
                    //刷新
                    onRefresh: function (page, rows) {
                        jserver.loadDataGridData(controlName, listMethod, totalMethod, params, page, rows, callback);
                    },
                    //改变每页行数
                    onChangePageSize: function (rows) {
                        jserver.loadDataGridData(controlName, listMethod, totalMethod, params, page, rows, callback);
                    },
                    //改变页码
                    onSelectPage: function (page, rows) {
                        jserver.loadDataGridData(controlName, listMethod, totalMethod, params, page, rows, callback);
                    }
                });
            }
            //回调函数
            if (callback)
                callback(data);
        });
    });
};


/*
 * 获取所有选择行的某列数据 (逗号分隔)
 * 参数： 
 * 	table:表格名称 
 * 	column:列名
 */
jserver.getSelectedData = function (table, column) {
    if (column == null || column.length <= 0) {
        //		$.messager.alert('提示', "列名不能为空！", 'info');
        layer.msg("列名不能为空！", {
            time: 0, // 不自动关闭
            icon: 0,
            title: '提示',
            btnAlign: 'c',
            btn: ['确定'],
            yes: function (index) {
                layer.close(index);
            }
        });
        return;
    }
    var rows = $('#' + table).datagrid('getSelections'); // 获取选中行的数据
    if (rows.length == 0 || rows == '' || rows == null) {
        //		$.messager.alert('提示', "请先选择一行！", 'info');
        window.top.layer.msg("请先选择一行！");
        return;
    }
    var _data;
    for (var i = 0; i < rows.length; i++) {
        if (_data == undefined) {
            _data = rows[i][column];
        } else {
            _data = _data + ',' + rows[i][column];
        }

    }
    return _data;
};

/*
 * 对某结点下的所有input框清除数据
 * 参数： 
 *  id:节点id
 */
jserver.resetValues = function (id) {
    $("#" + id + " :input").each(function () {
        var id = $(this).attr("id");
        var cls = $(this).attr("class");
        if (typeof(id) == "undefined" || typeof(cls) == "undefined") return;
        if (id.indexOf('_easyui_textbox_input') >= 0) {
            return;
        }
        if (id.indexOf('filebox') >= 0) {
            return;
        }
        if (cls.indexOf('combobox') >= 0) {
            $(this).combobox('setValue', '');
            return;
        }
        if (cls.indexOf('combotree') >= 0) {
            $(this).combotree('setValue', '');
            return;
        }
        if (cls.indexOf('combogrid') >= 0) {
            $(this).combogrid('setValue', '');
            return;
        }
        if (cls.indexOf('combotreegrid') >= 0) {
            $(this).combotreegrid('setValue', '');
            return;
        }
        $(this).textbox('setValue', '');
    });
    //集成tagboxBUG修复
    $("#" + id + " .tagbox span," + "#" + id + " input").each(function () {
        var cls = $(this).attr("class");
        if (typeof(id) == "undefined" || typeof(cls) == "undefined") return;
        if (cls.indexOf('tagbox-label') >= 0 && $(this).text() == '') {
            $(this).remove();
            return;
        }
        if (cls.indexOf('textbox-value') >= 0 && $(this).attr('name') == '' && $(this).attr('value') == '') {
            $(this).remove();

        }
    })
};

/*
 * 根据Msg对象中obj的名称找到domId下的input控件，然后对obj的名称对应值赋给input控件
 * 参数： 
 *  domId:dom结点id
 *  msg:msg的json对象
 * 	msg格式：{"code":"1","msg":"success","obj":[{"name":"李小","性别":"男"}]}
 * 返回值：
 * 	无
 */
jserver.setValues = function (domId, msg) {
    if (!(jserver.isJson(msg))) return "";
    for (var key in msg.obj) {
        for (var key1 in msg.obj[key]) {
            var t_id = "#" + domId + " #" + key1;
            //bug:没有对domId做限定
            if ($(t_id)) {
                var cls = $(t_id).attr('class');
                if (!cls) {
                    //对隐藏域input进行赋值
                    if ($(t_id)[0] && $(t_id)[0].tagName.toLowerCase() == 'input') {
                        var val = msg.obj[key][key1];
                        $(t_id).val(val);
                    } else {

                    }
                } else if (cls.indexOf('combotree') > 0) {
                    var arr = msg.obj[key][key1]; //多选时要求传入数组
                    $(t_id).combotree('setValue', arr.split(','));
                } else if (cls.indexOf('combobox') > 0) { //自动兼容tagbox，因为tagbox是它的子类
                    var arr = msg.obj[key][key1]; //多选时要求传入数组
                    if (cls.indexOf('tagbox') > 0 && arr == '') continue; //easyui-tagbox支持
                    $(t_id).combobox('setValue', arr.split(','));
                } else if (cls.indexOf('combogrid') > 0) {
                    var arr = msg.obj[key][key1]; //多选时要求传入数组
                    $(t_id).combogrid('setValue', arr.split(','));
                } else if (cls.indexOf('ueditor') > 0) { //Ueditor支持
                    // 给ueditor赋值
                    var content = msg.obj[key][key1];
                    UE.getEditor(key1 + '_ueditor').setContent(content);
                } else {
                    //alert(key1+','+msg.obj[key][key1]);
                    $(t_id).textbox("setValue", msg.obj[key][key1]);
                }
            }
        }
    }
};

/*
 * 根据Msg对象中obj的名称找到domId下的带ID的节点，然后对obj的名称对应值赋给带ID节点
 * 参数： 
 *  domId:dom结点id
 *  msg:msg的json对象
 * 	msg格式：{"code":"1","msg":"success","obj":[{"name":"李小","性别":"男"}]}
 * 返回值：
 * 	无
 */

jserver.setIdValues = function (domId, msg) {
    jserver.resetValues(domId);
    if (!(jserver.isJson(msg))) return "";
    for (var key in msg.obj) {
        for (var key1 in msg.obj[key]) {
            var t_id = "#" + domId + " #" + key1;
            //bug:没有对domId做限定
            if ($(t_id)) {
                $(t_id).html(msg.obj[key][key1]);
            }
        }
    }
};

/*
 * 对dom结点下的所有支持数据源标签，根据id及value生成json对象
 * 参数： 
 *  domId:dom结点id
 *  sDomIds:特殊格式dom节点id集合，由外部维护，通过','分割
 *  splitStr:通过特殊格式节点id按照支持设置的value值的分割符，不传入则设置为普通json，否则设置为分隔符格式
 * 返回值：
 * 	根据id及value生成的json对象
 */
jserver.getInputData = function (domId, sDomIds, splitStr) {
    var data = {};
    var id;
    var cls;
    //检查必填项
    if (!$('#' + domId).form('validate')) {
        //		$.messager.alert('提示', "请检查必填项！", 'info');
        layer.msg("请检查必填项！", {
            time: 0, // 不自动关闭
            icon: 0,
            title: '提示',
            btnAlign: 'c',
            btn: ['确定'],
            yes: function (index) {
                layer.close(index);
            }
        });
        return null;
    }
    //特殊格式表单支持(兼容单条数据),例：datagrid表格输入
    if (null != sDomIds) {
        var ids = sDomIds.split(",");
        for (var i = 0; i < ids.length; i++) {
            cls = $('#' + ids).attr('class');
            if (cls.indexOf('datagrid') >= 0) { //datagrid表格数据支持
                if (splitStr == null) //设置为json格式
                    data[ids] = $('#' + ids).datagrid('getData').rows;
                else { //设置为分隔符格式
                    data[ids] = '未支持分隔符格式';
                }
            } else if (cls.indexOf('combobox') >= 0) { //combobox多行数据或单行但是需添加分隔符支持
                if (splitStr == null) //设置为json格式
                    data[ids] = '未支持json格式';
                else { //设置为分隔符格式
                    data[ids] = $('#' + ids).combobox('getValues').join(splitStr);
                }
            }
        }
    }
    //普通格式表单支持
    $("#" + domId + " select," + "#" + domId + " input," + "#" + domId + " textarea").each(function () {
        id = $(this).attr("id");
        cls = $(this).attr('class');
        if (id == sDomIds) return;
        if (typeof(id) == "undefined" || id.indexOf("_easyui_textbox_input") >= 0 || !$('#' + id)[0]) {
            return;
        }
        if (typeof(cls) == "undefined") {
            if ($('#' + id)[0].tagName.toLowerCase() == 'input') { //未添加class的input支持
                data[$(this).attr("id")] = $(this).val();
            }
        } else {
            if (cls.indexOf("combogrid") >= 0) { //easyui-combogrid支持
                data[$(this).attr("id")] = $(this).combogrid('getValue');
            } else if (cls.indexOf("combobox") >= 0) { //easyui-combobox支持
                if (cls.indexOf("tagbox") >= 0) { //easyui-tagbox支持
                    data[$(this).attr("id")] = $(this).tagbox('getValues').join(',');
                } else {
                    data[$(this).attr("id")] = $(this).combobox('getValue') ? $(this).combobox('getValue') : null;
                }
            } else if (cls.indexOf("ueditor") >= 0) { //Ueditor支持
                var curid = $(this).attr("id");
                // 从Ueditor获取值赋值到隐藏域
                data[curid] = UE.getEditor(curid + '_ueditor').getContent();
            } else if (cls.indexOf('datetimebox') >= 0) {
                data[$(this).attr("id")] = $(this).val();
            } else if (cls.indexOf('datebox') >= 0) {
                if ($(this).textbox('getText') != null && $(this).textbox('getText') != "") {
                    value = utils.toDateFromString($(this).textbox('getText'));
                    if (value == null || value == "") {
                        layer.msg("请填写正确的时间格式（yyyymmdd）")
                        return;
                    } else {
                        data[$(this).attr("id")] = value;
                    }
                } else {
                    value = $(this).val();
                    data[$(this).attr("id")] = value;
                }
            } else if (cls.indexOf('numberbox') >= 0) {
                data[$(this).attr("id")] = Math.floor($(this).val() * 100) / 100;
            }
            else { //已添加class的input支持
                data[$(this).attr("id")] = $(this).val();
            }
        }
    });
    return data;
};

/**
 * 获取表单中的 name值
 * @param domId
 */
jserver.getFromData = function(domId){
    var params = {};
    //input
    $("#" + domId + " select," + "#" + domId + " input," + "#" + domId + " textarea").each(function () {
        if($(this).attr('name') != null && $(this).attr('name') != '' && $(this).attr('name') != undefined ){
            if($(this).attr("type") == 'radio'){
                params[$(this).attr("name")] = $("input[name='"+$(this).attr("name")+"']:checked").val();
            }else if(params.hasOwnProperty($(this).attr("name"))){
                params[$(this).attr("name")] = params[$(this).attr("name")]+"|"+$(this).val()
            }else{
                params[$(this).attr("name")] = $(this).val()
            }

        }
    });
return params

}
/**
 * 获取页面数据
 */
jserver.getParamsData = function (title) {

    if (!$('#' + title).form('validate')) {
        //		$.messager.alert('提示', "请检查必填项！", 'info');
        layer.msg("请检查必填项！", {
            time: 0, // 不自动关闭
            icon: 0,
            title: '提示',
            btnAlign: 'c',
            btn: ['确定'],
            yes: function (index) {
                layer.close(index);
            }
        });
        return null;
    }
    var inputs = $('#' + title + ' input[tid],#' + title + ' select[tid]');
    var data = {};

    for (var i = 0; i < inputs.length; i++) {
        var key = '', value = '';
        if ($(inputs[i]).hasClass('easyui-textbox')) {
            key = $(inputs[i]).attr('tid');
            value = $(inputs[i]).val();
        } else if ($(inputs[i]).hasClass('easyui-numberbox')) {
            key = $(inputs[i]).attr('tid');
            value = $(inputs[i]).numberbox('getValue');
        } else if ($(inputs[i]).hasClass('easyui-combotree')) {
            key = $(inputs[i]).attr('tid');
            value = $(inputs[i]).combotree('getValue');
        } else if ($(inputs[i]).hasClass('easyui-datebox')) {
            key = $(inputs[i]).attr('tid');
            if ($(inputs[i]).textbox('getText') != null && $(inputs[i]).textbox('getText') != "") {
                value = utils.toDateFromString($(inputs[i]).textbox('getText'));
                if (value == null || value == "") {
                    layer.msg("请填写正确的时间格式（yyyymmdd）")
                    return null;
                }
            } else {
                value = $(inputs[i]).val();
            }
        } else if ($(inputs[i]).hasClass('easyui-combogrid')) {
            key = $(inputs[i]).attr('tid');
            value = $(inputs[i]).combogrid('getValue');
            if (key == '个人ID' && utils.isNull(value)) {
                data['姓名'] = $(inputs[i]).combogrid('getText');
            } else {
                data['姓名'] = '';
            }
        } else if ($(inputs[i]).hasClass('easyui-combobox')) {
            key = $(inputs[i]).attr('tid');
            value = $(inputs[i]).combobox('getValues');
            var t_d = '';
            for (var k = 0; k < value.length; k++) {
                if (!utils.isNull(t_d)) t_d += ',';
                t_d += value[k];
            }
            value = t_d;
        } else if ($(inputs[i]).hasClass('easyui-radiobox')) {
            key = $(inputs[i]).attr('tid');
            value = $('#editDetails div[data-id=' + title + '] input[tid=' + key + ']').radiobox('getValues');
            if (data.hasOwnProperty(key)) continue;
        } else if ($(inputs[i]).hasClass('easyui-checkbox')) {
            key = $(inputs[i]).attr('tid');
            value = $(inputs[i]).checkbox('isCheck');
            value = value ? 1 : 0;
        } else {
            key = $(inputs[i]).attr('tid');
            value = $(inputs[i]).val();
        }
        if (!utils.isNull(key)) data[key] = value;
    }
    return data;
}


/*
 * 获取Msg对象中obj的第一个元素值
 * 参数： 
 *  msg:msg的json对象
 * 	msg格式：{"code":"1","msg":"success","obj":[{"name":"李小","性别":"男"}]}
 * 返回值：
 * 	第一个obj对象的值。eg:上面的格式将返回：李小
 */
jserver.getMsgObjFirstValue = function (msg) {
    if (!(jserver.isJson(msg))) return "";
    for (var key in msg.obj) {
        for (var key1 in msg.obj[key]) {
            return msg.obj[key][key1];
        }
    }
};

/*
 * 判断obj是否为json对象  
 * 参数： 
 *  obj:对象
 * 返回值：
 * 	true or false
 */

jserver.isJson = function (obj) {
    if (obj == null) return false;
    var isJson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
    return isJson;
};

/*
 * 比较两个日期的大小（传入的参数推荐是"yyyy-mm-dd"的格式，其他的日期格式也可以，但要保证一致）
 * 参数：
 *   date1:目标日期
 * 	 date2:被比较日期
 * 返回值：
 *   a比b小返回true，反之返回false
 */
var dateCompare = function (date1, date2) {
    if (date1 && date2) {
        var a = new Date(date1);
        var b = new Date(date2);
        return a < b;
    }
};

/* 
 * 比较两个时间的大小（传入的参数是"HH:mm"的格式，）
 * 参数：
 *   date1:目标时间
 * 	 date2:被比较时间
 * 返回值：
 *   time1比time2小返回true，反之返回false
 */
var timeCompare = function (time1, time2) {
    try {
        if (time1 && time2) {
            var t1 = parseInt(time1.split(":")[0] * 60) + parseInt(time1.split(":")[1]);
            var t2 = parseInt(time2.split(":")[0] * 60) + parseInt(time2.split(":")[1]);
            return t1 < t2;
        }
        return false;
    } catch (e) {
        return false;
    }
};
/* 
 * 比较两个时间的大小，支持的格式可在formatArr扩展
 * 参数：
 *   datetime1:目标时间
 * 	 datetime2:被比较时间
 * 返回值：
 *   datetime1比datetime2小返回true，反之返回false
 */
var dateTimeCompare = function (datetime1, datetime2) {
    var formatArr = ['YYYY-MM-DD',
        'YYYY-MM-DD HH:mm',
        'YYYY-MM-DD HH:mm:ss']; //支持的格式
    try {
        if (datetime1 && datetime2) {
            var dt1 = moment(datetime1, formatArr);
            var dt2 = moment(datetime2, formatArr);
            return dt1 < dt2;
        }
        return false;
    } catch (e) {
        return false;
    }
};

/*
 * 对validatebox的扩展  
 */
// $.extend($.fn.validatebox.defaults.rules, {
//     minLength: {
//         validator: function (value, param) {
//             return value.length >= param[0];
//         },
//         message: '长度最小 {0}字符'
//     },
//     idcard: { // 验证身份证
//         validator: function (value) {
//             //return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
//             var flag = checkIdcard(value);
//             return flag == true ? true : false;
//         },
//         message: '身份证号码格式不正确'
//     },
//     phone: { // 验证电话号码
//         validator: function (value) {
//             return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
//         },
//         message: '格式不正确,请使用下面格式:010-88888888'
//     },
//     mobile: { // 验证手机号码
//         validator: function (value) {
//             return /^(13|15|18)\d{9}$/i.test(value);
//         },
//         message: '手机号码格式不正确'
//     },
//     intOrFloat: { // 验证整数或小数
//         validator: function (value) {
//             return /^\d+(\.\d+)?$/i.test(value);
//         },
//         message: '请输入数字，并确保格式正确'
//     },
//     currency: { // 验证货币
//         validator: function (value) {
//             return /^\d+(\.\d+)?$/i.test(value);
//         },
//         message: '货币格式不正确'
//     },
//     qq: { // 验证QQ,从10000开始
//         validator: function (value) {
//             return /^[1-9]\d{4,9}$/i.test(value);
//         },
//         message: 'QQ号码格式不正确'
//     },
//     integer: { // 验证整数
//         validator: function (value) {
//             return /^[+]?[1-9]+\d*$/i.test(value);
//         },
//         message: '请输入整数'
//     },
//     age: { // 验证年龄
//         validator: function (value) {
//             return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
//         },
//         message: '年龄必须是0到120之间的整数'
//     },
//     chinese: { // 验证中文
//         validator: function (value) {
//             return /^[\Α-\￥]+$/i.test(value);
//         },
//         message: '请输入中文'
//     },
//     english: { // 验证英语
//         validator: function (value) {
//             return /^[A-Za-z]+$/i.test(value);
//         },
//         message: '请输入英文'
//     },
//     unnormal: { // 验证是否包含空格和非法字符
//         validator: function (value) {
//             return /.+/i.test(value);
//         },
//         message: '输入值不能为空和包含其他非法字符'
//     },
//     username: { // 验证用户名
//         validator: function (value) {
//             return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
//         },
//         message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
//     },
//     faxno: { // 验证传真
//         validator: function (value) {
//             return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
//         },
//         message: '传真号码不正确'
//     },
//     zip: { // 验证邮政编码
//         validator: function (value) {
//             return /^[0-9]\d{5}$/i.test(value);
//         },
//         message: '邮政编码格式不正确'
//     },
//     ip: { // 验证IP地址
//         validator: function (value) {
//             return /d+.d+.d+.d+/i.test(value);
//         },
//         message: 'IP地址格式不正确'
//     },
//     name: { // 验证姓名，可以是中文或英文
//         validator: function (value) {
//             return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
//         },
//         message: '请输入姓名'
//     },
//     msn: {
//         validator: function (value) {
//             return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
//         },
//         message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
//     },
//     selectNotNull: { //select空值验证
//         validator: function (value, param) {
//             //console.info(value);
//             return $(param[0]).find("option:contains('" + value + "')").val() != '';
//             //return value!='';
//         },
//         message: "请选择"
//     },
//     compareDate: { //比较日期选择器
//         validator: function (value, param) {
//             return dateCompare($(param[0]).datebox("getValue"), value);
//         },
//         message: "结束日期不能小于或等于开始日期"
//     },
//     compareTime: { //比较时间选择器（时分秒）
//         validator: function (value, param) {
//             return timeCompare($(param[0]).timespinner("getValue"), value);
//         },
//         message: "结束时间不能小于或等于开始时间"
//     },
//     compareDateTime: { //比较时间选择器（时分秒）
//         validator: function (value, param) {
//             return dateTimeCompare($(param[0]).timespinner("getValue"), value);
//         },
//         message: "结束时间不能小于或等于开始时间"
//     }
// });

//校验身份证合法性
function checkIdcard(idcard) {
    var Errors = ["验证通过!",
        "身份证号码位数不对!",
        "身份证号码出生日期超出范围或含有非法字符!",
        "身份证号码校验错误!",
        "身份证地区非法!"];
    var area = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外"
    };

    //var idcard=idcard;
    var Y, JYM;
    var S, M;
    var idcard_array = [];
    idcard_array = idcard.split("");
    //地区检验
    if (area[parseInt(idcard.substr(0, 2))] == null) {
        return Errors[4];
    }

    //身份号码位数及格式检验
    switch (idcard.length) {
        case 15:
            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
            } else {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
            }

            if (ereg.test(idcard)) {
                return true;

            } else {
                return Errors[2];
            }
            break;
        case 18:
            //18位身份号码检测
            //出生日期的合法性检查
            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
            if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
            } else {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
            }
            if (ereg.test(idcard)) { //测试出生日期的合法性
                //计算校验位
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 +
                    (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 +
                    (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 +
                    (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 +
                    (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 +
                    (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 +
                    (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 +
                    parseInt(idcard_array[7]) * 1 +
                    parseInt(idcard_array[8]) * 6 +
                    parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1); //判断校验位
                if (M == idcard_array[17]) {
                    return true;
                } else {
                    return Errors[3];
                }
            } else {
                return Errors[2];
            }
            break;
        default:
            return Errors[1];
            break;
    }
}

/**
 * 功能权限筛选函数
 * @ obj 可用实体类,属性有:控件ID,图标样式,名称,位置
 * @ lineID 需要放置到顶部(或其它自定义水平排列区域)的区域的id
 * @ menuID 需要放置到行内的菜单的区域的id
 * @ special 特殊功能额外放置的回调函数(参数:外部已经循环扫描后的可用实体类;返回值:是否跳过其它区域的添加)
 * 注 位置: 1,普通的功能(顶部);2,常规菜单功能(下拉菜单)
 */
jserver.powerChoose = function (obj, lineID, menuID, special) {
    $('#' + lineID).empty();
    $('#' + menuID).empty();
    for (var i = 0; i < obj.length; i++) {
        //参数获取
        var id = obj[i].控件ID;
        var style = obj[i].图标样式;
        var name = obj[i].名称;
        var position = obj[i].位置;
        var html = '';
        //console.log(id+','+name);
        //抽出特殊的功能到列表中(列内)
        if (special && $.isFunction(special)) {
            if (special(obj[i])) { //跳过其他区域的添加
                continue;
            }
        }
        //位置判断
        switch (position) {
            case '1': //抽出普通的功能到顶部(顶部横向排列)
                html = " <a href='#' id='" + id + "' class='easyui-linkbutton " + id + "' iconCls='" + style + "'>" + name + "</a>";
                $('#' + lineID).append(html);
                $('#' + id).linkbutton();
                break;
            case '2': //常规功能加载(下拉菜单)
                html = "<div id='" + id + "'class='" + id + "' data-options=\"iconCls:'" + style + "'\">" + name + "</div>";
                $('#' + menuID).append(html);
                break;
        }
    }
};

/*===================下载文件
 * options:{
 * url:'',  //下载地址
 * data:{name:value}, //要发送的数据
 * method:'post'
 * }
 */
jserver.DownLoadFile = function (options) {
    var config = $.extend(true, {method: 'post'}, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
    $form.attr('action', config.url);
    for (var key in config.data) {
        $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
    }
    $iframe.append($form);
    $(document.body).append($iframe);
    $form[0].submit();
    $iframe.remove();
};

/**
 * 同步下载单个文件
 * @ url 需要请求的服务器url路径,若为""或null，则使用默认的url
 * @ filename 需要下载的文件的hash文件名
 */
jserver.downloadSingle = function (url, filename) {
    console.log("11");
    if (url || url == '') {
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        url = projectName + '/jserver?server=filedownload';
        console.log("url");
    }
    url += ('&filename=' + filename);
    window.open(url)
}


/**
 * Excel 导入 by 李博
 * @param formId 表单ID
 * @param server 请求服务ID 默认为excelimport
 * @param method 请求的Sql ID
 * @param columns 需要解析的列数
 */
jserver.importExcel = function (formId, method, columns) {
    var form = document.getElementById(formId),
        formData = new FormData(form);

    //页面层
    var layerIndex = window.layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        skin: 'layui-layer-rim', //加上边框
        area: ['420px', '240px'], //宽高
        content: '<div id="progress" style="padding: 15px;">' +
        '<div id="title" style="text-align: center;font-size: large;font-weight: bold">正在导入</div>' +
        '<div id="content" style="text-indent: 2em;margin-top:30px ">正在导入中...</div>' +
        '</div>'
    });

    var requestId = jserver.randomString(32);

    var timer = setInterval(function () {
        var params = {
            server: "progress",
            progress: requestId
        };
        $.post(tempUrl + "jserver", params, function (data) {
            var json = JSON.parse(data);
            $('#progress #title').html(json.title);
            if (json.code === 0) {
                $('#progress #content').html(json.content);
            } else {
                $('#progress #content').html(json.content + " : " + json.current + "/" + json.count);
            }
        });
    }, 500);

    $.ajax({
        url: tempUrl + "jserver?server=excelimport&method=" + method + "&columns=" + columns + "&progress=" + requestId,
        type: "post",
        data: formData,
        processData: false,
        contentType: false,
        success: function (res) {
            data = JSON.parse(res);
            clearInterval(timer);
            window.layer.close(layerIndex);
            window.layer.alert(data.msg);
        },
        error: function (err) {
            clearInterval(timer);
            window.layer.alert("网络连接失败,稍后重试", err);
        }

    });

};


jserver.grxximportExcel = function (saveMethod, extraParams, callback) {
    var layerIndex = window.top.layer.open({
        title: '配置导入',
        shade: 0,
        btn: ['开始导入', '导入格式'], //按钮
        btn1: function (index) {
            var form = document.getElementById('importExcel');
            formData = new FormData();
            formData.append('upload', form.files[0]);
            if (extraParams) {
                for (index in extraParams) {
                    formData.append(index, extraParams[index]);
                }
            }
            $.ajax({
                //url: url + "jserver?server=excelimport&method=" + saveMethod,
                url: tempUrl + "jserver?server=excelimport&method=" + saveMethod,
                type: "post",
                data: formData,
                dataType: 'JSON',
                processData: false,
                contentType: false,
                success: function (data, index) {
                    if (data.code == 2) {//为参数错误的失败
                        window.top.layer.alert("导入失败！请检查列 '" + data.msg + "' 的数据是否合法！")
                    } else if (callback && $.isFunction(callback)) {
                        callback(data, layerIndex);
                    }
                },
                error: function (err) {
                    window.layer.alert("网络连接失败,稍后重试", err);
                }
            });

        },
        btn2: function (index) {
            var params = {};
            params['exporttype'] = 2007;
            Object.assign(params, extraParams);
            //此处存在URL、GET请求的255最大限度BUG
            var fileUrl = tempUrl + 'jserver?server=excelimportformat&method=' + saveMethod;
            // var fileUrl = tempUrl+"jserver?server=excelimport&method=" + saveMethod;
            for (var key in params) {
                fileUrl += '&' + key + '=' + params[key];
            }
            window.open(fileUrl);
        },
        content: "<div style='width:300px;'>" +
        "<div style='width:200px;margin:0 auto'>" +
        "<input type='file' id='importExcel'/>" +
        "</div>" +
        "</div>"
    })

}

/**
 * Excel 导出 By 李博
 * @param params 查询参数
 * @param method Sql ID
 */
jserver.exportExcel = function (params, method) {
    // var fileUrl = url + 'jserver?server=excelexport&method=' + method + '&progress=' + utils.guid();
    var fileUrl = tempUrl + 'jserver?server=excelexport&progress=&method=' + method;
    for (var key in params) {
        fileUrl += '&' + key + '=' + params[key];
    }
    window.open(fileUrl);

    // 执行以下代码会造成中文乱码
    // params.server = 'excelexport';
    // params.progress = jserver.randomString(32);
    // var p = {
    //     url: url + 'jserver?method=' + method,
    //     method: 'post',
    //     data: params
    // };
    //
    // var config = $.extend(true, {method: 'post'}, p);
    // var $iframe = $('<iframe id="down-file-iframe"/>');
    // var $form = $('<form target="down-file-iframe" method="' + config.method + '" accept-charset="UTF-8"/>');
    // $form.attr('action', config.url);
    // for (var key in config.data) {
    //     $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
    // }
    // $iframe.append($form);
    // $(document.body).append($iframe);
    // $form[0].submit();
    // $iframe.remove();
};

/**
 * 生成随机字符串
 * @param len
 * @returns {string}
 */
jserver.randomString = function (len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
};

/**
 * 获取已经选择的权限，默认使用逗号分隔
 * @param domId 树状列表ID
 * @param split 分隔符，若不传，则默认使用逗号分隔
 */
jserver.getChoicedPower = function (domId, split) {
    var nodes = $('#' + domId).tree('getChecked', ['checked', 'indeterminate']);
    var ids = '';
    for (var i = 0; i < nodes.length; i++) {
        if (ids != '') ids += (split ? split : ',');
        ids += nodes[i].id;
    }
    return ids;
};

/**
 * 当insdep的control加载完毕后的回调，已在源码中集成
 * @param queryparams 请求参数
 */
jserver.pageLoadFinish = function (queryparams) {
    //权限操作
    return;
    //隐藏所有权限按钮
    $('.easyui-linkbutton').each(function () {
        $(this).hide();
    });
    //获取按钮权限
    var params = {
        userId: JSON.parse(window.localStorage.getItem('loginuser'))['id'],
        modelId: queryparams
    };
    //根据按钮的id与名称来展示数据库存放的按钮
    jserver.loadData('jserver', 'base.获取模块功能信息', params, function (data) {
        //console.log(JSON.stringify(data));
        $('.easyui-linkbutton').each(function () {
            for (index in data.obj) {
                if (data.obj[index]['控件ID'] == $(this).attr('id')) {
                    $(this).show();
                }
            }
        })
    })
};

/**
 * 异步上传单个文件
 * @ url 需要请求的服务器url路径,若为""或null，则使用默认的url
 * @ id 文件Dom标签id
 * @ callback 成功后回调函数
 * @ priocess(e) 进度响应函数,e进度对象，e.loaded / e.total为百分比
 */
jserver.uploadSingle = function (url, id, callback, process) {
    if (url == null || url == '') {
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        url = projectName + '/jserver?server=fileupload';
    }

    //添加对普通file input的支持，添加对easyui-filebox的支持
    var filedom = $('#' + id).next().find('input[id^="filebox_file_id_"]:eq(0)');
    var file = filedom.get(0) ? filedom.get(0).files[0] : $('#' + id).get(0).files[0];
    if (file == undefined || file == null || file == "") {

        if (callback)
            callback(null);

    } else {
        var uploadFile = new FormData();
        uploadFile.append('file', file);

        var xhrOnProgress = function (fun) {
            xhrOnProgress.onprogress = fun; //绑定监听
            //使用闭包实现监听绑
            return function () {
                //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
                var xhr = $.ajaxSettings.xhr();
                //判断监听函数是否为函数
                if (typeof xhrOnProgress.onprogress !== 'function')
                    return xhr;
                //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
                if (xhrOnProgress.onprogress && xhr.upload) {
                    xhr.upload.onprogress = xhrOnProgress.onprogress;
                }
                return xhr;
            }
        };

        $.ajax({
            url: url,
            type: 'POST',
            data: uploadFile,
            cache: false,
            contentType: false, //不设置内容类型
            processData: false, //不处理数据
            xhr: xhrOnProgress(process),
            success: function (data) {
                if (callback)
                    callback(data instanceof Object ? data : JSON.parse(data));
            },
            error: function () {
                $.messager.alert('失败', '上传文件失败！', 'error');
            }
        });
    }

}

/**
 *  ajax请求数据
 * @type {{request: jserver.request.request, get: jserver.request.get, post: jserver.request.post}}
 */
jserver.request = {
    request: function (params) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: tempUrl + params.url,
                type: params.type,
                data: params.data,
                contentType: params.contentType,
                success: resolve,
                error: reject
            });
        });
    },
    get: function (params) {
        params = {
            ...params,
            type: 'get',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8'
        }
        return this.request(params);
    },
    post: function (params) {
        params = {
            ...params,
            type: 'post',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8'
        }
        return this.request(params);
    },
    postJson: function (params) {
        params = {
            ...params,
            type: 'post',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(params.data)
        }
        return this.request(params);
    }
}


/**
 * 按钮权限设置
 * @param domeId div的ID
 * @param roleId 登录者的角色ID
 * @param moduleId 模块ID
 */
jserver.getButtonRole = function (domeId, roleId, moduleId) {
    jserver.loadData('jserver', "模块功能.get模块功能By模块ID", {模块ID: moduleId, 角色ID: roleId}, function (data) {

        for (var i = 0; i < data.obj.length; i++) {
            if (data.obj[i].控件ID == 'btn_new') {
                $('#' + domeId).append('<a href="#" id="btn_new" class="easyui-linkbutton" iconCls="icon-add">添加</a>');
            } else if (data.obj[i].控件ID == 'btn_edit') {
                $('#' + domeId).append(' <a href="#" id="btn_edit" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>');
            } else if (data.obj[i].控件ID == 'btn_delete') {
                $('#' + domeId).append('  <a href="#" id="btn_delete" class="easyui-linkbutton" iconCls="icon-remove">删除</a>');
            } else if (data.obj[i].控件ID == 'btn_check') {
                $('#' + domeId).append(' <a href="#" id="btn_check" class="easyui-linkbutton" iconCls="icon-report_edit">签到</a>');
            } else if (data.obj[i].控件ID == 'btn_export') {
                $('#' + domeId).append(' <a href="#" id="btn_export" class="easyui-linkbutton" iconCls="icon-excel-export">导出</a>');
            }
        }
        $.parser.parse('#' + domeId);
    });
}

jserver.dateFormat = function (fmt, date) {
    let ret;
    let opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
        ;
    }
    ;
    return fmt;
}