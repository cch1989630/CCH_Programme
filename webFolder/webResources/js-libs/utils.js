//打开loading组件
//text(string): 加载提示文字
//str(string): load的背景颜色样式(取值:a,b,c,d)
//flag(boolean): 背景是否透明(取值:true透明,false不透明)
function loadStart(text,str,flag){
    if(!text){
        text = "加载中...";
    }
    $(".ui-loader h1").html(text);
    var _width = window.innerWidth;
    var _height = window.innerHeight;
    var htmlstr = '<div style="width:'+_width+'px;height:'+_height+'px;position:fixed;top:0px;left:0px;z-index:99999" class="loader-bg"></div>';
    $("body").append(htmlstr);
    if(flag){
        $(".ui-loader").removeClass("ui-loader-verbose").addClass("ui-loader-default");
    }
    else{
        $(".ui-loader").removeClass("ui-loader-default").addClass("ui-loader-verbose");
    }
    var cla = "ui-body-"+str;
    $("html").addClass("ui-loading");
    var arr = $(".ui-loader").attr("class").split(" ");
    var reg = /ui-body-[a-f]/;
    for(var i in arr){
        if(reg.test(arr[i])){
            $(".ui-loader").removeClass(arr[i]);
        }
    }
    $(".ui-loader").addClass(cla);
}
//结束loading组件
function loadStop(){
    $("html").removeClass("ui-loading");
    $(".loader-bg").remove();
}

//自定义的一个alert弹出框
function cchAlert(alertMessage, callBack) {
    var popupDialogId = 'popupDialog';
    $('<div data-role="popup" id="' + popupDialogId + '" data-confirmed="no" data-transition="pop" data-overlay-theme="b" data-theme="b" data-dismissible="false" style="min-width:216px;max-width:500px;">'+
            '<div role="main" class="ui-content">'+
                '<h3 class="ui-title" style="color:#fff; text-align:center;margin-bottom:15px">' +alertMessage+'</h3>'+
                '<a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b optionConfirm" data-rel="back" style="background: #1784fd;width: 60%;border-radius: 5px;height: 30px;line-height: 30px;padding: 0;font-size: .9em;margin: 0 0 0 20%;font-weight: 100;">确定</a>'+
                //'<a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b optionCancel" data-rel="back" data-transition="flow" style="background: #DBDBDB;width: 33%;border-radius: 5px;height: 30px;line-height: 30px;padding: 0;font-size: .9em;margin: 0 0 0 5%;font-weight: 100;color: #333;text-shadow: none;">取消</a>'+
            '</div>'+
      '</div>')
    .appendTo($.mobile.pageContainer);
    var popupDialogObj = $('#' + popupDialogId);
    popupDialogObj.trigger('create');
    popupDialogObj.popup({
        afterclose: function (event, ui) {
            popupDialogObj.find(".optionConfirm").first().off('click');
            var isConfirmed = popupDialogObj.attr('data-confirmed') === 'yes' ? true : false;
            $(event.target).remove();
            if (isConfirmed) {
               //这里执行确认需要执行的代码
               if (typeof callBack === "function") {
               		callBack();
               }
            }
        }
    });
    popupDialogObj.popup('open');
    
    popupDialogObj.find(".optionConfirm").first().on('click', function () {
        popupDialogObj.attr('data-confirmed', 'yes');
	});

}

/**
 * 通过封装调用的ajax类，来统一管理ajax回调发生的异常等信息
 * 该方法主要用在无跳转的ajax调用
 * @param beanName		方法所在spring Bean名称
 * @param functionName		方法名
 * @param data		json数据
 * @param successfn		成功返回调用方法
 * @return
 */
function jqueryAjaxData(requestStr, beanName, functionName, data, successfn) {
	data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
	
	loadStart(requestStr, "b", false);
	$.ajax({
        type: "post",
        data: {
			data:data,
			beanName:beanName,
			functionName:functionName
		},
        url: "ajaxNotUrl.do",
        dataType: "json",
        success: function(d){
            successfn(d);
        },
		error:function(XMLHttpRequest, textStatus, errorThrown) {
        	switch (XMLHttpRequest.status){  
	            case(500):  
	            	cchAlert(XMLHttpRequest.responseText);  
	                break;  
	            case(408):  
	            	cchAlert("请求超时");  
	                break;  
	            default:  
	            	cchAlert("亲！系统出错了！工程师马上到！");  
	        }  
		},
		complete: function(){
			loadStop();
		}
    });
}