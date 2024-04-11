<%-- 
    Document   : demoList
    Created on : Jul 21, 2023, 12:29:24 PM
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

<!--            <div class="msgWrap">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="mdi mdi-check-all me-2"></i>
                    Added Successfully
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>                
            </div>-->

            <div class="row">
                <div class="col-12">
                    <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                        <h4 class="mb-sm-0 font-size-18">Demo List</h4>
                        <div class="page-title-right">
                            <ol class="breadcrumb m-0">
                                <li class="breadcrumb-item"><a href="index.php">Dashboard</a></li>
                                <li class="breadcrumb-item active">Demo List</li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end page title -->
            <div class="row">
                <div class="col-12">
                    <div class="row">
<!--                        <div class="col-lg-3">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-grid">
                                        <button type="button" class="btn btn-success waves-effect waves-light" data-bs-toggle="modal" data-bs-target=".bs-example-modal-xl-Add-Enquiry"> <i class="bx bx-plus font-size-16 align-middle me-2"></i> Add Enquiry</button>
                                    </div>
                                    <div id="external-events" class="mt-2">
                                        <br>
                                        <p class="text-muted">Search your result according to this fields.</p>
                                        <div>
                                            <form class="custom-validation" action="backend/employeeAdd.php" method="post" enctype="multipart/form-data">
                                                <div class="row">                      

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



                                    </div>
                                    <div class="row justify-content-center mt-5">
                                        <img src="assets/images/verification-img.png" alt="" class="img-fluid d-block">
                                    </div>
                                </div>
                            </div>
                        </div>
                         end col-->
                        <div class="col-lg-12">
                            <div class="card">
                                
                                
                                <div class="px-3">
                                    <div class="mt-2">
                                       <p class="text-muted mb-2">Search your result according to this fields.</p>
                                       <div class="row">
