<!DOCTYPE html>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.kaapi.app.entities.APIUser"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix='sec' uri="http://www.springframework.org/security/tags" %>
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
                                    <li><a href="${pageContext.request.contextPath}/logout"><i class="md md-settings-power"></i> Logout</a></li>
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
                                <a href="${pageContext.request.contextPath}/wsapi" class="waves-effect"><i class="md md-account-child"></i><span>WS API</span></a>
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
                                        <%
//                                         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                                 		APIUser user = (APIUser)authentication.getPrincipal();
//                                 		out.print("____________adminID " +user.getId());
//                                 		out.print("____________adminID " +user.getUsername());
                                        %>
<%--                                         <sec:authorize access="isAuthenticated()">Logout</sec:authorize> --%>
                                      <%--   <sec:authorize access="isAuthenticated()">Logout</sec:authorize>
                                        <sec:authorize access="hasAnyRole('ADMIN' , 'USER')">ROLE ADMIN</sec:authorize>
                                        <sec:authorize access="hasRole('USER')">ROLE USER</sec:authorize>
                                        <sec:authorize access="isAnonymous()">Login</sec:authorize>
                                        
                                        <sec:authorize access="hasAnyRole('ADMIN' , 'USER')" var="isAdmin" />
                                        <c:if test="${isAdmin eq true}"> True </c:if>
                                        
                                        <script type="text/javascript">
                                        	if('${isAdmin}' == 'true'){
                                        		alert("True");
                                        	}
                                        </script> --%>
                                        
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
                                                            <th>Group</th>
                                                            <th>Roles</th>
                                                            <th>Registered Date</th>
                                                            <th>Approve Date</th>
                                                            <th>Action</th>
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
            
            
            							 <div id="oneReqestedUser" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                        <h4 class="modal-title" id="myModalLabel">New user registered</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <h5>Username : <span id="username">Test</span></h5>
                                                        <hr>
                                                        <h5>Email : <span id="email">Test</span></h5>
                                                        <hr>
                                                        <h5>Register Date : <span id="registerDate">Test</span></h5>
                                                        <hr>
                                                        <h5>Group : <span id="group">Test</span></h5>
                                                        <input type="hidden" id="userid"/>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-danger waves-effect" id="btnReject">Reject</button>
                                                        <button type="button" class="btn btn-success waves-effect waves-light" id="btnAccept">Accept</button>
                                                    </div>
        
                                                </div><!-- /.modal-content -->
                                            </div><!-- /.modal-dialog -->
                                        </div><!-- /.modal -->
            
           								 <!--  Modal content for the above example -->
                                        <div class="modal fade all-request-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
                                            <div class="modal-dialog modal-lg">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                        <h4 class="modal-title" id="myLargeModalLabel">Requested Users</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                      
                                                      	<div class="row">
				                                            <div class="table-responsive">
				                                                    <table class="table">
				
				                                                    <thead>
				                                                        <tr>
				                                                            <th>#</th>
				                                                            <th>Username</th>
				                                                            <th>Email</th>
				                                                            <th>Registered Date</th>
				                                                            <th>Group</th>
				                                                            <th>Action</th>
				                                                        </tr>
				                                                    </thead>
				                                                    <tbody id="tblRequestedUser">
				                                                       
				                                                       
								
								
				                                                    </tbody>
				                                                </table>
				                                            </div>
				                                        </div>
                                                      
                                                      
                                                    </div>
                                                </div><!-- /.modal-content -->    
                                            </div><!-- /.modal-dialog -->
                                        </div><!-- /.modal -->
            


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
	
	<th>#</th>
                                                         
	
	 <script id="CONTENT_TEMPLATE" type="text/x-jquery-tmpl">
	    	<tr>
				<td>{{= id}}</td>
				<td>{{= username}}</td>
				<td>{{= email}}</td>
				<td>{{= position}}</td>
				<td>{{= roles}}</td>
				<td>{{= createdDate}}</td>
				<td>{{= approvedDate}}</td>
				<!--<td>{{if accountNonLocked == true}} <i class="ion-android-close" style="color: red;"></i> {{else}} <i class="ion-android-close" style="color: green;"></i> {{/if}}</td>
				<td>{{if enabled == true}} <i class="ion-android-close" style="color: green;"></i> {{else}} <i class="ion-android-close" style="color: red;"></i> {{/if}}</td>
				-->
				<td>Action</td>
			</tr>
        </script>
        
        <script id="tblRequestedUser_tmpl" type="text/x-jquery-tmpl">
	    	<tr>
				<td>{{= id}}</td>
				<td>{{= username}}</td>
				<td>{{= email}}</td>
				<td>{{= createdDate}}</td>
				<td>{{= position}}</td>
				<td>  
					<a class="btn btn-danger waves-effect waves-light m-b-5" onclick="rejectRequest({{= id}}, this)">Reject</a> 
					<a class="btn btn-success waves-effect waves-light m-b-5"  onclick="acceptRequest({{= id}} , this)">Accept</a>
				</td>
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
				    		"perPage"     : 20
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
								 reqUser    += 	 '<a href="javascript:void(0);" class="list-group-item" id="getUserReqestedUserByID" onclick="getUserReqestedByID('+data.RESP_DATA[i].id+')">'+
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
		                     
		                     $('#listRequestedUser').append('<a href="#" onclick="users.listAllRequestedUser()"  data-toggle="modal" data-target=".all-request-modal">'+
		                     								'<small>See all notifications</small>'+ 
		                               						'</a>');
                             
						}
				    },
				    error:function(data,status,er) { 
				        console.log("error: "+data+" status: "+status+" er:"+er);
				    }
				});
				
				
			};
			
			users.listAllRequestedUser = function(){
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
							var reqUser = "";
							
							if(data.RESP_DATA.length>0){
								$("tbody#tblRequestedUser").html('');
								$("#tblRequestedUser_tmpl").tmpl(data.RESP_DATA).appendTo("tbody#tblRequestedUser");
							}else{
								$("tbody#tblRequestedUser").html('<tr>NO CONTENTS</tr>');
							}
							
		                     
                             
						}
				    },
				    error:function(data,status,er) { 
				        console.log("error: "+data+" status: "+status+" er:"+er);
				    }
				});
				
				
			};
			
			users.findRequestedUserByID = function(id){
				$.ajax({ 
				    url: "${pageContext.request.contextPath}/api/apiuser/requestedUser/"+id, 
				    type: 'GET', 
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                    xhr.setRequestHeader("Authorization" , "Basic ${kaapi}");
	                },
				    success: function(data) { 
						console.log("API " + data.RESP_DATA.id);
						if(data.RESP_DATA != null){
							$("#username").text(data.RESP_DATA.username);
							$("#email").text(data.RESP_DATA.mail);
							$("#registerDate").text(data.RESP_DATA.createdDate);
							$("#group").text(data.RESP_DATA.position);
							$("#userid").val(data.RESP_DATA.id);
                             
						}
				    },
				    error:function(data,status,er) { 
				        console.log("error: "+data+" status: "+status+" er:"+er);
				    }
				});
				
				
			};
			
			
			users.findAllUserByUsername(1);
			users.countRequestedUser();
			

			
			$("#btnReject").click(function(){
				rejectRequest($("#userid").val())
				$('#oneReqestedUser').modal('hide');
			});
			
			$("#btnAccept").click(function(){
				acceptRequest($("#userid").val());
				$('#oneReqestedUser').modal('hide');
			});
			
		});
		
			function getUserReqestedByID(id){
				users.findRequestedUserByID(id);	
				$('#oneReqestedUser').modal('show');
			}
		
			function acceptRequest(userID , _this){
				$.ajax({ 
				    url: "${pageContext.request.contextPath}/api/apiuser/acceptRequest/"+userID, 
				    type: 'POST', 
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                    xhr.setRequestHeader("Authorization" , "Basic ${kaapi}");
	                },
				    success: function(data) { 
						console.log(data);
						if(_this != null){
							_this.closest('tr').remove();
						}
						users.countRequestedUser();
				    },
				    error:function(data,status,er) { 
				        console.log("error: "+data+" status: "+status+" er:"+er);
				    }
				});
			}
			
			function rejectRequest(userID , _this){
				$.ajax({ 
				    url: "${pageContext.request.contextPath}/api/apiuser/rejectRequest/"+userID,
				    type: 'POST', 
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                    xhr.setRequestHeader("Authorization" , "Basic ${kaapi}");
	                },
				    success: function(data) { 
						console.log(data);
						if(_this != null){
							_this.closest('tr').remove();
						}
						users.countRequestedUser();
				    },
				    error:function(data,status,er) { 
				        console.log("error: "+data+" status: "+status+" er:"+er);
				    }
				});
			}
		
			/*************************************************************/
			
			
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