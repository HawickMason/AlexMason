<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>网上商城</title>
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/product.css" rel="stylesheet" type="text/css">
<script>
	function addToCart(){
		document.getElementById("cartForm").submit();
		alert("已添加至购物车");
	}
</script>
</head>
<body>

<div class="container header">
	<div class="span5">
		<div class="logo">
			<a href="http://www.nankai.edu.cn" target="_blank"> 
				<img src="${pageContext.request.contextPath}/image/r___________renleipic_01/logo.png" alt="NKU">
			</a>
		</div>
	</div>
	<div class="span9">
<div class="headerAd">
					<img src="${pageContext.request.contextPath}/image/header.jpg" alt="正品保障" title="正品保障" height="50" width="320">
</div>	</div>
	
<%@ include file="menu.jsp" %>

</div>

<div class="container index">		
	<div class="addedMessage">	
	<div class="success">
		<h3>商品已成功加入购物车</h3>
	</div>
		<span>
			<a href="${pageContext.request.contextPath}/cart_showCart.action" class="cartGo">去购物车结算~</a>
		</span>			
		<span class="nextGo">
			您还可以<a href="${pageContext.request.contextPath}/index.action" >继续购物~</a>
		</span>		
	</div>			
</div>

<div class="container footer">
	<div class="span24">
		<div class="footerAd">
					<img src="${pageContext.request.contextPath}/image/footer.jpg" alt="我们的优势" title="我们的优势" height="52" width="950">
		</div>
	</div>
	<div class="span24">
		<ul class="bottomNav">
					<li>
						<a href="#">关于我们</a>
						|
					</li>
					<li>
						<a href="#">联系我们</a>
						|
					</li>
					<li>
						<a href="#">诚聘英才</a>
						|
					</li>
					<li>
						<a href="#">法律声明</a>
						|
					</li>
					<li>
						<a>友情链接</a>
						|
					</li>
					<li>
						<a target="_blank">支付方式</a>
						|
					</li>
					<li>
						<a target="_blank">配送方式</a>
						|
					</li>
					<li>
						<a >SHOP++官网</a>
						|
					</li>
					<li>
						<a>SHOP++论坛</a>
						
					</li>
		</ul>
	</div>
	<div class="span24">
		<div class="copyright">Copyright © 2005-2015 网上商城 版权所有</div>
	</div>
</div>
</body>
</html>
