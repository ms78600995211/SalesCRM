<%-- 
    Document   : fileList
    Created on : Jul 18, 2023, 4:50:39 PM
    Author     : Kundan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>         
<jsp:include page="layout/header.jsp" />

<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
<div class="main-content">
<div class="page-content">
   <div class="container-fluid">
      <!-- start page title -->

      <div class="msgWrap">
         <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="mdi mdi-check-all me-2"></i>
               Added Successfully
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>                
     </div>

      <div class="row">
         <div class="col-12">
            <div class="page-title-box d-sm-flex align-items-center justify-content-between">
               <h4 class="mb-sm-0 font-size-18">Files Upload List</h4>
               <div class="page-title-right">
                  <ol class="breadcrumb m-0">
                     <li class="breadcrumb-item"><a href="index.php">Dashboard</a></li>
                     <li class="breadcrumb-item active">Files Upload List</li>
                  </ol>
               </div>
            </div>
         </div>
      </div>
      <!-- end page title -->
      <div class="row">
         <div class="col-12">
            <div class="row">
               <div class="col-lg-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-grid">
                                <button type="button" class="btn btn-success waves-effect waves-light" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"> <i class="bx bx-plus font-size-16 align-middle me-2"></i> Add Files Upload</button>
                            </div>
                            <div id="external-events" class="mt-2">
                                <br>
                                <p class="text-muted">Search your result according to this fields.</p>
                                <div>
                                    <form class="custom-validation" action="#" method="post" enctype="multipart/form-data">
                                       <div class="row">                      
                                          <!-- <div class="col-md-12">
                                             <div class="form-floating mb-3">
                                               <select class="form-select" id="floatingSelectGrid" aria-label="Floating label select example">
                                                   <option selected disabled>--Select One--</option>
                                                   <option value="1">One</option>
                                                   <option value="2">Two</option>
                                                   <option value="3">Three</option>
                                               </select>
                                               <label for="floatingSelectGrid">Enquiry Source</label>
                                             </div>
                                          </div>
                                          <div class="col-md-12">
                                             <div class="form-floating mb-3">
                                               <select class="form-select" id="floatingSelectGrid" aria-label="Floating label select example">
                                                   <option selected disabled>--Select One--</option>
                                                   <option value="1">One</option>
                                                   <option value="2">Two</option>
                                                   <option value="3">Three</option>
                                               </select>
                                               <label for="floatingSelectGrid">Status</label>
                                             </div>
                                          </div> -->
                                          <div class="col-md-12">
                                               <div class="mb-3">
                                                   <label class="form-label">Enquiry Source:</label>
                                                   <select class="form-control" name="District">
                                                     <option selected disabled>---Select One---</option>
                                                     <option value="#">Indiamart</option>
                                                     <option value="#">Google </option>
                                                     <option value="#">Website</option>
                                                     <option value="#">Phone</option>
                                                     <option value="#">Email</option>
                                                   </select>
                                               </div>
                                          </div>
                                          <div class="col-md-12">
                                               <div class="mb-3">
                                                   <label class="form-label">Status:</label>
                                                   <select class="form-control" name="District">
                                                      <option selected disabled>---Select One---</option>
                                                      <option value="">Pending</option>
                                                      <option value="">Not Answering</option>
                                                      <option value="Follow up Again">Follow up Again</option>
                                                      <option value="">Not Interested</option>
                                                      <option value="">Purchased From Others</option>
                                                      <option value="Success(Converted)">Success(Converted)</option>
                                                      <option value="Interested but buy later">Interested but buy later</option>
                                                      <option value="Demo Required">Demo Required</option>
                                                      <option value="Closed">Closed</option>
                                                      <option value="DeadLead">Dead Lead</option>
                                                      <option value="Reopen">Reopen</option>
                                                   </select>
                                               </div>
                                          </div>
                                          <div class="col-md-12">
                                               <div class="mb-3">
                                                  <button class="btn btn-primary w-100" type="submit" name=""><i class="bx bx-search font-size-16 align-middle me-2"></i> Search Result</button>
                                               </div>
                                          </div>
                                       </div>
                                    </form>
                                </div>



                                <!-- <div class="external-event fc-event bg-success" data-class="bg-success">
                                    <i class="mdi mdi-checkbox-blank-circle font-size-11 me-2"></i>New Event Planning
                                </div>
                                <div class="external-event fc-event bg-info" data-class="bg-info">
                                    <i class="mdi mdi-checkbox-blank-circle font-size-11 me-2"></i>Meeting
                                </div>
                                <div class="external-event fc-event bg-warning" data-class="bg-warning">
                                    <i class="mdi mdi-checkbox-blank-circle font-size-11 me-2"></i>Generating Reports
                                </div>
                                <div class="external-event fc-event bg-danger" data-class="bg-danger">
                                    <i class="mdi mdi-checkbox-blank-circle font-size-11 me-2"></i>Create New theme
                                </div> -->
                            </div>
                            <div class="row justify-content-center mt-5">
                                <img src="assets/images/verification-img.png" alt="" class="img-fluid d-block">
                            </div>
                        </div>
                    </div>
                </div>
               <!-- end col-->
               <div class="col-lg-9">
                  <div class="card">

                     <div class="btn-toolbar p-3">
                        <div>
                           <!-- <div class="btn-group me-2 mb-2 mb-sm-0"><button type="button" class="btn btn-primary"><i class="fa fa-inbox"></i></button><button type="button" class="btn btn-primary"><i class="fa fa-exclamation-circle"></i></button><button type="button" class="btn btn-primary"><i class="far fa-trash-alt"></i></button></div> -->
                           <!-- <div class="btn-group btn-group me-2 mb-2 mb-sm-0">
                              <button class="btn btn-primary btn-md dropdown-toggle" type="button" id="__BVID__753918___BV_dropdown__" data-bs-toggle="dropdown" aria-expanded="false">
                                 <div class="btn-content"><i class="fa fa-folder"></i><i class="mdi mdi-chevron-down ms-2"></i></div>
                              </button>
                              <ul class="dropdown-menu" aria-labelledby="__BVID__753918___BV_dropdown__" role="menu"><a class="dropdown-item" href="javascript: void(0);">Updates</a><a class="dropdown-item" href="javascript: void(0);">Social</a><a class="dropdown-item" href="javascript: void(0);">Team Manage</a></ul>
                           </div> -->
                           <!-- <div class="btn-group btn-group me-2 mb-2 mb-sm-0">
                              <button class="btn btn-primary btn-md dropdown-toggle" type="button" id="__BVID__527576___BV_dropdown__" data-bs-toggle="dropdown" aria-expanded="false">
                                 <div class="btn-content"><i class="fa fa-tag"></i><i class="mdi mdi-chevron-down ms-2"></i></div>
                              </button>
                              <ul class="dropdown-menu" aria-labelledby="__BVID__527576___BV_dropdown__" role="menu" style=""><a class="dropdown-item" href="javascript: void(0);">Updates</a><a class="dropdown-item" href="javascript: void(0);">Social</a><a class="dropdown-item" href="javascript: void(0);">Team Manage</a></ul>
                           </div> -->
                           <!-- <div class="btn-group btn-group me-2 mb-2 mb-sm-0">
                              <button class="btn btn-primary btn-md dropdown-toggle" type="button" id="__BVID__692118___BV_dropdown__" data-bs-toggle="dropdown" aria-expanded="false">
                                 <div class="btn-content"> More <i class="mdi mdi-dots-vertical ms-2"></i></div>
                              </button>
                              <ul class="dropdown-menu" aria-labelledby="__BVID__692118___BV_dropdown__" role="menu" style="">
                                 <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" onclick="showAssignedPerson()"> Assigned To </a></li>
                                 <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self"> Mail To</a></li>
                                 <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self"> Whatsapp</a></li>                                    
                                 <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self"> SMS</a></li>   
                              </ul>
                           </div> -->
                        </div>
                     </div>



                     <div class="card-body pt-0">
                        <div class="mt-1">
                           <!-- <div class="d-flex flex-wrap">
                              <h5 class="font-size-16 me-3">Recent Leave</h5>
                              <div class="ms-auto">
                                 <a href="javascript: void(0);" class="fw-medium text-reset">View All</a>
                              </div>
                           </div> -->
                           <div class="table-responsive mt-0">
                              <div class="row">
                                 <div class="col-12">
                                    <table id="datatable-buttons" class="table align-middle table-nowrap table-hover mb-0">
                                       <thead>
                                          <tr>
                                             <th>Sr.</th>
                                             <th>File</th>
                                             <th>File Name</th>
                                             <th>File Type</th>
                                             <th>Date</th>
                                             <th></th>
                                          </tr>
                                       </thead>
                                       <tbody>
                                          <tr>
                                             <td>1.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View PDF </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files" ><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td>2.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View Image </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td>3.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View Image </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td>4.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View Image </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td>5.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View Image </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td>6.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View Image </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td>7.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View PDF </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td>8.</td>
                                             <td class="text-capitalize">
                                                <a href="assets/images/product/crmUI.pdf" target="_blank">
                                                   <span class="badge badge-pill badge-soft-success font-size-11"> View PDF </span>
                                                </a>
                                             </td>
                                             <td>
                                                <p class="mb-0">Navik 200</p>
                                             </td>
                                             <td>Image</td>
                                             <td>
                                                22-04-2023
                                             </td>
                                             <td>
                                                <a href="#" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                <a href="enquiryDetail.php" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                             </td>
                                          </tr>
                                       </tbody>
                                    </table>
                                 </div>
                              </div>
                           </div>
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

