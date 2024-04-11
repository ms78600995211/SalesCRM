<%-- 
    Document   : login
    Created on : 6 Jun, 2023, 12:32:20 PM
    Author     : Akshay
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SalesCRM</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="assets/images/product/playLogo.png">
        <link href="assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
        <link href="assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <div class="account-pages my-5 pt-sm-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-8 col-lg-6 col-xl-5">
                        <div class="card overflow-hidden">
                            <div class="bg-primary bg-soft">
                                <div class="row">
                                    <div class="col-7">
                                        <div class="text-primary p-4">
                                            <h5 class="text-primary">Welcome Back !</h5>
                                            <p>Sign in to continue to CRM.</p>
                                        </div>
                                    </div>
                                    <div class="col-5 align-self-end">
                                        <img src="assets/images/profile-img.png" alt="" class="img-fluid">
                                    </div>
                                </div>
                            </div>
                            <div class="card-body pt-0"> 
                                <div class="auth-logo">
                                    <a href="index.html" class="auth-logo-light">
                                        <div class="avatar-md profile-user-wid mb-4">
                                            <span class="avatar-title rounded-circle bg-light">
                                                <img src="assets/images/logo-light.svg" alt="" class="rounded-circle" height="34">
                                            </span>
                                        </div>
                                    </a>

                                    <a href="index.html" class="auth-logo-dark">
                                        <div class="avatar-md profile-user-wid mb-4">
                                            <span class="avatar-title rounded-circle bg-light">
                                                <img src="assets/images/product/playLogo.png" alt="" class="rounded-circle" height="55">
                                            </span>
                                        </div>
                                    </a>
                                </div>
                                <div class="p-2">

                                    <c:if test="${not empty message}"> 
                                        <div>
                                            <div class="alert alert-danger py-2"> ${message}
                                            </div>
                                        </div>
                                    </c:if>


                                </div>
                                <form class="form-horizontal custom-validation" action="LoginController" method="post" id="login_form">        
                                    <div class="mb-3">
                                        <label for="username" class="form-label">Username</label>
                                        <input type="text" class="form-control" placeholder="Enter username" name="username" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="username" class="form-label">Password</label>
                                        <input type="password" class="form-control" placeholder="Enter password" name="password" required>
                                    </div>                                    
                                    <div class="mt-3 d-grid">
                                        <input type="submit" name="task" value="Log In" class="btn btn-primary waves-effect waves-light">
                                        <!--<button class="btn btn-primary waves-effect waves-light" type="submit" name="task">Log In</button>-->
                                    </div>
                                </form>
                            </div>            
                        </div>
                    </div>
                    <div class="mt-5 text-center">                            
                        <div>
                            <p>© <script>document.write(new Date().getFullYear())</script> CRM. Crafted with <a href="https://apogeegnss.com/" target="_blank"> <i class="mdi mdi-heart text-danger"></i> by APOGEEGNSS</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="assets/libs/jquery/jquery.min.js"></script>
    <script src="assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="assets/libs/metismenu/metisMenu.min.js"></script>
    <script src="assets/libs/simplebar/simplebar.min.js"></script>
    <script src="assets/libs/node-waves/waves.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
    <!-- App js -->
    <script src="assets/js/app.js"></script>


    <script src="assets/js/pages/form-validation.init.js"></script>
    <script src="assets/libs/parsleyjs/parsley.min.js"></script>
</body>
</html>



<style>
    .error{
        color:red!important;
        margin-left: 15px;
        font-size: 13px;
    }
</style>

<script>
                                // $(document).ready(function(){
                                //     $("#login_form").validate({
                                //       rules :{
                                //         email: {
                                //               required: true,
                                //           },
                                //         password: {
                                //               required: true,  
                                //               minlength: 6,
                                //           }
                                //       },
                                //       messages :{ 
                                //         email: {
                                //                 required:  "Please Enter Email or Username.",
                                //         },
                                //         password: {
                                //                 required: "Please Enter Password.",  
                                //                 minlength: "Please Enter atleast 6 Digit."
                                //         },
                                //       }
                                //     });
                                // });
</script>
