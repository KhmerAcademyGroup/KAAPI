<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
        <meta name="author" content="Coderthemes">

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/theme/images/favicon_1.ico">

        <title>ADMIN | KAAPI</title>

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



    <body class="fixed-left">
        
        <!-- Begin page -->
        <div id="wrapper">
        
            <!-- Top Bar Start -->
            <div class="topbar">
                <!-- LOGO -->
                <div class="topbar-left">
                    <div class="text-center">
                        <a href="#" class="logo"><i class="md md-terrain"></i> <span>KAAPI </span></a>
                    </div>
                </div>
                <!-- Button mobile view to collapse sidebar menu -->
                <div class="navbar navbar-default" role="navigation">
                    <div class="container">
                        <div class="">
                            <div class="pull-left">
                                <button class="button-menu-mobile open-left">
                                    <i class="fa fa-bars"></i>
                                </button>
                                <span class="clearfix"></span>
                            </div>
                            <form class="navbar-form pull-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control search-bar" placeholder="Type here for search...">
                                </div>
                                <button type="submit" class="btn btn-search"><i class="fa fa-search"></i></button>
                            </form>

                            <ul class="nav navbar-nav navbar-right pull-right">
                                <li class="dropdown hidden-xs">
                                    <a href="#" data-target="#" class="dropdown-toggle waves-effect waves-light" data-toggle="dropdown" aria-expanded="true">
                                        <i class="md md-notifications"></i> <span class="badge badge-xs badge-danger" id="totalRequestedUser"></span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-lg">
                                        <li class="text-center notifi-title">Notification</li>
                                        <li class="list-group" id="listRequestedUser" style="max-height: 400px; overflow: auto">
                                           
                                           <!-- list Requested User-->
                                          
                                           
                                            <a href="javascript:void(0);" class="list-group-item">
                                              <small>See all notifications</small>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="hidden-xs">
                                    <a href="#" id="btn-fullscreen" class="waves-effect waves-light"><i class="md md-crop-free"></i></a>
                                </li>
                                <li class="hidden-xs">
                                    <a href="#" class="right-bar-toggle waves-effect waves-light"><i class="md md-chat"></i></a>
                                </li>
                                <li class="dropdown">
                                    <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img src="${pageContext.request.contextPath}/resources/theme/images/avatar-1.jpg" alt="user-img" class="img-circle"> </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="javascript:void(0)"><i class="md md-face-unlock"></i> Profile</a></li>
                                        <li><a href="javascript:void(0)"><i class="md md-settings"></i> Settings</a></li>
                                        <li><a href="javascript:void(0)"><i class="md md-lock"></i> Lock screen</a></li>
                                        <li><a href="${PageContext.request.contextPath}"><i class="md md-settings-power"></i> Logout</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <!--/.nav-collapse -->
                    </div>
                </div>
            </div>
            <!-- Top Bar End -->


            <!-- ========== Left Sidebar Start ========== -->

            <div class="left side-menu">
                <div class="sidebar-inner slimscrollleft">
                    <div class="user-details">
                        <div class="pull-left">
                            <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-1.jpg" alt="" class="thumb-md img-circle">
                        </div>
                        <div class="user-info">
                            <div class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">John Doe <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="javascript:void(0)"><i class="md md-face-unlock"></i> Profile<div class="ripple-wrapper"></div></a></li>
                                    <li><a href="javascript:void(0)"><i class="md md-settings"></i> Settings</a></li>
                                    <li><a href="javascript:void(0)"><i class="md md-lock"></i> Lock screen</a></li>
                                    <li><a href="javascript:void(0)"><i class="md md-settings-power"></i> Logout</a></li>
                                </ul>
                            </div>
                            
                            <p class="text-muted m-0">Administrator</p>
                        </div>
                    </div>
                    <!--- Divider -->
                    <div id="sidebar-menu">
                        <ul>
                            <li>
                                <a href="#" class="waves-effect"><i class="md md-account-child"></i><span>User</span></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/ka_api" class="waves-effect"><i class="md md-account-child"></i><span>WebService API</span></a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
            <!-- Left Sidebar End --> 



            <!-- ============================================================== -->
            <!-- Start right Content here -->
            <!-- ============================================================== -->                      
            <div class="content-page">
                <!-- Start content -->
                <div class="content">
                    <div class="container">

                       
                        <div class="row">
                            
                            <div class="col-md-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">KA API Users</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="table-responsive">
                                                    <table class="table">

                                                    <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>Username</th>
                                                            <th>Email</th>
                                                            <th>Position</th>
                                                            <th>Roles</th>
                                                            <th>Registered Date</th>
                                                            <th>Group</th>
                                                            <th>Locked</th>
                                                            <th>Disabled</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="CONTENTS">
                                                       
                                                       
				
				
                                                    </tbody>
                                                </table>
                                                
                                                <div id="PAGINATION" class="pull-right">
												
												</div>
                                            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- End Row -->


                    </div> <!-- container -->
                               
                </div> <!-- content -->

                <footer class="footer text-right">
                    2015 © Moltran.
                </footer>

            </div>
            <!-- ============================================================== -->
            <!-- End Right content here -->
            <!-- ============================================================== -->


            <!-- Right Sidebar -->
            <div class="side-bar right-bar nicescroll">
                <h4 class="text-center">Chat</h4>
                <div class="contact-list nicescroll">
                    <ul class="list-group contacts-list">
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-1.jpg" alt="">
                                </div>
                                <span class="name">Chadengle</span>
                                <i class="fa fa-circle online"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-2.jpg" alt="">
                                </div>
                                <span class="name">Tomaslau</span>
                                <i class="fa fa-circle online"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-3.jpg" alt="">
                                </div>
                                <span class="name">Stillnotdavid</span>
                                <i class="fa fa-circle online"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-4.jpg" alt="">
                                </div>
                                <span class="name">Kurafire</span>
                                <i class="fa fa-circle online"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-5.jpg" alt="">
                                </div>
                                <span class="name">Shahedk</span>
                                <i class="fa fa-circle away"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-6.jpg" alt="">
                                </div>
                                <span class="name">Adhamdannaway</span>
                                <i class="fa fa-circle away"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-7.jpg" alt="">
                                </div>
                                <span class="name">Ok</span>
                                <i class="fa fa-circle away"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-8.jpg" alt="">
                                </div>
                                <span class="name">Arashasghari</span>
                                <i class="fa fa-circle offline"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-9.jpg" alt="">
                                </div>
                                <span class="name">Joshaustin</span>
                                <i class="fa fa-circle offline"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                        <li class="list-group-item">
                            <a href="#">
                                <div class="avatar">
                                    <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-10.jpg" alt="">
                                </div>
                                <span class="name">Sortino</span>
                                <i class="fa fa-circle offline"></i>
                            </a>
                            <span class="clearfix"></span>
                        </li>
                    </ul>  
                </div>
            </div>
            <!-- /Right-bar -->


        </div>
        <!-- END wrapper -->
    
        <script>
            var resizefunc = [];
        </script>

        <!-- jQuery  -->
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
        
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.tmpl.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.bpopup.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.bootpag.min.js"></script>


        <!-- CUSTOM JS -->
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.app.js"></script>
	
	
	 <script id="CONTENT_TEMPLATE" type="text/x-jquery-tmpl">
	    	<tr>
				<td>{{= id}}</td>
				<td>{{= username}}</td>
				<td>{{= email}}</td>
				<td>{{= position}}</td>
				<td>{{if accountNonLocked == true}} <i class="ion-android-close" style="color: red;"></i> {{else}} <i class="ion-android-close" style="color: green;"></i> {{/if}}</td>
				<td>{{= roles}}</td>
				<td>{{= createdDate}}</td>
				<td>{{= position}}</td>
				<td>{{if enabled == true}} <i class="ion-android-close" style="color: red;"></i> {{else}} <i class="ion-android-close" style="color: green;"></i> {{/if}}</td>
			</tr>
        </script>
        
        
		<script type="text/javascript">
		
		var users = {};
		var check = true;
		
		$(document).ready(function(){
		
			
			
			users.findAllUserByUsername = function(currentPage){
				$.ajax({ 
				    url: "${pageContext.request.contextPath}/api/apiuser", 
				    type: 'GET', 
				    data: {
				    		"currentPage" : currentPage,
				    		"perPage"     : 1
				    },
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                    xhr.setRequestHeader("Authorization" , "Basic ${kaapi}");
	                },
				    success: function(data) { 
						console.log(data);
						if(data.RESP_DATA.length>0){
							$("tbody#CONTENTS").html('');
							$("#CONTENT_TEMPLATE").tmpl(data.RESP_DATA).appendTo("tbody#CONTENTS");
						}else{
							$("tbody#CONTENTS").html('<tr>NO CONTENTS</tr>');
						}
				    	if(check){
				    		users.setPagination(data.PAGINATION.totalPages,1);
				    		check=false;
				    	}
				    },
				    error:function(data,status,er) { 
				        console.log("error: "+data+" status: "+status+" er:"+er);
				    }
				});
			};
			
			users.setPagination = function(totalPage, currentPage){
   		    	$('#PAGINATION').bootpag({
   			        total: totalPage,
   			        page: currentPage,
   			        maxVisible: 10,
   			        leaps: true,
   			        firstLastUse: true,
   			        first: 'First',
   			        last: 'Last',
   			        wrapClass: 'pagination',
   			        activeClass: 'active',
   			        disabledClass: 'disabled',
   			        nextClass: 'next',
   			        prevClass: 'prev',
   			        lastClass: 'last',
   			        firstClass: 'first'
   			    }).on("page", function(event, currentPage){
   			    	check = false;
   			    	users.findAllUserByUsername(currentPage);
   			    }); 
    		};
    		
    		users.countRequestedUser = function(){
				$.ajax({ 
				    url: "${pageContext.request.contextPath}/api/apiuser/count_req", 
				    type: 'GET', 
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                    xhr.setRequestHeader("Authorization" , "Basic ${kaapi}");
	                },
				    success: function(data) { 
						console.log(data);
						if(data.RESP_DATA != null){
							$("#totalRequestedUser").html(data.RESP_DATA.length);
							var reqUser = "";
							 for (i = 0; i < data.RESP_DATA.length; i++) {
								 reqUser    += 	 '<a href="javascript:void(0);" class="list-group-item">'+
									            	'<div class="media">'+
									                  '<div class="pull-left">'+
									                     '<em class="fa fa-user-plus fa-2x text-info"></em>'+
									                  '</div>'+
									                  '<div class="media-body clearfix">'+
	                                                    '<div class="media-heading">'+data.RESP_DATA[i].username+' registered.</div>'+
	                                                    '<p class="m-0">'+
	                                                       '<small>'+data.RESP_DATA[i].createdDate+'</small>'+
	                                                    '</p>'+
	                                                 '</div>'+
									               '</div>'+
									             '</a>';
		                     }
		                     $("#listRequestedUser").html(reqUser);
						}
				    },
				    error:function(data,status,er) { 
				        console.log("error: "+data+" status: "+status+" er:"+er);
				    }
				});
			};
			
			
			users.findAllUserByUsername(1);
			users.countRequestedUser();

		});
		
		
		
			/*************************************************************/
			
			
			var webSocket = 
				new WebSocket('ws://'+ document.location.host + '${pageContext.request.contextPath}/notify');
			
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
				users.countRequestedUser();
				console.log("GET : " + event.data);
			}
			
			function onOpen(event) {
				console.log("Open...");
			}
			
			function onError(event) {
				alert(event.data);
			}
			
	</script>
	</body>
</html>