<div class="modal fade bs-example-modal-lg-Add-Files" tabindex="-1" role="dialog" aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
      <div class="modal-content">
          <div class="modal-header">
              <h5 class="modal-title" id="myExtraLargeModalLabel">File Add</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
             <div>
              <form class="custom-validation" action="backend/employeeAdd.php" method="post" enctype="multipart/form-data">
                  <div class="row">                      
                      <div class="col-md-6">
                          <div class="mb-3">
                              <label for="" class="form-label">File Name:<span class="text-danger">*</span> </label>
                              <input type="text" class="form-control" name="Name" placeholder="Name" required>
                          </div>
                      </div>
                      <div class="col-md-6">
                          <div class="mb-3">
                              <label class="form-label">File Type:<span class="text-danger">*</span></label>
                              <select class="form-control" name="District">
                                <option selected disabled>---Select One---</option>
                                <option value="AL">Brochure</option>
                                <option value="AL">Catalog</option>                   
                                <option value="WY">Image</option>
                              </select>
                          </div>
                      </div>            
                      <div class="col-md-12">
                          <div class="mb-3">
                              <label for="" class="form-label">File/Image:<span class="text-danger">*</span></label>
                              <input type="file" class="form-control" name="" placeholder="Mobile" required>
                          </div>
                      </div>
                      <div class="col-md-12">
                          <div class="mb-3">
                              <label for="" class="form-label">About File: </label>
                              <textarea class="form-control" name="description" placeholder="About File" rows="5" required></textarea>
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

