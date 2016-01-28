<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
      
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--         <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc."> -->
<!--         <meta name="author" content="Coderthemes"> -->

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/theme/images/favicon_1.ico">

        <title>Participants | KhmerAcademy</title>

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

        <!-- sweet alerts -->
        <link href="${pageContext.request.contextPath}/resources/theme/assets/sweet-alert/sweet-alert.min.css" rel="stylesheet">

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
        
         <!--bootstrap-wysihtml5-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/theme/assets/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
        <link href="${pageContext.request.contextPath}/resources/theme/assets/summernote/summernote.css" rel="stylesheet" />
   		
   
   
    </head>



    <body class="fixed-left">
        
        <!-- Begin page -->
        <div id="wrapper">
        
           



            <!-- ============================================================== -->
            <!-- Start right Content here -->
            <!-- ============================================================== -->                      
            <div >
                <!-- Start content -->
                <div class="content">
                    <div class="container">

	 					<div id="block-post" style="background:white;margin:20px; margin: 20px 70px 20px;">
	 							
	 							<div class="user-details">
	                        <div class="pull-left">
	                            <img src="${pageContext.request.contextPath}/resources/theme/images/default-avatar.png" alt="" class="thumb-md img-circle">
	                        </div>
	                       	<div class="user-info">
	                            <div class="dropdown">
	                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">${username} <span class="caret"></span></a>
	                                <ul class="dropdown-menu">
	                                    <li><a href="${pageContext.request.contextPath}/logout"><i class="md md-settings-power"></i> Logout</a></li>
	                                </ul>
	                            </div>
	                        </div>
                    </div>
 							
 							
				 			<script src="//cdn.ckeditor.com/4.5.6/standard/ckeditor.js"></script>
							<textarea name="editor1" id="editor1"></textarea>
					        <script>
