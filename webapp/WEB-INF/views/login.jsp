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
            <div class="panel panel-color panel-success panel-pages">
                <div class="panel-heading bg-img"> 
                    <div class="bg-overlay"></div>
                    <h3 class="text-center m-t-10 text-white"> <strong>Khmer Academy WebService API</strong> </h3>
                </div> 

				<!-- ${pageContext.request.contextPath}/login -->
                <div class="panel-body">
                <form class="form-horizontal m-t-20" id="frmLogin" action="#" method="POST">
                    
                    <div class="form-group ">
                        <div class="col-xs-12">
                            <input class="form-control input-lg" type="text" required name="ka_username" placeholder="Username">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-12">
                            <input class="form-control input-lg" type="password" required name="ka_password" placeholder="Password">
                        </div>
                    </div>

                    <div class="form-group ">
                        <div class="col-xs-12">
                            <div class="checkbox checkbox-primary">
                                <input id="checkbox-signup" type="checkbox">
                                <label for="checkbox-signup">
                                    Remember me
                                </label>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div class="form-group text-center m-t-40">
                        <div class="col-xs-12">
                            <button class="btn btn-success btn-lg w-lg waves-effect waves-light" type="submit">Log In</button>
                        </div>
                    </div>

                    <div class="form-group m-t-30">
                        <div class="col-sm-7">
                            <a href="#"><i class="fa fa-lock m-r-5"></i> Forgot your password?</a>
                        </div>
                        <div class="col-sm-5 text-right">
                            <a href="${pageContext.request.contextPath}/register">Create an account</a>
                        </div>
                    </div>
                </form> 
                </div>                                 
                
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.min.js"></script>
        
        <script type="text/javascript">
        
        $(function() {
        	
        	$("#frmLogin").submit(function(e){
       		
       		  e.preventDefault();
       			
       		  $.ajax({
  	            url: "${pageContext.request.contextPath}/login",
  	            type: "POST",
  	            data: $("#frmLogin").serialize(),
//   	            beforeSend: function (xhr) {
//   	                xhr.setRequestHeader("X-Ajax-call", "true");
//   	            },
  	            success: function(data) {
  	            	if(data == "User account is locked"){
  	            		alert(data);
  	            	}else if(data == "User is disabled"){
  	            		alert(data);
  	            	}else if(data == "Bad credentials"){
  	            		alert(data);
  	            	}else{
  	            		alert("Logined success.");
  	            		location.href = "${pageContext.request.contextPath}/"+data;
  	            	}
  	            	
  	            },
  	         	error: function(data){
  	         		console.log(data);
  				}
  	        });
       			
       		});
	      
        
        });
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
        
        
    	