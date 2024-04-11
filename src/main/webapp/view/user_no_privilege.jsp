<%-- 
    Document   : user_no_privilege
    Created on : 27 Sep, 2021, 5:06:10 PM
    Author     : Vikrant
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<div class="main-content">
    <div class="page-content">
        <div class="container-fluid">
            <!-- start page title -->




            <!-- end page title -->
            <div class="row">
                <div class="col-12">
                    <div class="row">

                        <!-- end col-->
                        <div class="col-lg-9">
                            <div class="card">
                                <div class="card-body">
                                    <div class="mt-1">

                                        <h2 style="color:red">You have no permission for this page.</h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- end col -->
                    </div>
                    <div style='clear:both'></div>
                </div>
            </div>
        </div>
        <!-- container-fluid -->
    </div>
    <!-- End Page-content -->

    <!-- ADD Enquiry Model Start -->

    <div class="modal fade bs-example-modal-xl-Add-Enquiry" tabindex="-1" role="dialog" aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="myExtraLargeModalLabel">Enquiry Add</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div>
                        <form class="custom-validation" action="backend/employeeAdd.php" method="post" enctype="multipart/form-data">
                            <div class="row">                      
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Name:<span class="text-danger">*</span> </label>
                                        <input type="text" class="form-control" name="Name"
                                               placeholder="Name" required>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label class="form-label">District:<span class="text-danger">*</span></label>
                                        <select class="form-control" name="District">
                                            <option selected disabled>---Select One---</option>
                                            <option value="AL">Alabama</option>
                                            <option value="WY">Wyoming</option>
                                            <option value="AL">Alabama</option>
                                            <option value="WY">Wyoming</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Mobile:<span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" name=""
                                               placeholder="Mobile" required>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Enquiry Source:<span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" name=""
                                               placeholder="Enquiry Source" required>
                                    </div>
                                </div>                      
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Address:<span class="text-danger">*</span></label>
                                        <textarea type="text" class="form-control" name=""
                                                  placeholder="Address"></textarea>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Enquiry Message:<span class="text-danger">*</span></label>
                                        <textarea class="form-control" placeholder="Enquiry Message"></textarea>
                                    </div>
                                </div>

                                <a href="#" class="show_hide mb-2" data-content="toggle-text">Add More... </a> 
                                <div class="contentHideShow">
                                    <div>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Enquiry No</label>
                                                    <input type="text" class="form-control" name="mobile_no" required
                                                           placeholder="Enquiry No" />
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Product Name</label>
                                                    <input type="text" class="form-control" name=""
                                                           placeholder="Product Name" required>
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Sender Email</label>
                                                    <input type="email" class="form-control" name="emergency_mobile_no" required                            
                                                           placeholder="Sender Email" />                                              
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Sender Company Name</label>
                                                    <input type="text" class="form-control" name=""
                                                           placeholder="Sender Company Name" required>
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">City</label>
                                                    <select class="form-control" name="City">
                                                        <option selected disabled>---Select One---</option>
                                                        <option value="AL">Alabama</option>
                                                        <option value="WY">Wyoming</option>
                                                        <option value="AL">Alabama</option>
                                                        <option value="WY">Wyoming</option>
                                                        <option value="WY">Wyoming</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">State</label>
                                                    <select class="form-control" name="State">
                                                        <option selected disabled>---Select One---</option>
                                                        <option value="AL">Alabama</option>
                                                        <option value="WY">Wyoming</option>
                                                        <option value="AL">Alabama</option>
                                                        <option value="WY">Wyoming</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary waves-effect" data-bs-dismiss="modal">Close</button>
                                <button class="btn btn-primary" type="submit" name="submitFormBtn">Submit form</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div>
    </div>
</div>

<%@include file="../layout/footer.jsp" %>
<script src="calendar/js/main.js"></script>