// 					            CKEDITOR.replace( 'editor1' );
					            
					            CKEDITOR.replace( 'editor1', {
					                filebrowserBrowseUrl: '/browser/browse.php',
					                filebrowserUploadUrl: '/uploader/upload.php'
					            });
					        </script>
	        
		                  		
		                    
		                    
		                    <button type="button" id="btPost" class="btn btn-primary waves-effect waves-light m-b-5">Post</button>
		             </div>

                        <div class="row">
                      
                 
                            <div class="col-md-12">
                            
		                     	
                                <section id="cd-timeline " class="cd-container getContents">
                                    
                                    
                                   <%--  <div class="cd-timeline-block">
                                        <div class="cd-timeline-img cd-success">
                                            <img src="${pageContext.request.contextPath}/resources/theme/images/users/avatar-1.jpg" alt="" class="thumb-md img-circle">
                                        </div> <!-- cd-timeline-img -->

                                        <div class="cd-timeline-content">
                                            <h3>Timeline Event One</h3>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto, optio, dolorum provident rerum aut hic quasi placeat iure tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus veritatis qui ut.</p>
                                            <span class="cd-date">May 23</span>
                                        </div> <!-- cd-timeline-content -->
                                    </div> <!-- cd-timeline-block --> --%>

                                   <!--  <div class="cd-timeline-block">
                                        <div class="cd-timeline-img cd-success">
                                            <i class="fa fa-tag"></i>
                                        </div> cd-timeline-img

                                        <div class="cd-timeline-content">
                                            <h3>Timeline Event One</h3>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto, optio, dolorum provident rerum aut hic quasi placeat iure tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus veritatis qui ut.</p>
                                            <span class="cd-date">May 23</span>
                                        </div> cd-timeline-content
                                    </div> cd-timeline-block
                                    
                                    <div class="cd-timeline-block">
                                        <div class="cd-timeline-img cd-success">
                                            <i class="fa fa-tag"></i>
                                        </div> cd-timeline-img

                                        <div class="cd-timeline-content">
                                            <h3>Timeline Event One</h3>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto, optio, dolorum provident rerum aut hic quasi placeat iure tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus veritatis qui ut.</p>
                                            <span class="cd-date">May 23</span>
                                        </div> cd-timeline-content
                                    </div> cd-timeline-block
                                    
                                    <div class="cd-timeline-block">
                                        <div class="cd-timeline-img cd-success">
                                            <i class="fa fa-tag"></i>
                                        </div> cd-timeline-img

                                        <div class="cd-timeline-content">
                                            <h3>Timeline Event One</h3>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto, optio, dolorum provident rerum aut hic quasi placeat iure tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus veritatis qui ut.</p>
                                            <span class="cd-date">May 23</span>
                                        </div> cd-timeline-content
                                    </div> cd-timeline-block
                                    
                                    <div class="cd-timeline-block">
                                        <div class="cd-timeline-img cd-success">
                                            <i class="fa fa-tag"></i>
                                        </div> cd-timeline-img

                                        <div class="cd-timeline-content">
                                            <h3>Timeline Event One</h3>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto, optio, dolorum provident rerum aut hic quasi placeat iure tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus veritatis qui ut.</p>
                                            <span class="cd-date">May 23</span>
                                        </div> cd-timeline-content
                                    </div> cd-timeline-block
                                    
                                    <div class="cd-timeline-block">
                                        <div class="cd-timeline-img cd-success">
                                            <i class="fa fa-tag"></i>
                                        </div> cd-timeline-img

                                        <div class="cd-timeline-content">
                                            <h3>Timeline Event One</h3>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto, optio, dolorum provident rerum aut hic quasi placeat iure tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus veritatis qui ut.</p>
                                            <span class="cd-date">May 23</span>
                                        </div> cd-timeline-content
                                    </div> cd-timeline-block -->
                                </section> <!-- cd-timeline -->
                            </div>
                        </div><!-- Row -->


            </div> <!-- container -->
                               
                </div> <!-- content -->

                <!-- <footer class="footer text-right">
                    2016 © KhmerAcademy
                </footer> -->

            </div>
            <!-- ============================================================== -->
            <!-- End Right content here -->
            <!-- ============================================================== -->



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

  		 <!-- CUSTOM JS -->
        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.app.js"></script>


        <script type="text/javascript">
            jQuery(document).ready(function($){
            var $timeline_block = $('.cd-timeline-block');

                //hide timeline blocks which are outside the viewport
                $timeline_block.each(function(){
                    if($(this).offset().top > $(window).scrollTop()+$(window).height()*0.75) {
                        $(this).find('.cd-timeline-img, .cd-timeline-content').addClass('is-hidden');
                    }
                });

                //on scolling, show/animate timeline blocks when enter the viewport
                $(window).on('scroll', function(){
                    $timeline_block.each(function(){
                        if( $(this).offset().top <= $(window).scrollTop()+$(window).height()*0.75 && $(this).find('.cd-timeline-img').hasClass('is-hidden') ) {
                            $(this).find('.cd-timeline-img, .cd-timeline-content').removeClass('is-hidden').addClass('bounce-in');
                        }
                    });
                });
            });
        </script>
        
    
	        <script src="${pageContext.request.contextPath}/resources/theme/js/jquery.tmpl.min.js"></script>
			
			
		
        
        <script type="text/javascript">
        $(function() {
        	
        	var part = {};
        	
        	part.list = function(){
        		$.ajax({
      	            url: "${pageContext.request.contextPath}/participants/list",
      	            type: "GET",
      	         	datatype: 'JSON',
    	  	        beforeSend: function(xhr) {
    	               xhr.setRequestHeader("Accept", "application/json");
    	               xhr.setRequestHeader("Content-Type", "application/json");
    	            },
      	            success: function(data) {
      	            	console.log(data);
      	          	 	contentsHTML ="";
      	          		for(i=0;i<data.RESP_DATA.length;i++){
      	          	 		contentsHTML += '<div class="cd-timeline-block">'+
    						                        '<div class="cd-timeline-img cd-success">'+
    						                        '<img src="${pageContext.request.contextPath}/resources/theme/images/default-avatar.png" alt="" class="thumb-md img-circle">'+
    						                    '</div> <!-- cd-timeline-img -->'+
    						
    						                    '<div class="cd-timeline-content">'+
    						                        '<h3>'+data.RESP_DATA[i].username+'</h3>'+
    						                        '<div>'+data.RESP_DATA[i].contents+'</div>'+
    						                        '<span class="cd-date">'+data.RESP_DATA[i].postDate+'</span>'+
    						                    '</div> <!-- cd-timeline-content -->'+
    						             '</div> <!-- cd-timeline-block -->';
      	          	 	}
      	          		
      	          		$(".getContents").html(contentsHTML);
      	          		
      	          		
      	             	

      	            	
      	            },
      	         	error: function(data){
      	         		alert(data);
      				}
      	        });
        		
        	};
        	
        	part.list();
        	
        	$("#btPost").click(function(e){ 
	       		if( CKEDITOR.instances['editor1'].getData().trim() == "" ){ 
	       			return;
	       		} 
		     	 json ={				
		     					"contents"		: CKEDITOR.instances['editor1'].getData().trim(),
								"username" 		: "${username}"
		     	  };
	       		  $.ajax({
	  	            url: "${pageContext.request.contextPath}/participants/add",
	  	            type: "POST",
	  	         	datatype: 'JSON',
	  	          	data: JSON.stringify(json), 
		  	        beforeSend: function(xhr) {
		               xhr.setRequestHeader("Accept", "application/json");
		               xhr.setRequestHeader("Content-Type", "application/json");
		            },
	  	            success: function(data) {
	  	            	console.log(data);
	  	            	part.list();
	  	            },
	  	         	error: function(data){
	  	         		alert(data);
	  				}
	  	        });
       			
       		});
        
        });
        </script>
        
        
		
	</body>
</html>