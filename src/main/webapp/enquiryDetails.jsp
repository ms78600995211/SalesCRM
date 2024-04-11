<%-- 
    Document   : enquiryDetails
    Created on : 3 Jul, 2023, 3:23:34 PM
    Author     : admin
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
            <div class="row">
                <div class="col-12">
                    <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                        <div class="d-flex">
                            <div><a onclick="history.go(-2)"><i class="fa-solid fa-angle-left"></i></a> </div>
                            <h4 class="mb-sm-0 font-size-18 ms-2">Enquiry Detail</h4>
                        </div>
                        <!--<button type="button" name="back" onclick="history.back()">back</button>-->
                        <div class="page-title-right">
                            <ol class="breadcrumb m-0">
                                <li class="breadcrumb-item"><a href="index.php">Dashboard</a></li>
                                <li class="breadcrumb-item active">Enquiry Detail</li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end page title -->
            <c:if test="${not empty message}">

                <div class="msgWrap">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="mdi mdi-check-all me-2"></i>
                        Added Successfully
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>                
                </div>
            </c:if>
            <div class="row">
                <div class="col-12">
                    <div class="row">
                        <div class="col-lg-5">
                            <div class="card">
                                <div class="card-body p-0">
                                    <!-- <div class="d-grid">
                                       <button type="button" class="btn btn-success waves-effect waves-light" data-bs-toggle="modal" data-bs-target=".bs-example-modal-xl-Add-Enquiry"> <i class="bx bx-plus font-size-16 align-middle me-2"></i> Add Enquiry</button>
                                    </div> -->
                                    <div id="external-events">
                                        <!-- <br> -->
                                        <!-- <p class="text-muted">Search your result according to this fields.</p> -->
                                        <div>                              
                                            <div class="w-100 user-chat">
                                                <div class="card mb-0">
                                                    <div class="p-3 border-bottom ">
                                                        <div class="row">
                                                            <div class="col-md-12 col-12">
                                                                <h5 class="font-size-15 mb-1">Steven Franklin</h5>
                                                                <p class="text-muted mb-0"><i class="mdi mdi-circle text-success align-middle me-1"></i> Active now</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div class="chat-conversation p-3">
                                                            <ul class="list-unstyled mb-0" data-simplebar style="max-height: 350px;">
                                                                <li> 
                                                                    <div class="chat-day-title">
                                                                        <span class="title">Today</span>
                                                                    </div>
                                                                </li>
                                                                <li class="me-3">
                                                                    <div class="conversation-list mb-2">                                                       
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Steven Franklin</div>
                                                                            <p class="mb-1">
                                                                                Hello!
                                                                            </p>
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:00</p>
                                                                        </div>                                                        
                                                                    </div>
                                                                </li>    
                                                                <li class="right ms-3">
                                                                    <div class="conversation-list mb-2">                                                        
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Henry Wells</div>
                                                                            <p class="mb-1">
                                                                                Hi, How are you? What about our next meeting?
                                                                            </p>    
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:02</p>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <li class="me-3">
                                                                    <div class="conversation-list mb-2">                                                        
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Steven Franklin</div>
                                                                            <p class="mb-1">
                                                                                Yeah everything is fine
                                                                            </p>

                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:06</p>
                                                                        </div>                                                        
                                                                    </div>
                                                                </li>    
                                                                <li class="last-chat">
                                                                    <div class="conversation-list mb-2">
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Steven Franklin</div>
                                                                            <p class="mb-1">& Next meeting tomorrow 10.00AM</p>
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:06</p>
                                                                        </div>                                                        
                                                                    </div>
                                                                </li>    
                                                                <li class=" right me-3">
                                                                    <div class="conversation-list mb-2">
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Henry Wells</div>
                                                                            <p class="mb-1">
                                                                                Wow that's great
                                                                            </p>    
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:07</p>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <li class="me-3">
                                                                    <div class="conversation-list mb-2">                                                       
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Steven Franklin</div>
                                                                            <p class="mb-1">
                                                                                Hello!
                                                                            </p>
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:00</p>
                                                                        </div>                                                        
                                                                    </div>
                                                                </li>    
                                                                <li class="right ms-3">
                                                                    <div class="conversation-list mb-2">                                                        
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Henry Wells</div>
                                                                            <p class="mb-1">
                                                                                Hi, How are you? What about our next meeting?
                                                                            </p>    
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:02</p>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <li class="me-3">
                                                                    <div class="conversation-list mb-2">                                                        
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Steven Franklin</div>
                                                                            <p class="mb-1">
                                                                                Yeah everything is fine
                                                                            </p>

                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:06</p>
                                                                        </div>                                                        
                                                                    </div>
                                                                </li>    
                                                                <li class="last-chat">
                                                                    <div class="conversation-list mb-2">
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Steven Franklin</div>
                                                                            <p class="mb-1">& Next meeting tomorrow 10.00AM</p>
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:06</p>
                                                                        </div>                                                        
                                                                    </div>
                                                                </li>    
                                                                <li class=" right me-3">
                                                                    <div class="conversation-list mb-2">
                                                                        <div class="ctext-wrap px-2 py-1">
                                                                            <div class="conversation-name mb-0">Henry Wells</div>
                                                                            <p class="mb-1">
                                                                                Wow that's great
                                                                            </p>    
                                                                            <p class="chat-time mb-0"><i class="bx bx-time-five align-middle me-1"></i> 10:07</p>
                                                                        </div>
                                                                    </div>
                                                                </li> 
                                                            </ul>
                                                        </div>
                                                        <div class="p-3 chat-input-section">
                                                            <form action="EnquiryListController" method="POST">
                                                                <div class="row">
                                                                    <div class="col">
                                                                        <div class="position-relative">
                                                                            <input type="text" name="message" class="form-control chat-input pe-0" placeholder="Enter Message...">
                                                                            <!--<div class="chat-input-links" id="tooltip-container">
                                                                                <ul class="list-inline mb-0">
                                                                                    <li class="list-inline-item"><a href="javascript: void(0);" title="Emoji"><i class="mdi mdi-emoticon-happy-outline"></i></a></li>
                                                                                    <li class="list-inline-item"><a href="javascript: void(0);" title="Images"><i class="mdi mdi-file-image-outline"></i></a></li>
                                                                                    <li class="list-inline-item"><a href="javascript: void(0);" title="Add Files"><i class="mdi mdi-file-document-outline"></i></a></li>
                                                                                </ul>
                                                                            </div> -->
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-auto">
                                                                        <button type="submit" name="task" value="sendChatMessage" class="btn btn-primary btn-rounded chat-send w-md waves-effect waves-light"><span class="d-none d-sm-inline-block me-2">Send</span> <i class="mdi mdi-send"></i></button>
                                                                        <!--<input type="submit" name="Send" value="Send" class="btn btn-primary btn-rounded chat-send w-md waves-effect waves-light"><span class="d-none d-sm-inline-block me-2"></span> <i class="mdi mdi-send"></i></input>-->
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                        </div>                           
                                    </div>
                                    <!-- <div class="row justify-content-center mt-5">
                                       <img src="assets/images/verification-img.png" alt="" class="img-fluid d-block">
                                    </div> -->
                                </div>
                            </div>
                        </div>
                        <!-- end col-->
                        <div class="col-lg-7">
                            <div class="card">
                                <div class="card-body">
                                    <div class="mt-1"> 

                                        <div>
                                            <!-- <a href="javascript: void(0);" id="assignPerson" data-type="select" data-pk="1" data-value="" data-title="Select sex"></a> -->
                                        </div>                       


                                        <div class="d-flex flex-wrap">
                                            <h5 class="font-size-16 me-3">Enquiry Detail</h5>
                                            <div class="ms-auto">

                                                <form method="POST" action="EnquiryListController">

                                                    <select name="assign_to"   id="assign_to">
                                                        <option>Assign to</option>
                                                    </select>
                                                    <input type="hidden" id="key_person_id" value="${key_person_id}">
                                                    <input type="hidden" name="enquiry_table_id" value="${enquiry_table_id}">
                                                    <input type="submit" name="task" value="Assign To" class="btn btn-success ">
                                                </form>

                                            </div>
                                        </div>


                                        <div class="table-responsive mt-0">
                                            <div class="row mt-4">
                                                <c:forEach var="beanType" items="${requestScope['list']}"
                                                           varStatus="loopCounter">
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Enquiry No:</strong></p>
                                                            <p>${beanType.enquiry_no}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Sender Name:</strong></p>
                                                            <p>${beanType.sender_name}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Sender Email:</strong></p>
                                                            <p>${beanType.sender_email}</p>
                                                           
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Sender Mobile:</strong></p>
                                                            <p>${beanType.sender_mob}</p>
                                                          
                                                        </div>
                                                    </div>
                                                        <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Other Email:</strong></p>
                                                            
                                                            <p>${beanType.sender_alternate_email}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Other Mobile:</strong></p>
                                                            
                                                            <p>${beanType.sender_alternate_mob}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Company Name:</strong></p>
                                                            <p>${beanType.sender_company_name}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Address:</strong></p>
                                                            <p>${beanType.enquiry_address}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>City:</strong></p>
                                                            <p>${beanType.enquiry_city}</p>
                                                        </div>
                                                    </div>
                                                    <!--                                                <div class="col-md-6 ">
                                                                                                        <div>
                                                                                                            <p class="mb-1"><strong>District:</strong></p>
                                                                                                            <p>${beanType.sender_company_name}</p>
                                                                                                        </div>
                                                                                                    </div>-->
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>State:</strong></p>
                                                            <p>${beanType.enquiry_state}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Enquiry Date Time:</strong></p>
                                                            <p>${beanType.enquiry_date_time}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Product Name:</strong></p>
                                                            <p>${beanType.product_name}</p>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12 ">
                                                        <div>
                                                            <p class="mb-1"><strong>Enquiry Message:</strong></p>
                                                            <p>${beanType.enquiry_message}</p>
                                                        </div>
                                                    </div>


                                                    <!--                                                    <div class="d-flex flex-wrap">                                    
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                    
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                    
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                    
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <iframe class="mb-1"
                                                                                                                        src="assets/images/product/crmUI.pdf#toolbar=0&navpanes=0&scrollbar=0"
                                                                                                                        frameBorder="0"
                                                                                                                        scrolling="auto"
                                                                                                                        height="113"
                                                                                                                        width="80"
                                                                                                                        ></iframe>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12">View <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <a href="assets/images/product/playLogo.png" target="_blank">
                                                                                                                    <img src="assets/images/product/playLogo.png">
                                                                                                                </a>
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12" download="">Download <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <img src="assets/images/product/playLogo.png">
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12" download="">Download <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <img src="assets/images/product/playLogo.png">
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12" download="">Download <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <img src="assets/images/product/playLogo.png">
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12" download="">Download <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <img src="assets/images/product/playLogo.png">
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12" download="">Download <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                            <div class="border attechmentBox">
                                                                                                                <img src="assets/images/product/playLogo.png">
                                                                                                                <a href="assets/images/product/crmUI.pdf" class="btn btn-primary px-1 py-1 font-size-12" download="">Download <i class="bx bx-download align-baseline ms-1"></i></a>
                                                                                                            </div>
                                                                                                        </div> -->


                                                </c:forEach>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <%--<c:if test="${role ne  SALES_SUPERVISOR}">--%>
                                            <c:choose>
                                                <c:when test="${role eq SALES_MANAGER}">
                                                    <c:if test="${assignedToId eq 'not exists'}">
                                                        <form action="EnquiryListController" method="post">
                                                            <div class="mb-3">
                                                                <p class="mb-1"><strong>Select Status:</strong></p>
                                                                <select name="status_id" class="form-control" required="required">
                                                                    <option selected disabled>---Select One---</option>
                                                                    <c:forEach var="beanTypess" items="${requestScope['statuslist']}"
                                                                               varStatus="loopCounter">
                                                                        <option value="${beanTypess.enquiry_status_id}" >${beanTypess.status}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="mb-3">
                                                                <p class="mb-1"><strong>Remark:</strong></p>
                                                                <textarea name="remark" class="form-control" required="required"></textarea>
                                                            </div>
                                                            <input type="hidden" name="enquiry_table_id" value="${enquiry_table_id}">
                                                            <div class="col-md-12">
                                                                <div class="mb-3">
                                                                    <input type="submit" name="task" value="UPDATE" class="btn btn-primary w-100">
                                                                    <!--<button class="btn btn-primary w-100" type="submit" name=""><i class="font-size-16 align-middle me-2"></i> UPDATE</button>-->
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </c:if>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="EnquiryListController" method="post">
                                                        <div class="mb-3">
                                                            <p class="mb-1"><strong>Select Status:</strong></p>
                                                            <select name="status_id" class="form-control" required="required">
                                                                <option selected disabled>---Select One---</option>
                                                                <c:forEach var="beanTypess" items="${requestScope['statuslist']}"
                                                                           varStatus="loopCounter">
                                                                    <option value="${beanTypess.enquiry_status_id}" >${beanTypess.status}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="mb-3">
                                                            <p class="mb-1"><strong>Remark:</strong></p>
                                                            <textarea name="remark" class="form-control" required="required"></textarea>
                                                        </div>
                                                        <input type="hidden" name="enquiry_table_id" value="${enquiry_table_id}">
                                                        <div class="col-md-12">
                                                            <div class="mb-3">
                                                                <input type="submit" name="task" value="UPDATE" class="btn btn-primary w-100">
                                                                <!--<button class="btn btn-primary w-100" type="submit" name=""><i class="font-size-16 align-middle me-2"></i> UPDATE</button>-->
                                                            </div>
                                                        </div>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                            <%--</c:if>--%>
                                        </div>
                                        <div class="col-md-7">
                                            <div>                                 
                                                <div class="table-responsive">
                                                    <table class="table align-middle table-nowrap mb-0">
                                                        <thead class="table-light">
                                                            <tr>
                                                                <th class="align-middle">Assigned By</th>
                                                                <th class="align-middle">Assigned To</th>

                                                                <th class="align-middle">Date/Time</th>
                                                                <th class="align-middle">Status</th>
                                                                <th class="align-middle">Updated By</th>
                                                                <th class="align-middle">Remark</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="beanTypes" items="${requestScope['lists']}"
                                                                       varStatus="loopCounter">
                                                                <tr>
                                                                    <td><a href="javascript: void(0);" class="text-body fw-bold">${beanTypes.assigned_by}</a> </td>
                                                                    <td>${beanTypes.assigned_to}</td>
                                                                    <td>
                                                                        ${beanTypes.update_date_time}
                                                                    </td>


                                                                    <c:if test="${beanTypes.status=='Dead Lead'}">
                                                                        <td class="fontFourteen"> <span class="badge badge-pill deadLeadStatus font-size-11">${beanTypes.status}</span> </td>
                                                                    </c:if>  
                                                                    <c:if test="${beanTypes.status=='Not Answering'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill notAnsweringStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>  
                                                                        <c:if test="${beanTypes.status=='Pending'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill pendingStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>  
                                                                        <c:if test="${beanTypes.status=='Demo Required'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill demoStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>  
                                                                        <c:if test="${beanTypes.status=='Follow up Again'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill followUPStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanTypes.status=='Not Interested'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill notInterestedStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanTypes.status=='Purchased From Others'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill pfoStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanTypes.status=='Interested but buy later'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill intBuyLaterStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanTypes.status=='Closed'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill closedStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>                                                                        
                                                                        <c:if test="${beanTypes.status=='Reopen'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill reopenStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanTypes.status=='Success(Converted)'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill convertedStatus font-size-11">${beanTypes.status}</span></td>
                                                                        </c:if> 


                                                                    <td>${beanTypes.created_by}</td>
                                                                    <td>
                                                                        <button type="button" onclick="getremarkbyid(${beanTypes.enquiry_assigned_table_id},${beanTypes.revision_no},'${beanTypes.status}');" class="btn btn-primary btn-sm btn-rounded waves-effect waves-light" data-bs-toggle="modal" data-bs-target=".bs-example-modal-sm">
                                                                            Show Remark
                                                                        </button>
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
            </div>
            <!-- /.modal-content -->
        </div>
    </div>
    <!-- ADD Enquiry Model End -->







    <!-- Show Remark Modal Start -->
    <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm modal-dialog-centered">
            <div class="modal-content">
                <form action="EnquiryListController" method="post">
                    <!-- <div class="modal-header">
                        <h5 class="modal-title" id="mySmallModalLabel">Remark</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div> -->
                    <div class="modal-body" style="height: 250px;">
                        <!--<h6 style="padding-bottom: 5px;border-bottom: 1px solid #6a6868;display: initial;"><span class="text-muted">GNSS Admin</span> > <span class="text-info">Shalu Kansal</span></h6>-->
                        <p class="mb-0 mt-3"><div id="fullremark"></div>
                        <div id="fullremarkenquiryrevision"></div>
                        <div id="fullremarkenquiryid"></div>
                        <input type="hidden" name="enquiry_table_id" value="${enquiry_table_id}">
                        <input type="hidden" name="enquiry_status" id="enquiry_status" value="">
                        </p>
                        <input type="submit" name="task" value="Update Remark"  class="btn btn-primary" > 
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!-- Show Remark Modal End -->

    <jsp:include page="layout/footer.jsp" />

    <script src="assets/libs/bootstrap-editable/js/index.js"></script>
    <script src="assets/js/pages/form-xeditable.init.js"></script>  

    <script>
                                                                            function getremarkbyid(id, revision_no, status) {
                                                                                $('#enquiry_status').val(status);
                                                                                $.ajax({
                                                                                    url: "EnquiryListController",
                                                                                    data: "task=Getremarkbyid&id=" + id + "&revision_no=" + revision_no + "&status=" + status,
                                                                                            type: 'POST',
                                                                                    success: function (alldata) {
                                                                                        if (alldata != '') {
                                                                                            $('#fullremark').html('<textarea id="remarkpopup" name="remarkpopup" rows="4" cols="37">' + alldata + '</textarea>');
                                                                                            $('#fullremarkenquiryrevision').html('<input type="hidden" name="enquiryrevision"value="' + revision_no + '">');
                                                                                            $('#fullremarkenquiryid').html('<input type="hidden" name="enquiryassignid"value="' + id + '">');

                                                                                        } else {
                                                                                            $('#fullremark').html('No remark available.');
                                                                                        }
                                                                                    },
                                                                                    error: function (e) {
                                                                                        alert('Error:12212212112121 ' + e);
                                                                                    }
                                                                                });
                                                                            }


    </script>
    <script>
        $("#assignPerson").editable({prepend: "not selected",
            mode: "inline", inputclass: "form-select form-select-sm",
            source: [{value: 1, text: "Kundan"}, {value: 2, text: "Shalu"},
                {value: 3, text: "Komal"}, {value: 4, text: "Ankit"},
                {value: 5, text: "Sharad"}, {value: 6, text: "Chandan"}],
            display: function (t, e) {
                var n = $.grep(e, function (e) {
                    return e.value == t
                });
                n.length ? $(this).text(n[0].text).css("color", {"": "#98a6ad", 1: "#5fbeaa", 2: "#5d9cec"}[t]) : $(this).empty()
            }}), $("#inline-group").editable({showbuttons: !1, mode: "inline", inputclass: "form-select form-select-sm"})
    </script>  
    <script>
        window.onload = function ()
        {
            getAllExecutives();
        };

        function getAllExecutives() {
            debugger;
            var key_person_id = $('#key_person_id').val();
            $.ajax({
                url: "EnquiryController",
                data: "task=GetAllExecutive",
                type: 'POST',
                success: function (alldata) {
                    var keys = [];
                    var result = $.parseJSON(alldata);

                    if (result != '') {
                        $('#assign_to').empty();
                        $('#assign_to').append('<option value="">Assign to</option>');
                        for (var i = 0; i <= result.length; i++) {
                            keys.push(result[i]['key_person_name']);
                            if (key_person_id != result[i].key_person_id) {
                                $('#assign_to').append('<option value="' + result[i].key_person_id + '-' + result[i].key_person_name + '">' + result[i].key_person_name + '</option>');
                            }
                        }
                    } else {
                        $('#designation_id').html('');
                    }
                },
                error: function (e) {
                    alert('Error:12212212112121 ' + e);
                }
            });
        }

    </script>

    <script>
        $(document).ready(function () {
            // AJAX to fetch messages and update the chat room every few seconds
            function fetchMessages() {
                $.ajax({
                    url: "ChatServlet",
                    method: "GET",
                    dataType: "json",
                    success: function (data) {
                        // Update the chat room with the new messages
                        // Add your implementation here
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("Error: " + textStatus);
                    }
                });
            }

            setInterval(fetchMessages, 5000); // Fetch messages every 5 seconds
        });

        // Function to send new messages
        function sendChatMessage() {
            // Add your implementation here
            alert("Hello");
        }
    </script>