<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>

<!DOCTYPE html>
<html>
<head>

<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0;"/>
<link rel="stylesheet" type="text/css" href="default.css" media="screen" />

<script class="jsbin" src="jquery.tools.min.js"></script>
<script class="jsbin" src="util.js"></script>
  


<style>
body{
	margin:4px;
	background:#aaa;
	
	border-radius:5px;
	height:100%;
	
}
html{
	height:100%;
}

.body{
	
	overflow:auto;
	
}
.main{
	margin:auto auto;
	padding:1px;
	border:1px solid #aaa;
	height:98%;
	background:#fff;
	border-radius:10px;
	
	
}
.tail,.head{
margin:2px;
height:7%;	
min-height: 40px;


background-color: #fafafa;
background-image: -moz-linear-gradient(top,#fff,#f2f2f2);
background-image: -webkit-gradient(linear,0 0,0 100%,from(#fff),to(#f2f2f2));
background-image: -webkit-linear-gradient(top,#fff,#f2f2f2);
background-image: -o-linear-gradient(top,#fff,#f2f2f2);
background-image: linear-gradient(to bottom,#fff,#f2f2f2);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',endColorstr='#fff2f2f2',GradientType=0);
border: 1px solid #d4d4d4;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
border-radius: 4px;
-webkit-box-shadow: 0 1px 4px rgba(0,0,0,0.065);
-moz-box-shadow: 0 1px 4px rgba(0,0,0,0.065);
box-shadow: 0 1px 4px rgba(0,0,0,0.065);
	
}
.dialog input,.dialog textarea {
border: 1px solid #ccc;
padding: 9px 8px 7px;
-webkit-box-sizing: border-box;
-moz-box-sizing: border-box;
-ms-box-sizing: border-box;
box-sizing: border-box;
-webkit-border-radius: 3px;
border-radius: 3px;
-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
-moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
}
.tail{
	border-radius:0 0 10px 10px;
}
.head{
	border-radius:10px 10px 0 0;
	-webkit-box-shadow: inset 0 -1px 0 rgba(0,0,0,0.1),0 1px 10px rgba(0,0,0,0.1);
-moz-box-shadow: inset 0 -1px 0 rgba(0,0,0,0.1),0 1px 10px rgba(0,0,0,0.1);
box-shadow: inset 0 -1px 0 rgba(0,0,0,0.1),0 1px 10px rgba(0,0,0,0.1);
}


.body{
	width:100%;
	height:83.5%;
	background:#fff;
	
}
.tail h2{

	font-size:12px;
	text-align:center;
	margin:0px;
}

.tail a{
	margin-top:0px;
}
input{
	
	
	margin-top:6px;
	
	width:300px;
}

.lid{
	
	
}
li{
	background:#fff;
	background-color:#fff!important;
	padding-top:2px!important;
	
}
li a{
	height:25px!important;
	width:200px;
	border:1px solid #f0f0f0;
	border-radius:5px;
	padding-top:10px!important;
}
li h2{
	
	margin:0px;
	padding:0px;
}
.status{
	z-index:1000;
	display:none;
	position:absolute;
	margin-top:-40px;
	
	
}
.status h2{
	font-size:12px;
	text-align:center;
	margin:0px;
}
#loading{
	
	display:none;
	position:absolute;
}
.btn {
display: inline-block;
padding: 4px 14px;
margin: auto;
font-size: 14px;
line-height: 20px;
text-align: center;
vertical-align: middle;
cursor: pointer;
color: #333;
text-shadow: 0 1px 1px rgba(255,255,255,0.75);
background-color: #f5f5f5;
background-image: -moz-linear-gradient(top,#fff,#e6e6e6);
background-image: -webkit-gradient(linear,0 0,0 100%,from(#fff),to(#e6e6e6));
background-image: -webkit-linear-gradient(top,#fff,#e6e6e6);
background-image: -o-linear-gradient(top,#fff,#e6e6e6);
background-image: linear-gradient(to bottom,#fff,#e6e6e6);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',endColorstr='#ffe6e6e6',GradientType=0);
border-color: #e6e6e6 #e6e6e6 #bfbfbf;
border-color: rgba(0,0,0,0.1) rgba(0,0,0,0.1) rgba(0,0,0,0.25);
filter: progid:DXImageTransform.Microsoft.gradient(enabled = false);
border: 1px solid #bbb;
border-bottom-color: #a2a2a2;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
border-radius: 4px;
-webkit-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
-moz-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
}
a {
color: #08c;
text-decoration: none;
}

#dataDialog {
	margin:1%;
	width:98%;
	max-width:300px;
	height:400px;
	background:#777;
	padding:1px;
	border:2px solid #ccc;
	display:none;
	position:absolute;
	border-radius:5px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
-moz-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
}

#regionalAdm{
	width:20%;
	min-width:250px;
	border:1px solid #ccc;
	height:98%;
	margin:2px;
	float:left;
	border-radius:5px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
-moz-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
}
#distributors{
width:80%;
max-width:400px;
float:left;
	border:1px solid #ccc;
	height:98%;
	margin:2px;
	border-radius:5px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
-moz-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
}

.element{
	border:1px solid #aaa;height:60px;margin:5px;background:#fff;border-radius:0px;border:none;border-bottom:1px solid #ccc;
	-webkit-box-shadow: 0px 0px 12px rgba(0,0,0,.6);
box-shadow: 0px 0px 12px rgba(0,0,0,.6);
-webkit-background-clip: padding-box;
-moz-background-clip: padding;
background-clip: padding-box;

}

.selected{
background: #0571A6 !important;
background: -moz-linear-gradient(top, #73aec9 0, #73aec9 1px, #338ab0 1px, #0571a6 100%) !important;
background: -o-linear-gradient(top, #73aec9 0, #73aec9 1px, #338ab0 1px, #0571a6 100%) !important;
background: -webkit-gradient(linear, left top, left bottom, color-stop(0, #73aec9), color-stop(5%, #73aec9), color-stop(5%, #338ab0), color-stop(100%, #0571a6)) !important;
background: linear-gradient(top, #73aec9 0%,#73aec9 1px,#338ab0 1px,#0571a6 100%) !important;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#338AB0', endColorstr='#0571A6',GradientType=0 ) !important;
border-color: #045a8b !important;
color: #fff !important;


}

.dialog {

border: 1px solid #a4baca;
background: #fdfdfd;
-webkit-border-radius: 4px;
border-radius: 4px;
-webkit-box-shadow: 0 1px 5px rgba(0,0,0,0.1);
-moz-box-shadow: 0 1px 5px rgba(0,0,0,0.1);
box-shadow: 0 1px 5px rgba(0,0,0,0.1);
-webkit-background-clip: padding-box;
background-clip: padding-box;
width: 400px;
display:none;
}

.dialog .header {
position: relative;
background: #517fa4;
height: 44px;
border: 1px solid #1c5380;
border-width: 1px 1px 0;
-webkit-box-shadow: 0 -1px 0 #06365f inset, 0 1px 0 rgba(255,255,255,0.2) inset;
-moz-box-shadow: 0 -1px 0 #06365f inset, 0 1px 0 rgba(255,255,255,0.2) inset;
box-shadow: 0 -1px 0 #06365f inset, 0 1px 0 rgba(255,255,255,0.2) inset;
-webkit-border-radius: 4px 4px 0 0;
border-radius: 4px 4px 0 0;
background-color: #517fa4;
background: #517fa4;

background-position: 50% 50%;
margin: -1px -1px 1px;
}
.dialog .logo {


color: #fff;
font-size: 18px;
text-align: center;

font-family: "freight-sans-pro","proxima-nova","Helvetica Neue",Arial,Helvetica,sans-serif;
}

.dialog .close {
    background-image:url(close.png);
    position:absolute; 
	right:0px;
	z-index:100;
	top:0px;
    cursor:pointer;
    height:35px;
    width:35px;
}
.filters{
	min-height:300px;
}
 .body1 span{
	
	display: inherit;
line-height: 18px;
padding-left: 10px;
}
 .body1 span strong{
	
	min-width:120px;
	display: inline-block;
}
 .body1 span label{
	
	font-size:10pt;
	font-weight:bold;
}
</style>
</head>
<body>
	
	<input type="hidden" id="host" value="148.147.175.92"/>
	<div class="main">
		<div class="tail head  toolbar">  
                   
				<h2 style="margin:0px;height:auto;border:none">Gateway</h2>
				
		</div>
		<div class="head toolbar" style="border-radius:0px">
					
					<button onclick="$('.upload').overlay().load()" id="upload" style="float:left;width:200px;position:relative;margin:15px;" class="button" href="#run" id="run">
                        
                        Upload Data(Excel Sheet)
                    </button>
					
					<button  onclick="window.open('table/mytable.html','_self');" id="complaints" style="float:left;width:200px;position:relative;margin:15px;" class="mybutton button" href="#run" >
                        
                        Complaint Box
                    </button>
					
					
					
        
		</div>
		
		
		
			<div id="dataDialog">
			
			</div>
		<div class="body" id="dbody" style="overflow-x:hidden;overflow-y:auto">
		
			<div id="regionalAdm" class="demo">
				<div class="head" style="margin:0px;border-radius:3px">
					<p style="float:left;padding:10px;font-size:12pt">Regional Admin</p>
					<input type="text"  style="float:right;border:1px solid #e0e0e0;height:60%;width:60%;border-radius:3px"></input>
				</div>
			<div class="body1" style="height:82%">
					
				</div>
			<div class="tail" style="margin:0px;border-radius:3px">
					
				</div>
			</div>
			
			<div id="distributors" class="demo">
				<div class="head" style="margin:0px;border-radius:3px">
					<p style="float:left;padding:10px;font-size:12pt">Distributors</p>
					<input type="text"  style="float:right;border:1px solid #e0e0e0;height:60%;width:60%;border-radius:3px"></input>
				</div>
				<div class="body1" style="height:82%">
					
				</div>
				<div class="tail" style="margin:0px;border-radius:3px">
					
				</div>
			</div>
			
			
			
		</div>
		
		
		
		
	<div class="filters dialog">
		
		<a class="close"></a>
		<div class="header">
			
			<button  onclick="loadUpdates();" id="updates" style="float:left;width:200px;position:relative;margin:15px;" class="mybutton button" href="#run">
                        
                        My Updates
            </button>
			<h1 class="logo"></h1>
		</div>
		<div style="padding:10px;width:95%;height:90%;overflow-x:hidden;overflow-y:auto;">
				
				 <div class="filters_data" style="width:100%;height:100%">
				 	<%
  						  BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
					%>
					 <form action="<%= blobstoreService.createUploadUrl("/sendAll") %>" method="post" enctype="multipart/form-data">
					 
					 	<input type="hidden" value="" id="currentUser" name="user"/>
			
			 	<h2 style="border:none;padding-top:10px;float:left">Information Category</h2>
			 	 
				<select name="category" id="category" data-mini="true" style="margin:5px;padding:5px;border-radius:4px;">
					<option value="KO News">KO News</option>
					<option value="From CEO’s desk">From CEO's desk</option>
					<option value="Consumer Promotions">Consumer Promotions</option>
					<option value="Schemes and Discounts">Schemes and Discounts</option>					
					<option value="Videos">Videos</option>
					<option value="Photographs">Photographs</option>
					<option value="Best Practices">Best Practices</option>
					<option value="New Launches">New Launches</option>
					<option value="Focus Packs/Brand">Focus Packs/Brand</option>
				</select>
		            	
		                <input type="file"  id="fileField" name="infofile" />	                             
	                	
		                <h2 style="border:none;padding-top:10px;">Text Information:</h2>	              
						
						<textarea style="width:90%;height:100px;" id="myInfo" name="data">Hi How are you
						
						</textarea>
						
		                <input type="submit" value="Done" id="done" style="display:none"/>	                
	                	
	               		<button type="submit" class="button" >Send</button>
	               		
		            </form>
		            
				</div>
				
				
				
				
		
		</div>
		
	</div>
	
	<div class="dialog upload">
		<input type="hidden" value="" id="currentUser"/>
		<a class="close"></a>
		
		<iframe src="upload.html" style="border:none;width:100%;height:100%;">
				
		
		</iframe>
		
	</div>	
	
	<div class="dialog updates">
		<input type="hidden" value="" id="currentUser"/>
		<a class="close"></a>
		
		<iframe src="" style="border:none;width:100%;height:100%;">
				
		
		</iframe>
		
	</div>	
			
	</div>
	
	
	
<script>

	$(".dialog").overlay({
				
				mask: {  color: '#fff',   loadSpeed: 200,   opacity: 0.5  },
				
				
				closeOnClick: false,
				load: false

			});
	
	$(".filters").overlay({
		
		mask: {  color: '#fff',   loadSpeed: 200,   opacity: 0.5  },
		
		onBeforeLoad :function(){
			
		},
		closeOnClick: false,
		load: false

	});
	
	$(".updates").overlay({
		
		mask: {  color: '#fff',   loadSpeed: 200,   opacity: 0.5  },
		

		closeOnClick: false,
		onBeforeLoad: function(){
			
			$(".updates iframe").attr("src","table/commontable.jsp?user="+$("#currentUser").val());		
		},
		load: false

	});
	
	
	//var isMobile=true;
	$(document).ready(function(){
		var mh = $(window).height();
		

		var th = $(".toolbar").height();
		
		// lazyLoad();
		$("#help").css("margin-left","34px");
		$("#help").width($("#command").width());
		
		$("#loading").css("margin-left",($("#dbody").width()-36)/2);
		$("#loading").css("margin-top",-($(".main").height())/2);
		var width = $(window).width();
		$(".dialog").css("max-width",($(window).width()-20)+"px");
		$(".upload").height($(window).height()-100);
		$(".upload").css("max-height",($(window).height()-100)+"px");
		if(isMobile.any()){
			
			$("#regionalAdm").css("width",(width-18));
			$("#distributors").css("width",(width-18));
			$("#distributors").css("max-width",(width-18));
			$("#distributors").css("display","none");
			
			$("#dbody").height($(window).height()-2*$(".toolbar").height()-20);
			$("#regionalAdm").height($("#dbody").height()-8);
			$("#distributors").height($("#dbody").height()-8);	
			
			$("#regionalAdm .body1").height($("#regionalAdm").height()-2*$("#regionalAdm .head").height());		
					
		}else{
			$("#regionalAdm").css("width",$("#distributors").width());
			$("#dbody").height($(window).height()-2*$(".toolbar").height()-28);
			$("#distributors .body1").height($("#distributors").height()-2*$("#distributors .head").height());
			$("#regionalAdm .body1").height($("#regionalAdm").height()-2*$("#regionalAdm .head").height());		
		
		}
		
		
		
		fillData('radmin');
	});
	
	var isrunning=false;
	var sc =null;

	var distrib=[];
	
	
	function openDialog(){
		
		$("#dataDialog" ).show("slow");
	}
		
	function loadUpdates(){
		
		
		$(".updates iframe").attr("src","table/commontable.jsp?user="+$("#currentUser").val());
		$('.updates').overlay().load();
	}
	
	
</script>
</body>
</html>