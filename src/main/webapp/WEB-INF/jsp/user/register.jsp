<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>viadômetro</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="css/styles.css" rel="stylesheet">
	</head>
</head>
<body>
	<form class="col-md-12" action="register" method="POST">
   		<div class="form-group">
        <input name="user.name" type="text" class="form-control input-lg" placeholder="Email">
    </div>
    <div class="form-group">
        <input type="password" class="form-control input-lg" placeholder="Senha">
    </div>
    <div class="form-group">
        <input type="password" class="form-control input-lg" placeholder="Confirmar Senha">
    </div>
    <div class="form-group">
        <button class="btn btn-primary btn-lg btn-block">Cadastrar</button>
    </div>
</form>
	</body>
</html>