<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
        <meta name="author" content="Coderthemes">

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/theme/images/favicon_1.ico">

        <title>${msg} | KhmerAcademy WebService API</title>

        <!-- Base Css Files -->
        <link href="${pageContext.request.contextPath}/resources/theme/css/bootstrap.min.css" rel="stylesheet" />

        <!-- Font Icons -->
        <link href="${pageContext.request.contextPath}/resources/theme/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resources/theme/assets/ionicon/css/ionicons.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resources/theme/css/material-design-iconic-font.min.css" rel="stylesheet">

        <!-- animate css -->
        <link href="${pageContext.request.contextPath}/resources/theme/css/animate.css" rel="stylesheet" />

        <!-- Waves-effect -->
        <link href="${pageContext.request.contextPath}/resources/theme/css/waves-effect.css" rel="stylesheet">

        <!-- Custom Files -->
        <link href="${pageContext.request.contextPath}/resources/theme/css/helper.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/resources/theme/css/style.css" rel="stylesheet" type="text/css" />

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->

        <script src="${pageContext.request.contextPath}/resources/theme/js/modernizr.min.js"></script>
        
 


    </head>
    <body>


         <div class="wrapper-page">
            <div class="panel panel-color panel-primary panel-pages">
                <div class="panel-heading bg-img"> 
                    <div class="bg-overlay"></div>
                   <h3 class="text-center m-t-10 text-white"> Create a new Account </h3>
                </div> 


                <div class="panel-body">
                <form class="form-horizontal m-t-20" action="#" id="frmRegister">
                   
                    <div class="form-group">
                         <label>Email address</label>
                         <input id="email" name="email" require  type="email" class="form-control" >
                    </div>
                    
                    <div class="form-group">
                         <label>Username</label>
                         <input id="username" name="username" require  type="text" class="form-control" >
                    </div>

                     <div class="form-group">
                         <label>Password</label>
                         <input id="password" name="password" require type="password" class="form-control">
                    </div>
                    
                    <div class="form-group">
                         <label>Confirm Password</label>
                         <input id="confirmPassword" name="confirmPassword" require  type="password" class="form-control">
                    </div>
                    
                    <!-- <div class="form-group">
                    	<label>Group</label>
                    	<select class="form-control" id="group">
                                <option value="WEB">WEB</option>
                                <option value="ANDROID">Android</option>
                                <option value="IOS">IOS</option>
                                <option value="OTHER">Other</option>
                         </select>
                    </div> -->
                    
                    <div class="form-group text-center m-t-40">
                        <div class="col-xs-12">
                            <button class="btn btn-primary waves-effect waves-light btn-lg w-lg" type="submit">Register</button>
                        </div>
                    </div>

                    <div class="form-group m-t-30">
                        <div class="col-sm-12 text-center">
                            <a href="${pageContext.request.contextPath}/login">Already have account?</a>
                        </div>
                    </div>
                </form> 
                </div>                                 
                
            </div>
        </div>


        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.min.js"></script>
        
        <script type="text/javascript">
        $(function() {
        	
        	$("#frmRegister").submit(function(e){
       		
       		  e.preventDefault();
       		
       		if( $("#password").val() != $("#confirmPassword").val() ){ 
       			alert("Passwords do not match!");
       			return;
       		}
	     		json ={				
	     					"email"			: $("#email").val(),
							"username" 		: $("#username").val(),
							"password"		: $("#password").val(),
							"position"		: "OTHER"//$("#group").val()
	     		};
       		  $.ajax({
  	            url: "${pageContext.request.contextPath}/api/apiuser/",
  	            type: "POST",
  	         	datatype: 'JSON',
  	          	data: JSON.stringify(json), 
	  	        beforeSend: function(xhr) {
				   xhr.setRequestHeader("Authorization", "Basic ${kaapi}");
	               xhr.setRequestHeader("Accept", "application/json");
	               xhr.setRequestHeader("Content-Type", "application/json");
	            },
  	            success: function(data) {
  	            	send();
  	            	location.href = "${pageContext.request.contextPath}/login";
  	            	alert(data.MESSAGE);
  	            },
  	         	error: function(data){
  	         		
  	         		alert(data.responseJSON.Error.details);
  				}
  	        });
       			
       		});
	      
        
        });
        
        
	        var webSocket = 
				new WebSocket('ws://'+ document.location.host + '${pageContext.request.contextPath}/websockets/notify');
			
			webSocket.onerror = function(event) {
				onError(event)
			};
			
			webSocket.onopen = function(event) {
				onOpen(event)
			};
			
			webSocket.onmessage = function(event) {
				onMessage(event)
			};
			
			function onMessage(event) {
				console.log(event.data);
			}
			
			function onOpen(event) {
				console.log("OPEN...");
			}
			
			function onError(event) {
				alert(event.data);
			}
			
			function send() {
				var txt = "SENT";
				webSocket.send(txt);
				return false;
			}
        </script>
        
        
      
		<script>
            var resizefunc = [];
        </script>
    	<script src="${pageContext.request.contextPath}/resources/theme/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/js/waves.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/js/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.nicescroll.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.scrollTo.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/assets/jquery-detectmobile/detect.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/assets/fastclick/fastclick.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/assets/jquery-slimscroll/jquery.slimscroll.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/assets/jquery-blockui/jquery.blockUI.js"></script>


        <!-- CUSTOM JS -->
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.app.js"></script>
	
		
		
	</body>
</html>
        
        
    	