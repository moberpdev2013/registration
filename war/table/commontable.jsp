<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Complaint Box</title>
<link rel="stylesheet" type="text/css" href="css/flexigrid.css">
<script type="text/javascript" src="../jquery.tools.min.js"></script>
<script type="text/javascript" src="js/flexigrid.js"></script>
<style>
	.sm{
		width:100px;
		height:100px;
		padding:3px;
		border:1px solid #f0f0f0;
		border-radius:5px;
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
min-width: 400px;
max-width: 600px;
width:auto;
max-height:500px;
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
    background-image:url(../close.png);
    position:absolute; 
	right:0px;
	z-index:100;
	top:0px;
    cursor:pointer;
    height:35px;
    width:35px;
}
</style>
</head>

<body style="cursor: default; overflow: hidden; width: 94%;">

<div class="dialog">
		
		<a class="close"></a>
		
		<div style="width:100%;height:90%;overflow-x:hidden;overflow-y:auto;">
				
			<img style="width:100%;height:100%" src=""/>
		</div>
		
</div>

<table id="flex1" style="display:none"></table>

<script type="text/javascript">
//addData();
//function addData(){
var rows = new Array();
var colModel = new Array();
var search = new Array();
//$.getJSON("/index.jsp?action=json&name=Promotions&user=9881763210",function(jsol){
var fg=null;
$.getJSON("/index.jsp?action=json&name=Promotions&user=<%=request.getParameter("user")%>",function(jsol){
	
	var cc=0;
	var keyName="";
	$.each(jsol,function(k,jso){
	
		keyName = jso.keyName;
	
		var data = jQuery.parseJSON(jso.data);
		
		if(!data.length){
			
			data=data.data;
		}
		
		$.each(data,function(i,j){
	
			var cell=new Object();
			cell.cell=new Array();
			
			if(cc==0){
				
				for(var k in j){
					
					var cm=new Object();
					
					//display: 'ID', name : 'id', width : 40, sortable : true, align: 'center'
					cm.display=k;
					cm.name=k;
					cm.width=100;
					cm.sortable=true;
					cm.align="center";
					colModel.push(cm);
					
					var sm=new Object();
					sm.display=k;
					sm.name=k;
					search.push(sm);
				}
				cc++;
			}
			for(var k in j){
				
				if(k=="dataImage"){
				
					cell.cell[k]="<img onclick=\"$('.dialog img').attr('src','/serve?blob-key="+j[k]+"');$('.dialog').overlay().load();\" class=\"sm\" src=\"/serve?blob-key="+j[k]+"\"/>";
				
				}else{
					
					cell.cell[k]=j[k];
					
				}
			}
		
			rows.push(cell);
		
		});
		
	});
		
	var data = {
		    total: rows.length,    
		    page:1,
		    rows: rows
		}

	fg = $("#flex1").flexigrid({
			
			dataType: 'json',
			colModel : colModel,			
			usepager: true,
			title: keyName,
			useRp: false,
			rp: 15,
			showTableToggleBtn: true,
			width: 700,
			onSubmit: addFormData,
			height: 450,
			searchitems : search
	});



		$("#flex1").flexAddData(data);//.flexReload();
		
		

	});
	
	

//}

$(".dialog").overlay({
	
	mask: {  color: '#fff',   loadSpeed: 200,   opacity: 0.5  },
	

	closeOnClick: false,
	load: false

});

function loadImage(src){
	
	$(".dialog img").attr("src",src);
	$(".dialog").overlay().load();
}
//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit			
function addFormData(){
	
	return false;
}
	
$('#sform').submit(function (){
	$('#flex1').flexOptions({newp: 1}).flexReload();
	return false;
});
</script>
</body>
</html>