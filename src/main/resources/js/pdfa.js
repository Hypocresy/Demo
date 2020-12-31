$(function () {
    $("#project").datagrid({
        // url: "/getRoles",
        columns: [[{field: '_operation', title: '操作', width: 130, align: 'center'},
            {field: '项目名称', title: '项目名称', width: 160, align: 'center'},
            {field: '治疗区', title: '治疗区', width: 130, align: 'center'},
            {field: '评定师', title: '评定师', width: 140, align: 'center'},
            {field: '评定时间', title: '评定时间', width: 150, align: 'center'}]],
        fit: true,
        fitColumns: true,
        rownumbers: false,
        pagination: false,
        singleSelect: true,
        striped: false,
        height:200,
        width: 710,
        scrollbarSize:6,

    });


    $("#role_data1").datagrid({
        width: 350,
        height: 200,
        fitColumns: true,
        singleSelect: true,
        pagination: false,
        scrollbarSize:6,
        title:'可选',
        columns: [[
            {field:'_operation',title:'操作',width:100,align:'center'},
            {field:'名称',title:'名称',width:150,align:'center'},
            {field:'类别',title:'类别',width:140,align:'center'}
        ]],
        toolbar: 'tb',
        onClickRow: function (rowIndex, rowData) {/*点击一行时,回调*/

            /*判断是否已经存在该权限*/
            /*取出所有的已选权限*/
            var allRows = $("#role_data2").datagrid("getRows");
            /*取出每一个进行判断*/
            for (var i = 0; i < allRows.length; i++) {
                /*取出每一行*/
                var row = allRows[i];
                if (rowData.pid == row.pid) {/*已经存在该权限*/
                    /*让已经存在权限成为选中的状态*/
                    /*获取已经成为选中状态当前角标*/
                    var index = $("#role_data2").datagrid("getRowIndex", row);
                    /*让该行成为选中状态*/
                    $("#role_data2").datagrid("selectRow", index);
                    return;
                }
            }

            /*把当前选中的,添加到已选权限*/
            $("#role_data2").datagrid("appendRow", rowData);
        }
    });

    $("#role_data2").datagrid({
        width:350,
        height:200,
        singleSelect:true,
        fitColumns:true,
        scrollbarSize:6,
        title:'已选',
        columns:[[
            {field:'_operation',title:'操作',width:100,align:'center'},
            {field:'名称',title:'名称',width:150,align:'center'},
            {field:'类别',title:'类别',width:100,align:'center'}
        ]],
        onClickRow:function (rowIndex,rowData) {
            /*删除当中选中的一行*/
            $("#role_data2").datagrid("deleteRow",rowIndex);
        }
    });
    changeEUIBoxWidth('findpro',120,15);
// 动态改变 easy ui输入框的 width height
    function changeEUIBoxWidth(id, width,height){
        $('#'+id).parent().find($('span:eq(0)')).css('width',width+'px');
        $('#'+id).parent().children("span").eq(0).css('width',width+'px');
        $('#'+id).parent().find($('span:eq(0)')).css('height',height+'px');
        $('#'+id).parent().children("span").eq(0).css('height',height+'px');
        $.parser.parse('#'+id);
    }

    $('.btn-group button').click(function () {
       // $('.btn-group button').css('background-color','#27A9E3');
        $('.btn-group button').css('background-color','white');
       $(this).css('background-color','#27A9E3');
        $('.btn-group button').css('color','black');
        $(this).css('color','white');

    })

 //
    $("#project-right").datagrid({
        width:398,
        singleSelect:true,
        // fitColumns:true,
        scrollbarSize:0,
        data:[{},{}],
        autoRowHeight:true,
        columns:[[
            {field:'项目名称',title:'项目名称',width:110,align:'center'},
            {field:'关联量表',title:'关联量表',width:140,align:'center'},
            {field:'_operation',title:'操作',width:140,align:'center'}
        ]],
        onClickRow:function (rowIndex,rowData) {
            /*删除当中选中的一行*/
            $("#role_data2").datagrid("deleteRow",rowIndex);
        }
    });

        $('#方案类型').combobox({
            valueField:'id',
            textField:'text',
            data:[{id:'01',text:'初期方案'},{id:'02',text:'中期方案'},{id:'03',text:'末期方案'}],
            editable:false,
            panelHeight: 'auto'
        });

});
function  programClick(id){
    $('.d-right-center').hide();
    $('#'+id).show();
    if(id=='d-right-center1'){
        $('#project-right').datagrid('resize');
    }
    if(id=='d-right-center2'){
        $('#future_panel').accordion('resize');
    }
    if(id=='d-right-center3'){
        $('#his_panel').accordion('resize');
    }

}