<!--                                          <div class="col-md-1">
                                             <div class="btn-toolbar pb-1">
                                                <div>
                                                   <div class="btn-group btn-group me-2 mb-2 mb-sm-0">
                                                      <button class="btn btn-primary btn-md dropdown-toggle" type="button" id="__BVID__692118___BV_dropdown__" data-bs-toggle="dropdown" aria-expanded="false">
                                                         <div class="btn-content"> More <i class="mdi mdi-dots-vertical ms-2"></i></div>
                                                      </button>
                                                      <ul class="dropdown-menu" aria-labelledby="__BVID__692118___BV_dropdown__" role="menu" style="">
                                                         <c:choose>
                                                            <c:when test="${role eq 'Sales Manager'}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                Add code for other cases if needed 
                                                            </c:otherwise>
                                                         </c:choose>
                                                         <li role="presentation" class="">
                                                            <a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" onclick="clickOnMoreAction(1)">Assigned To</a>
                                                         </li>
                                                         <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" data-bs-target="#composemodal" onclick="clickOnMoreAction(2)"> Mail To</a></li>
                                                         <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" data-bs-target="#setReminder" onclick="clickOnMoreAction(3)">Set Reminder</a></li>
                                                         <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self"> Whatsapp</a></li>
                                                         <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self"> SMS</a></li>
                                                      </ul>
                                                   </div>
                                                </div>
                                             </div>
                                          </div>-->
                                          <div class="col-md-9">
                                             <form class="custom-validation" action="EnquiryController" method="post">
                                                <div class="row">
                                                   <div class="col-md-2">
                                                      <div class="mb-3">
                                                         <select class="form-control" name="source_id">
                                                            <option selected disabled>---Select Source---</option>
                                                            <c:forEach var="source" items="${requestScope['sourcelist']}"
                                                               varStatus="loopCounter">
                                                               <option value="${source.enquiry_source_table_id}" >${source.enquiry_source}</option>
                                                            </c:forEach>
                                                         </select>
                                                      </div>
                                                   </div>
                                                   <div class="col-md-2">
                                                      <div class="mb-3">
                                                         <select class="form-control" name="status_id">
                                                            <option selected disabled>---Select Status---</option>
                                                            <c:forEach var="status" items="${requestScope['statuslist']}"
                                                               varStatus="loopCounter">
                                                               <option value="${status.enquiry_status_id}" >${status.status}</option>
                                                            </c:forEach>
                                                         </select>
                                                      </div>
                                                   </div>
                                                   <div class="col-md-2">
                                                      <div class="mb-3">
                                                         <select class="form-control" name="designation_id">
                                                            <option selected disabled>---Select User---</option>
                                                            <c:forEach var="designation" items="${requestScope['lists']}"
                                                               varStatus="loopCounter">
                                                               <option value="${designation.key_person_id}" >${designation.salutation} ${designation.key_person_name}</option>
                                                            </c:forEach>
                                                         </select>
                                                      </div>
                                                   </div>
                                                   <div class="col-md-1">
                                                      <div class="mb-3">
                                                         <button class="btn btn-primary w-100" type="submit" name="task" value="Search Result"><i class="bx bx-search font-size-16 align-middle"></i> </button>
                                                      </div>
                                                   </div>
                                                   <div class="col-md-1">
                                                      <div class="mb-3">
                                                         <button class="btn btn-warning w-100" type="submit" name="task1" value="getImportant" onclick="selectImportant()"><i class="fa-solid fa-heart font-size-16 align-middle"></i> </button>
                                                      </div>
                                                   </div>
                                                   <div class="col-md-1">
                                                      <div class="mb-1">
                                                         <button class="btn btn-danger w-100" type="submit" name="task" value="PDF" ><i class="far fa-file-pdf font-size-16 align-middle"></i> </button>
                                                      </div> 
                                                   </div>
                                                   <div class="col-md-1">
                                                      <div class="mb-1">
                                                         <button class="btn btn-success w-100" type="submit" name="task" value="Excel"><i class="far fa-file-excel font-size-16 align-middle"></i> </button>
                                                      </div>
                                                   </div>
                                                   <div class="col-md-1">
                                                      <div class="mb-1">
                                                         <a href="#" class="btn btn-secondary w-100" onclick="callIndiamartSchedular()"><i class="fa-solid fa-arrows-rotate"></i></a>
                                                      </div>
                                                   </div>
                                                </div>
                                             </form>
                                          </div>
                                          <div class="col-md-2">
                                             <div class="row">
                                                <div class="col-md-12 text-right">
                                                   <button type="button" class="btn btn-primary waves-effect waves-light text-right" data-bs-toggle="modal" data-bs-target=".bs-example-modal-xl-Add-Enquiry"> <i class="bx bx-plus font-size-16 align-middle me-2"></i> Add Enquiry</button>
                                                </div>
                                             </div>
                                          </div>
                                       </div>
                                       <div class="mb-2">
                                          <form method="POST" id="dataForm" action="EnquiryController">
                                             <div class="btn-group btn-group me-2 mb-2 mb-sm-0" id="assignedPerson" style="display: none;">
                                                <div class="d-flex">
                                                   <div>
                                                      <select class="form-control" name="assign_to"   id="assign_to" required>
                                                         <option>Assign to</option>
                                                      </select>
                                                   </div>
                                                   <div class="ms-2">
                                                      <button class="form-control btn btn-success" type="submit" onclick="submitform();"><i class="mdi mdi-check"></i></button>
                                                   </div>
                                                   <div class="ms-1">
                                                      <a class="form-control btn btn-danger" onclick="hideAssignedPerson()" type="button"><i class="mdi mdi-close"></i></a>
                                                   </div>
                                                   <div id="allids"></div>
                                                   <input type="hidden" name="task" value="assign_in_bulk" />
                                                </div>
                                             </div>
                                             <div class="btn-group btn-group me-2 mb-2 mb-sm-0" id="assignedPersonMail" style="display:none">
                                                <div class="d-flex">
                                                   <div>
                                                      <select class="form-control" name="mail_to"   id="mail_to">
                                                         <option>Mailed To</option>
                                                      </select>
                                                   </div>
                                                   <div class="ms-2">
                                                      <a class="form-control btn btn-success" type="submit" disabled="false" href="javascript: void(0);" tardropdownget="_self" data-bs-toggle="modal" data-bs-target="#composemodal" onclick="mailTo()"><i class="mdi mdi-check"></i></a>
                                                   </div>
                                                   <div class="ms-1">
                                                      <a class="form-control btn btn-danger" onclick="hideAssignedPersonMail()" type="button"><i class="mdi mdi-close"></i></a>
                                                   </div>
                                                   <div id="allids"></div>
                                                   <input type="hidden" name="task" value="assign_in_bulk" />
                                                </div>
                                             </div>
                                             <div class="btn-group btn-group me-2 mb-2 mb-sm-0" id="setReminderId" style="display:none">
                                                <div class="d-flex">
                                                   <div>
                                                      <select class="form-control" name="mail_to"   id="mail_to">
                                                         <option>Set Reminder</option>
                                                      </select>
                                                   </div>
                                                   <div class="ms-2">
                                                      <button class="form-control btn btn-success" id="reminderfunid" type="submit" disabled tardropdownget="_self" data-bs-toggle="modal" data-bs-target="#setReminder" onclick="setReminderFun()"><i class="mdi mdi-check"></i></button>
                                                   </div>
                                                   <div class="ms-1">
                                                      <a class="form-control btn btn-danger" onclick="hideAssignedPersonReminder()" type="button"><i class="mdi mdi-close"></i></a>
                                                   </div>
                                                   <div id="allids"></div>
                                                   <input type="hidden" name="task" value="assign_in_bulk" />
                                                </div>
                                             </div>
                                          </form>
                                       </div>
                                    </div>
                                 </div>




                                <div class="card-body pt-0">
                                    <div class="mt-1">

                                        <div class="table-responsive mt-0">
                                            <div class="row">
                                                <div class="col-12">
                                                    <table id="datatable-buttons" class="table align-middle table-nowrap table-hover mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th>
                                                                    <div class="form-check font-size-16">
                                                                        <input class="form-check-input" type="checkbox" id="showCheckoutAll">
                                                                        <label class="form-check-label" for="checkAll"></label>All.
                                                                    </div>                                                
                                                                </th>
                                                                <th>Sr.</th>
                                                                <th>Sender. Name</th>
                                                                <th>Mobile No.</th>
                                                                <th>Product</th>
                                                                <th>Location</th>
                                                                <th>Assigned</th>
                                                                <th>Status</th>
                                                                <th></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                                       varStatus="loopCounter">
                                                                <tr>
                                                                    <td style="width: 20px;" class="align-middle">
                                                                        <div class="form-check font-size-16">
                                                                            <input class="form-check-input checkBoxList" type="checkbox" id="checkAll">
                                                                            <label class="form-check-label" for="checkAll"></label>
                                                                        </div>
                                                                    </td>
                                                                    <td class="fontFourteen">${loopCounter.count}</td>   
                                                                    <td>

                                                                        <p class="mb-0">${beanType.sender_name}</p>
                                                                        <small class="text-muted">${beanType.enquiry_date_time}</small>
                                                                    </td>
                                                                     <td class="fontFourteen" >
                                                                        <p class="mb-0 noWrap">${beanType.sender_mob}</p>
                                                                        <small class="text-muted" id="sender_email${beanType.enquiry_table_id}">${beanType.sender_email}</small>
                                                                    </td>
                                                                    
                                                                    
                                                                     <td class="text-capitalize">
                                                                        <p class="mb-0 noWrap maxWidth200 ellipsisShow">${beanType.product_name}</p>
                                                                        <small class="text-muted">${beanType.enquiry_source}</small>       
                                                                    </td>
                                                                    
                                                                    
                                                                    <td class="text-capitalize">
                                                                        <p class="mb-0">${beanType.enquiry_city}</p>
                                                                        <small class="text-muted">${beanType.enquiry_state}</small>
                                                                    </td>
                                                                    
                                                                    <td>${beanType.assigned_to}</td>
                                                                    <td>
                                                                        <span class="badge badge-pill badge-soft-success font-size-11">${beanType.status}</span>
                                                                    </td>
                                                                    <td>
                                                                       <a href="EnquiryListController?id=${beanType.enquiry_table_id}"><span class="badge badge-pill badge-soft-primary font-size-14"><i class="far fa-list-alt"></i></span></a>
                                                                   
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>

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

    <script>
                                                    $(document).ready(function () {
                                                        $(".contentHideShow").hide();
                                                        $(".show_hide").on("click", function () {
                                                            var txt = $(".contentHideShow").is(':visible') ? 'Add More...' : 'Add Less';
                                                            $(".show_hide").text(txt);
                                                            $(this).next('.contentHideShow').slideToggle(200);
                                                        });
                                                    });
    </script>


    <script>
        jQuery(document).ready(function () {
            jQuery('#showCheckoutAll').change(function () {
                if ($(this).prop('checked')) {
                    // let myArray = (".checkBoxList").val();
                    $(".checkBoxList").attr("checked", "checked", );
                } else {
                    $(".checkBoxList").removeAttr("checked", "checked", );
                }
            });
        });
    </script>

    <script>
        $("#assignedPerson").hide();
        function showAssignedPerson() {
            $("#assignedPerson").show();
        }
        function hideAssignedPerson() {
            $("#assignedPerson").hide();
        }
    </script>