var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
};


var childMap=[];

function fillData(category){
	
	//var dm="[{\"id\":16001,\"category\":\"radmin\",\"organization\":\"Test\",\"immediateParent\":\"9881763210\",\"deviceKey\":\"\",\"webAddress\":\"\",\"name\":\"Test1\",\"service\":\"in\",\"userName\":\"9881763211\",\"isPublic\":\"no\",\"password\":\"1234\"},{\"id\":17001,\"category\":\"distributors\",\"organization\":\"Test\",\"immediateParent\":\"9881763211\",\"deviceKey\":\"\",\"webAddress\":\"\",\"name\":\"Test3\",\"service\":\"in\",\"userName\":\"9881763212\",\"isPublic\":\"no\",\"password\":\"1234\"}]"
	
	//var ap = jQuery.parseJSON(dm);
	
	$.getJSON("/index.jsp?action=getContacts",function(data){
	
		$.each(data,function(i,j){
		
			if(j.category==category){
			
				appendToParent(j);
				
			}else{
				
				var objA = childMap[j.immediateParent];
				
				if(objA==null){
					
					objA=new Array();
				}
				objA[objA.length] = j;
				
				childMap[j.immediateParent]=objA;
				
			}
		});
		
		$("#regionalAdm .body1").height($("#regionalAdm .body1").height()-4);
	});
	
			
	
}

function appendToParent(j){
	
	$("#regionalAdm .body1").append("<div onclick=\"openDistrib(this,'"+j.userName+"');\" class=\"element\" ><span><strong>Name</strong><label>"+j.name+"</label></span><span><strong>User</strong> <label>"+j.userName+"</label></span><span><strong>Service Status</strong> <label>"+j.service+"</label></span></div>");
}

function openDistrib(obj,uname){
	var md=childMap[uname];
	$(".element").removeClass("selected");
	$(obj).addClass("selected");
	if(md==null)
	return;
	$("#distributors").show("slow",function(){
			$("#distributors .element").remove();
			
					
			
			$.each(md,function(ii,jj){
				
				var sin = jj.mobilityStatus;
				
				var style="display:none";
				
				if(sin && sin=="on"){
					
					style="display:block";
				}
				
				$("#distributors .body1").append("<div class=\"element\" ><img style=\""+style+";float:right\" src=\"icon_user_online.png\" /><span><strong>Name</strong><label>"+jj.name+"</label></span><span><strong>User</strong> <label class=\"uname\">"+jj.userName+"</label></span><span><strong>Service Status</strong> <label>"+jj.service+"</label></span></div>");
			});
			if(isMobile.any()){
				
				$("#regionalAdm").css("display","none");
				$("#distributors").css("display","block");
			
			}
			$("#distributors .body1").height($("#distributors").height()-2*$("#distributors .head").height()-4);
			
			$("#distributors .element").click(function(){
			
				$("#currentUser").val($(".uname",this).html());
				
				$(".filters").overlay().load();
			});
			
	});
	
	
			
}

function sendData(){
	
	/*$.get("/sendAll?data="+$("#myInfo").val()+"&user="+$("#currentUser").val(),function(data){
		
		alert("Sent Data Successfully..........."+data);
	});*/
	sendToServer("/sendAll",$("#currentUser").val());
	
}

function sendToServer(url,touser){
	
	$.post(url, { data: $("#myInfo").val(), user: touser })
	.done(function(data) {
		alert("Sent Data Successfully..........."+data);
	});
}

function sendDataAll(){
	
	/*$.get("/sendAll?user=all&data="+$("#myInfo").val(),function(data){
		
		alert("Sent Data Successfully..........."+data);
	});*/
	sendToServer("/sendAll","all");
}
