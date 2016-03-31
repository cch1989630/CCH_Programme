<!DOCTYPE html>
<html>
    <head>
        <title>注册页面</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="webResources/css-libs/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="webResources/css-libs/public.css" />
        <link rel="stylesheet" href="webResources/css-default/register.css" />
        <script type="text/javascript" src="webResources/js-libs/jquery.min.js"></script>
        <script type="text/javascript" src="webResources/js-libs/jquery.mobile-1.4.5.min.js"></script>
        <script type="text/javascript" src="webResources/js-libs/utils.js"></script>
        <script type="text/javascript" src="webResources/js-default/register.js"></script>
    </head>
    <body>
        <div data-role="page" id="home">
            <div data-role="header" class="header">
                <h1>感谢您的注册</h1>
            </div>
            <div class="ui-field-contain">
                <label for="phone">手机号码:</label>
            	<input id="phone" name="phone" value="" type="text" placeholder="11位手机号码">
            </div>
            <div class="ui-field-contain">
                <label for="confirmPassword">生日:</label>
                <input type="date">
            </div>
            <div class="ui-field-contain">
                <label for="password">密码:</label>
                <input id="password" name="password" value="" type="password" placeholder="密码">
            </div>
            <div class="ui-field-contain">
                <label for="confirmPassword">确认密码:</label>
                <input id="confirmPassword" name="confirmPassword" value="" type="password" placeholder="确认密码">
            </div>
            <button id="registerBtn" class="ui-btn ui-corner-all" data-inline="true" data-msgtext="" data-textvisible="true" data-textonly="false">注册</button>
            <div data-role="footer">
                <!--
                <h4>曹春华和李亚楠感谢您的到来与祝福</h4>
                -->
            </div>
        </div>
    </body>
</html>