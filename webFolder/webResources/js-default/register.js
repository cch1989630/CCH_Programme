$( document ).on( "click", "#registerBtn", function() {
	var data = {};
	data = JSON.stringify(data);
	jqueryAjaxData("正在加油注册中", "LoginController", "register", data, successRegister);
	//cchAlert("正在加油注册中");
});

function successRegister() {
	
}