<!-- ADD Enquiry Model End -->






       
<jsp:include page="layout/footer.jsp" />


<script src="assets/libs/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/datatables.net-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="assets/libs/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
<script src="assets/libs/datatables.net-buttons-bs4/js/buttons.bootstrap4.min.js"></script>
<script src="assets/libs/jszip/jszip.min.js"></script>
<script src="assets/libs/pdfmake/build/pdfmake.min.js"></script>
<script src="assets/libs/pdfmake/build/vfs_fonts.js"></script>
<script src="assets/libs/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script src="assets/libs/datatables.net-buttons/js/buttons.print.min.js"></script>
<script src="assets/libs/datatables.net-buttons/js/buttons.colVis.min.js"></script>
<script src="assets/libs/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script src="assets/libs/datatables.net-responsive-bs4/js/responsive.bootstrap4.min.js"></script>
<script src="assets/js/pages/datatables.init.js"></script>



<script src="assets/libs/sweetalert2/sweetalert2.min.js"></script>


<script>
    $(document).on("click", ".deleteBtn", function(event) {
        let dataValueEmpID = $(this).attr('data-value');
        let redirectUrl = $(this).attr('data-url');
        event.preventDefault();   
            Swal.fire({
                title: "Are you sure?",
                text: "You won't be able to revert this!",
                icon: "warning",
                showCancelButton: !0,
                confirmButtonText: "Yes, delete it!",
                cancelButtonText: "No, cancel!",
                confirmButtonClass: "btn btn-success mt-2",
                cancelButtonClass: "btn btn-danger ms-2 mt-2",
                buttonsStyling: !1
            }).then(function(t) {
                t.value ? approveConfirmation() : '';
                function approveConfirmation(){ 
                    // $.ajax({
                    // url: "backend/LeaveApprovedConfirmation.php", 
                    // type : 'POST',
                    // data : {dataValueEmpID:dataValueEmpID},
                    // success: function(result){                        
                    //     const resultObj = JSON.parse(result);
                    //     var status = resultObj.status;
                    //     if (status === "true"){
                            Swal.fire(
                              'Good job!',
                              'You clicked the button!',
                              'success'
                            )
                            // $("#approveBtn").hide();
                            // $("#disApproveBtn").hide();
                        // }else{
                            // Swal.fire(
                            //   'Kuchh to gadbad hai Daya!',
                            //   'You clicked the button!',
                            //   'success'
                            // )
                        // }
                    // }});
                 }
                //  t.dismiss == Swal.DismissReason.cancel && Swal.fire({
                //     title: "Cancelled",
                //     text: "",
                //     icon: "error"
                // })
            })
        }); 
</script>







