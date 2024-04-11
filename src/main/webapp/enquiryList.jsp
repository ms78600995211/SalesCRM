<%-- 
    Document   : enquiryList.jsp
    Created on : 3 Jul, 2023, 1:32:09 PM
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

            <c:if test="${not empty message}">
                <div class="msgWrap">
                    <c:if test="${msgbgcolor=='green'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="mdi mdi-check-all me-2"></i>
                            ${message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>  
                    </c:if>
                    <c:if test="${msgbgcolor=='red'}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="mdi mdi-check-all me-2"></i>
                            ${message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>  
                    </c:if>

                </div>
            </c:if>
            <div id="yourDivId" style="display: none">
                <div class="msgWrap" id="emailSuccess">
                    <div class="alert alert-dismissible fade show" role="alert" id="emailmessage">

                    </div>                
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                        <h4 class="mb-sm-0 font-size-18">Enquiry List
                            <!--<input type="button" onclick="callIndiamartSchedular()" class="btn btn-primary " name="refresh" value="Refresh">-->

                        </h4>
                        <div class="page-title-right">
                            <ol class="breadcrumb m-0">
                                <li class="breadcrumb-item"><a href="index.php">Dashboard</a></li>
                                <li class="breadcrumb-item active">Enquiry List
                                </li>
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
                                                                    <form class="custom-validation" action="EnquiryController" method="post" >
                                                                        <div class="row">                      
                        
                                                                            <div class="col-md-12">
                                                                                <div class="mb-3">
                                                                                    <label class="form-label">Enquiry Source:</label>
                                                                                    <select class="form-control" name="source_id">
                                                                                        <option selected disabled>---Select One---</option>
                        <c:forEach var="source" items="${requestScope['sourcelist']}"
                                   varStatus="loopCounter">
                            <option value="${source.enquiry_source_table_id}" >${source.enquiry_source}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-12">
                <div class="mb-3">
                    <label class="form-label">Status:</label>
                    <select class="form-control" name="status_id">
                        <option selected disabled>---Select One---</option>
                        <c:forEach var="status" items="${requestScope['statuslist']}"
                                   varStatus="loopCounter">
                            <option value="${status.enquiry_status_id}" >${status.status}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-12">
                <div class="mb-3">
                    <input type="submit" name="task" value="Search Result"  class="btn btn-primary w-100">
                    <button class="btn btn-primary w-100" type="submit" name=""><i class="bx bx-search font-size-16 align-middle me-2"></i> Search Result</button>
                </div>
            </div>
        </div>
    </form>
</div>                                        


<div class="col-md-12">
    <div class="mb-1">
       <button class="btn btn-danger w-100"><i class="far fa-file-pdf font-size-16 align-middle me-2"></i> PDF</button>
    </div>
</div>
<div class="col-md-12">
    <div class="mb-1">
       <button class="btn btn-success w-100"><i class="far fa-file-excel font-size-16 align-middle me-2"></i> Excel</button>
    </div>
</div>

<div class="col-md-12 mt-3">
  <div class="mb-1">
     <button class="btn btn-info w-100"><i class="far fa-file-excel font-size-16 align-middle me-2"></i> Quotation</button>
  </div>
</div>                                        


</div>
<div class="row justify-content-center mt-5">
<img src="assets/images/verification-img.png" alt="" class="img-fluid d-block">
</div>
</div>
</div>
</div>-->
                        <!-- end col-->


                        <div class="col-lg-12">
                            <div class="card">                                     
                                <div class="px-3">
                                    <div class="mt-2">
                                        <p class="text-muted mb-2">Search your result according to this fields.</p>
                                        <div class="row">
                                            <div class="col-md-1"> 
                                                <div class="btn-toolbar pb-1">
                                                    <div>
                                                        <div class="btn-group btn-group me-2 mb-2 mb-sm-0">
                                                            <button class="btn btn-primary btn-md dropdown-toggle" type="button" id="__BVID__692118___BV_dropdown__" data-bs-toggle="dropdown" aria-expanded="false">
                                                                <div class="btn-content"> More <i class="mdi mdi-dots-vertical ms-2"></i></div>
                                                            </button>
                                                            <ul class="dropdown-menu" aria-labelledby="__BVID__692118___BV_dropdown__" role="menu" style="">
                                                                <!--<li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" onclick="showAssignedPerson()"> Assigned To </a></li>-->
                                                                <c:choose>
                                                                    <c:when test="${role eq 'Sales Manager'}">
                                                                        <!--                                                                        <li role="presentation" class="">
                                                                                                                                                    <a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" onclick="clickOnMoreAction(1)">Assigned To</a>
                                                                                                                                                </li>-->
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <!-- Add code for other cases if needed -->
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <li role="presentation" class="">
                                                                    <a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" onclick="clickOnMoreAction(1)">Assigned To</a>
                                                                </li>
                                                                <!--<li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" data-bs-toggle="modal" data-bs-target="#composemodal" onclick="mailTo()"> Mail To</a></li>-->
                                                                <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" data-bs-target="#composemodal" onclick="clickOnMoreAction(2)"> Mail To</a></li>
                                                                <!--<li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" data-bs-toggle="modal" data-bs-target="#setReminder">Set Reminder</a></li>-->
                                                                <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" data-bs-target="#setReminder" onclick="clickOnMoreAction(3)">Set Reminder</a></li>
                                                                <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self"> Whatsapp</a></li>                                    
                                                                <li role="presentation" class=""><a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self"> SMS</a></li>   
                                                            </ul>
                                                        </div>                                       
                                                    </div>
                                                </div>
                                            </div>                             
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
                                                            <!--<a class="form-control btn btn-success" type="submit" disabled="false" href="javascript: void(0);" tardropdownget="_self" data-bs-toggle="modal" data-bs-target="#composemodal" onclick="mailTo()"><i class="mdi mdi-check"></i></a>-->
                                                            <button class="form-control btn btn-success" id="reminderfunid" type="submit" disabled tardropdownget="_self" data-bs-toggle="modal" data-bs-target="#setReminder" onclick="setReminderFun()"><i class="mdi mdi-check"></i></button>
                                                            <!--<a class="dropdown-item" disabled="false" href="javascript: void(0);" target="_self" data-bs-toggle="modal" data-bs-target="#setReminder" onclick="">Set Reminder</a>-->
                                                        </div>
                                                        <div class="ms-1">
                                                            <a class="form-control btn btn-danger" onclick="hideAssignedPersonReminder()" type="button"><i class="mdi mdi-close"></i></a>
                                                        </div>
                                                        <div id="allids"></div>
                                                        <input type="hidden" name="task" value="assign_in_bulk" />
                                                    </div>
                                                </div>

                                            </form>

                                            <!--                                            <form method="POST" id="dataForm" action="EnquiryController">
                                                                                            <div class="btn-group btn-group me-2 mb-2 mb-sm-0" id="assignedPersonMail">
                                            
                                                                                                <select class="form-control myClassForMoreHandle" name="assign_to_mail1"   id="assign_to_mail">
                                                                                                    <option> Mailed To</option>
                                                                                                </select>
                                                                                                <div class="ms-2">
                                                                                                    <button class="form-control btn btn-success" type="submit" onclick=""><i class="mdi mdi-check"></i></button>
                                                                                                    <a class="form-control btn btn-success" type="submit" disabled="false" href="javascript: void(0);" tardropdownget="_self" data-bs-toggle="modal" data-bs-target="#composemodal" onclick="mailTo()"><i class="mdi mdi-check"></i></a>
                                                                                                </div>
                                                                                                <div class="ms-1">
                                                                                                    <a class="form-control btn btn-danger" onclick="hideAssignedPersonMail()" type="button"><i class="mdi mdi-close"></i></a>
                                                                                                </div>
                                                                                                <div id="allids"></div>
                                                                                                <input type="hidden" name="task" value="assign_in_bulk" />
                                                                                            </div>
                                                                                        </form>-->

                                        </div> 
                                    </div>
                                </div>


                                <div class="card-body pt-0">
                                    <div class="mt-1">
                                        <div class="table-responsive mt-2">
                                            <div class="row">
                                                <div class="col-12" id="hiddenfieldforreminde">
                                                    <table id="datatable" class="table table-bordered" nowrap class="w-100">
                                                        <thead>
                                                            <tr>                                                                
                                                                <th>
                                                                    <div class="form-check font-size-16">
                                                                        <input class="form-check-input enable-checkbox" type="checkbox" id="showCheckoutAll" disabled>
                                                                        <label class="form-check-label" for="checkAll"></label>All
                                                                    </div>  

                                                                </th>
                                                                <!--                                                                <th>
                                                                                                                                    <div class="form-check font-size-16">
                                                                                                                                        <input class="form-check-input" type="checkbox" id="showCheckoutMail">
                                                                                                                                        <label class="form-check-label" for="checkAll"></label>Mail
                                                                                                                                    </div>  
                                                                
                                                                                                                                </th>-->
                                                                <th>Sr.</th>
                                                                <th>Sender Name</th>
                                                                <th>Mobile No.</th>
                                                                <th>Product</th>
                                                                <th>Location</th>
                                                                <th>Assigned</th>
                                                                <th>Status</th>
                                                                <th style="min-width:80px"></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="myBody">
                                                            <c:forEach var="beanType" items="${requestScope['list']}" varStatus="loopCounter">
                                                                <tr>
                                                                    <td style="width: 20px;" class="align-middle">
                                                                        <div class="form-check font-size-16">


                                                                            <!--<input class="form-check-input checkBoxList" type="checkbox">-->
                                                                            <!--<input class="form-check-input checkBoxList" type="checkbox" name="checkdata" value="${beanType.enquiry_table_id}" id="checkAll">-->


                                                                            <input class="form-check-input checkBoxList enable-checkbox checkboxsel${beanType.enquiry_table_id}" type="checkbox" name="checkdata" value="${beanType.enquiry_table_id}" emailValue="${beanType.sender_email}" sender_name="${beanType.sender_name}" id="checkAll" onchange="validateData('${beanType.enquiry_table_id}', this)" disabled>
                                                                            <!--<label class="form-check-label" for="checkAll"></label>-->

                                                                            <c:choose>
                                                                                <c:when test="${beanType.importantEnquiry eq 'Y'}">
                                                                                    <!--<input class="checkbox-important" type="checkbox" id="${beanType.enquiry_table_id}" name="" onclick="setImportant(${beanType.enquiry_table_id})" checked>-->
                                                                                    <link href="assets/css/myStyle.css" rel="stylesheet" type="text/css"/>
                                                                                    <span class="text-warning" id="star${beanType.enquiry_table_id}" onclick="setImportant(${beanType.enquiry_table_id})">
                                                                                        <i class="fa-solid fa-star" id="icon${beanType.enquiry_table_id}"></i></span>

                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <!--<input class="checkbox-important" type="checkbox" id="${beanType.enquiry_table_id}" name="" onclick="setImportant(${beanType.enquiry_table_id})">-->
                                                                                    <span id="star${beanType.enquiry_table_id}" 
                                                                                          onclick="setImportant(${beanType.enquiry_table_id})">
                                                                                        <i class="fa-regular fa-star" id="icon${beanType.enquiry_table_id}"></i></span>
                                                                                    </c:otherwise>
                                                                                </c:choose>

                                                                        </div>
                                                                    </td>
                                                                    <!--                                                                    <td style="width: 20px;" class="align-middle">
                                                                                                                                            <div class="form-check font-size-16">
                                                                                                                                                <input class="form-check-input checkBoxListMail" type="checkbox" id="${beanType.enquiry_table_id}" value="${beanType.sender_email}" onchange="validateData('${beanType.enquiry_table_id}')">
                                                                                                                                                <label class="form-check-label" for="checkAll"></label>
                                                                                                                                            </div>
                                                                                                                                        </td>-->
                                                                    <td class="fontFourteen">${loopCounter.count}</td>                                                                    
                                                                    <td class="fontFourteen">
                                                                        <p class="mb-0 noWrap">${beanType.sender_name}</p>
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

                                                                    <td class="fontFourteen">
                                                                        <small class="text-muted">${beanType.assigned_to}</small>
                                                                    </td>                                                                                                                                   

                                                                    <c:if test="${beanType.status=='Dead Lead'}">
                                                                        <td class="fontFourteen"> <span class="badge badge-pill deadLeadStatus font-size-11">${beanType.status}</span> </td>
                                                                    </c:if>  
                                                                    <c:if test="${beanType.status=='Not Answering'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill notAnsweringStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>  
                                                                        <c:if test="${beanType.status=='Pending'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill pendingStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>  
                                                                        <c:if test="${beanType.status=='Demo Required'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill demoStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>  
                                                                        <c:if test="${beanType.status=='Follow up Again'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill followUPStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanType.status=='Not Interested'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill notInterestedStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanType.status=='Purchased From Others'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill pfoStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanType.status=='Interested but buy later'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill intBuyLaterStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanType.status=='Closed'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill closedStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>                                                                        
                                                                        <c:if test="${beanType.status=='Reopen'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill reopenStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if>
                                                                        <c:if test="${beanType.status=='Success(Converted)'}">
                                                                        <td class="fontFourteen"><span class="badge badge-pill convertedStatus font-size-11">${beanType.status}</span></td>
                                                                        </c:if> 

                                                                    <td>
                                                                        <a href="#" data-bs-toggle="modal"  onclick="getDataForEdit(${beanType.enquiry_table_id})"><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
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







    <!-- Reminder Modal -->
    <div class="modal fade" id="setReminder" tabindex="-1" role="dialog" aria-labelledby="composemodalTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">            
                <div class="modal-header">
                    <h5 class="modal-title" id="composemodalTitle">Set Reminder for</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form method="post" id="form1">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label>Date</label>
                                <input type="date" class="form-control" placeholder="To" id="date">
                                <span class="error-message" id="textInputErrorDate" style="color: red"></span>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label>Time</label>
                                <input type="time" class="form-control" placeholder="To" id="time">
                                <span class="error-message" id="textInputErrorTime" style="color: red"></span>
                            </div>                       
                            <div class="col-md-12 mb-3">
                                <button type="submit" class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#setReminder" id ="setreminderid">Set Reminder <i class="fab fa-telegram-plane ms-1"></i></button>
                                <!--<button type="submit" class="btn btn-primary w-100" onclick="setDateTime(this)">Set Reminder <i class="fab fa-telegram-plane ms-1"></i></button>-->
                            </div>
                        </div>
                    </form>

                    <!-- <hr style="border:1px solid #999;margin: 0;"> -->
                    <div class="row mt-2">
                        <div class="col-md-12">
                            <p class="font-size-12 mb-0 py-1">Recent Reminders </p>
                        </div>
                    </div>
                    <hr style="border:0.15px solid #ccc;margin: 0;">

                    <div>
                        <c:forEach var="reminder" items="${requestScope['allRemindersDetails']}"
                                   varStatus="loopCounter">
                            <div class="mt-2">
                                <p class="mb-0">Follow-Up with ${reminder.sender_name} Again</p>
                                <p class="mb-2"><span style="font-size:10px;color: #19c3ad;">${reminder.date}</span>  &nbsp&nbsp&nbsp&nbsp <span style="font-size:10px;color: #19c3ad;">${reminder.time}</span></p>
                            </div>
                            <hr style="border:0.05px solid #ebe9e9;margin: 0;">
                        </c:forEach> 
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>                    
                </div> 
            </div>
        </div>
    </div>
    <!-- end modal -->








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
                        <form  action="EnquiryController" name="addenq" method="post" >
                            <div class="row">                      
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Name:<span class="text-danger">*</span> </label>
                                        <input type="text" class="form-control" name="sender_name"
                                               placeholder="Name" required>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Mobile:<span class="text-danger">*</span></label>
                                        <input type="text" class="form-control"  onkeypress="return (event.charCode != 8 && event.charCode == 0 || (event.charCode >= 48 && event.charCode <= 57))"
                                               name="sender_mob"
                                               placeholder="Mobile" >
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Other Mobile:<span class="text-danger"></span></label>
                                        <input type="text" class="form-control" onkeypress="return (event.charCode != 8 && event.charCode == 0 || (event.charCode >= 48 && event.charCode <= 57))"
                                               maxlength="10" minlength="10" name="other_mobile"
                                               placeholder="Mobile" >
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Email<span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" name="sender_email"
                                               placeholder="email" >
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Other Email<span class="text-danger"></span></label>
                                        <input type="text" class="form-control" name="other_email"
                                               placeholder="email" >
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Enquiry Source:<span class="text-danger">*</span></label>
                                        <select name="enquiry_source_table_id" id="enquiry_source_table_id" class="form-control" required="required">
                                            <option value="">Select Source</option>
                                            <c:forEach var="source" items="${requestScope['sourcelist']}"
                                                       varStatus="loopCounter">
                                                <option value="${source.enquiry_source_table_id}" >${source.enquiry_source}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>    
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Address:<span class="text-danger">*</span></label>
                                        <textarea type="text" class="form-control" name="enquiry_address" placeholder="Address" required></textarea>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Enquiry Message:<span class="text-danger">*</span></label>
                                        <textarea class="form-control" name="enquiry_message" placeholder="Enquiry Message" required></textarea>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="" class="form-label">Marketing Vertical:<span class="text-danger">*</span></label>
                                        <select name="marketing_vertical_id" id="marketing_vertical_id" class="form-control" required="required">
                                            <option value="">Select Vertical</option>
                                            <c:forEach var="vertical" items="${requestScope['verticallist']}"
                                                       varStatus="loopCounter">
                                                <option value="${vertical.marketing_vertical_id}" >${vertical.marketing_vertical_name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div> 

                                <a href="#" class="show_hide mb-2" data-content="toggle-text">Add More... </a> 
                                <div class="contentHideShow">
                                    <div>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Product Name</label>
                                                    <input type="text" class="form-control" name="product_name"
                                                           placeholder="Product Name" required>
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Sender Company Name</label>
                                                    <input type="text" class="form-control" name="sender_company_name"
                                                           placeholder="Sender Company Name" >
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">City</label>
                                                    <input type="text" name="city" id="city"  required placeholder="Enter City Name" class="form-control" value="">
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="mb-3">
                                                    <label class="form-label">State</label>
                                                    <input type="text" name="state" id="state" required  placeholder="Enter State Name" class="form-control" value="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary waves-effect" data-bs-dismiss="modal">Close</button>
                                <!--<button class="btn btn-primary" type="submit" name="submitFormBtn">Submit form</button>-->
                                <input type="submit" name="task" id="" class="btn btn-primary" value="Submit form">
                            </div>
                        </form>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div>
    </div>
</div>
<!-- ADD Enquiry Model End -->




<!-- Edit Enquiry Model Start -->

<div class="modal fade bs-example-modal-lg-Edit-Enquery" id="edit_popup" tabindex="-1" role="dialog" aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl" >
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="myExtraLargeModalLabel">Enquiry Edit</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div>
                    <form class="custom-validation" id="addenq" action="EnquiryController" method="post" >
                        <input type="hidden" name="enquiry_table_id">
                        <div class="row">                      
                            <div class="col-md-3">
                                <div class="mb-3">
                                    <label for="" class="form-label">Name:<span class="text-danger">*</span> </label>
                                    <input type="text" class="form-control" name="sender_name"
                                           placeholder="Name" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="mb-3">
                                    <label for="" class="form-label">Mobile:<span class="text-danger"></span></label>
                                    <input type="text" class="form-control" onkeypress="return (event.charCode != 8 && event.charCode == 0 || (event.charCode >= 48 && event.charCode <= 57))"
                                           name="sender_mob"
                                           placeholder="Mobile" >
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="mb-3">
                                    <label for="" class="form-label">Other Mobile:<span class="text-danger"></span></label>
                                    <input type="text" class="form-control"  onkeypress="return (event.charCode != 8 && event.charCode == 0 || (event.charCode >= 48 && event.charCode <= 57))" 
                                           maxlength="10" minlength="10" name="other_mobile"
                                           placeholder="Mobile" >
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="mb-3">
                                    <label for="" class="form-label">Email:<span class="text-danger"></span></label>
                                    <input type="text" class="form-control" name="sender_email"
                                           placeholder="Email" >
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="mb-3">
                                    <label for="" class="form-label">Other Email<span class="text-danger"></span></label>
                                    <input type="text" class="form-control" name="other_email"
                                           placeholder="email" >
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="mb-3">
                                    <label for="" class="form-label">Enquiry Source:<span class="text-danger">*</span></label>
                                    <!--                                    <select name="enquiry_source_table_id" readonly id="enquiry_source_table_id" class="form-control" required="required">
                                                                            <option>Select Source</option>
                                    
                                                                        </select>-->
                                    <input type="text" readonly class="form-control" name="enquiry_source_table_id" >
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="" class="form-label">Address:<span class="text-danger">*</span></label>
                                    <textarea type="text" class="form-control" name="enquiry_address"
                                              placeholder="Address" required ></textarea>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="" class="form-label">Enquiry Message:<span class="text-danger">*</span></label>
                                    <textarea class="form-control" placeholder="Enquiry Message" name="enquiry_message" required></textarea>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="mb-3">
                                    <label for="" class="form-label">Marketing Vertical:<span class="text-danger">*</span></label>
                                    <!--                                    <select name="marketing_vertical_id" readonly id="marketing_vertical_id" class="form-control" required="required">
                                                                            <option>Select Vertical</option>
                                    
                                                                        </select>-->
                                    <input type="text" class="form-control" readonly name="marketing_vertical_id" >
                                </div>
                            </div> 
                            <a href="#" class="show_hide mb-2" data-content="toggle-text">Add More... </a> 
                            <div class="contentHideShow">
                                <div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="mb-3">
                                                <label class="form-label">Enquiry No</label>
                                                <input type="text" class="form-control" readonly name="enquiry_no" required placeholder="Enquiry No" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="mb-3">
                                                <label class="form-label">Product Name</label>
                                                <input type="text" class="form-control" name="product_name" placeholder="Product Name" required>
                                            </div>
                                        </div>

                                        <div class="col-md-3">
                                            <div class="mb-3">
                                                <label class="form-label">Sender Company Name</label>
                                                <input type="text" class="form-control" name="sender_company_name"
                                                       placeholder="Sender Company Name" >
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="mb-3">
                                                <label class="form-label">City</label>
                                                <input type="text" class="form-control" id="city" required name="enquiry_city" value="">
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="mb-3">
                                                <label class="form-label">State</label>
                                                <input type="text" class="form-control" id="state" required name="enquiry_state" value="">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="modal-footer">
                            <!--                            <button type="button" class="btn btn-secondary waves-effect" data-bs-dismiss="modal">Close</button>
                                                        <button class="btn btn-primary" type="submit" name="submitFormBtn">Submit form</button>-->

                            <input type="submit" name="task" id="" class="btn btn-primary" value="Update">

                        </div>
                    </form>
                </div>
            </div>
        </div> 
    </div>
</div>

<!-- Edit Enquiry Model End -->






<!-- Modal -->
<div class="modal fade" id="composemodal" tabindex="-1" role="dialog" aria-labelledby="composemodalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">

            <form method="post" action="#" enctype="multipart/form-data">
                <div class="modal-header">
                    <h5 class="modal-title" id="composemodalTitle">New Message</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div>
                        <div class="mb-3">
                            <span><strong>To</strong></span>
                            <!--<input type="email" class="form-control" placeholder="To">-->
                            <select name="to" multiple class="form-control" placeholder="To" id="containerId">
                                <!--<option  selected disabled>------------------Select To Mail-----------------</option>-->
                            </select>

                        </div>

                        <div class="mb-3">
                            <!--<input type="text" class="form-control" placeholder="Subject">-->
                            <span><strong>Subject</strong></span>
                            <select id="subject" class="form-control">
                                <option  selected disabled>Select Subject</option>
                                <c:forEach var="source" items="${requestScope['subjectlist']}"
                                           varStatus="loopCounter">
                                    <option value="${source.subject_name}" id="${source.subject_id}" >${source.subject_name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <span><strong>Add Files</strong></span>
                            <select class="form-control" id="brochure_catalog" name="District" multiple="multiple">

                            </select>
                        </div>
                        <!-- <div class="mb-3">
                            <input type="file" class="form-control" placeholder="Subject" multiple>
                        </div> -->
                        <div class="mb-3">
                            <span><strong>Body</strong></span>
                            <textarea id="email-editor" class="form-control" rows="6"  name="area"></textarea>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#composemodal" id ="myButton" onclick="sendMail()">Send <i class="fab fa-telegram-plane ms-1"></i></button>
                </div>
            </form>

        </div>
    </div>
</div>
<!-- end modal -->

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


<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>

<script>

                        function submitform() {
                            var checkboxes = document.getElementsByName('checkdata');
                            var selectedIDs = [];
                            for (var i = 0; i < checkboxes.length; i++) {
                                if (checkboxes[i].checked) {
//                    alert(checkboxes[i].value);
                                    selectedIDs.push(checkboxes[i].value);
                                }
                            }

                            $('#allids').append('<input type="hidden" name="tottal_ids" value="' + selectedIDs + '">');
                        }
</script>
<script>

    $(document).ready(function () {
        $("#addenq").validate({
            rules: {
                sender_name: {
                    required: true,
                },
                other_email: {

                    email: true,
                },

                other_mobile: {

                    minlength: 10,
                    maxlength: 10,

                }
            },
            messages: {
                sender_name: {
                    sender_name: "Please enter name",

                },
                other_email: {

                    email: "Please Enter Valid Email.", //add an email rule that will ensure the value entered is valid email id.
                },

                other_mobile: {
                    minlength: "Please enter minimum 10 digits",
                    maxlength: "Please enter Maximum 10 digits",

                },
            }
        });
    });
</script>
<script>
//    $(document).ready(function () {
//        $('.js-example-basic-single').select2();
//    });
</script>

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
//        jQuery('#showCheckoutMail').change(function () {
//            if ($(this).prop('checked')) {
//                // let myArray = (".checkBoxList").val();
//                $(".checkBoxListMail").attr("checked", "checked", );
//            } else {
//                $(".checkBoxListMail").removeAttr("checked", "checked", );
//            }
//        });
    });</script>

<script>
//    $("#assignedPerson").hide();
//    $("#assignedPersonMail").hide();

    function clickOnMoreAction(id) {

//        $('.checkboxsel' + enquiry_table_id).prop('checked', false);
        $(".enable-checkbox").removeAttr("disabled");
        if (id == 1) {

            $(".checkBoxList").prop("checked", false);
            $(".hiddenInputClass").remove();
            $("#assignedPersonMail").hide();
            $("#setReminderId").hide();
//            $("#assignedPerson").show();
//            $("#assignedPersonMail").attr("custom-assign-attr", "assigncheck");
            document.getElementById("assignedPerson").style.display = 'inline';
//            debugger;
            var checkboxes = document.getElementsByName('checkdata');
            var selectedIDs = [];
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked) {
//                    alert(checkboxes[i].value);
                    selectedIDs.push(checkboxes[i].value);
                }
            }
        }

        if (id == 2) {
            $(".checkBoxList").prop("checked", false);
            $(".hiddenInputClass").remove();
            $("#assignedPerson").hide();
            $("#setReminderId").hide();
//            $("#assignedPersonMail").show();
//            $("#assignedPersonMail").attr("custom-mail-attr", "mailcheck");
            document.getElementById("assignedPersonMail").style.display = 'inline';
//            debugger;
            var checkboxes = document.getElementsByName('checkdata');
            var selectedIDs = [];
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked) {
//                    alert(checkboxes[i].value);
                    selectedIDs.push(checkboxes[i].value);
                }
            }
        }

        if (id == 3) {
            $(".checkBoxList").prop("checked", false);
            $("#assignedPerson").hide();
            $("#assignedPersonMail").hide();
//            $("#assignedPersonMail").show();
//            $("#assignedPersonMail").attr("custom-mail-attr", "mailcheck");
            document.getElementById("setReminderId").style.display = 'inline';
//            debugger;
            var checkboxes = document.getElementsByName('checkdata');
            var selectedIDs = [];
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked) {
//                    alert(checkboxes[i].value);
                    selectedIDs.push(checkboxes[i].value);
                }
            }

            var newInput = $("<input>").attr({
                type: "hidden",
                id: "hiddenInputId",
                class: "hiddenInputClass",
                name: "forReminder",
                placeholder: "Enter something",
                value: ""
            });
            // Append the new input element to the form
            $("#hiddenfieldforreminde").append(newInput);
        }

        getAllExecutives(selectedIDs);
    }

//    $("#assignedPersonMail").hide();
//    function showEmail() {
//        $("#assignedPerson").hide();
////        alert("kundan");
//        $("#assignedPersonMail").show();
//        debugger;
////        document.getElementById("checkAll").disabled = false;
//        var checkboxes = document.getElementsByName('checkdata');
//        var selectedIDs = [];
//        for (var i = 0; i < checkboxes.length; i++) {
//            if (checkboxes[i].checked) {
////                alert(checkboxes[i].value);
//                selectedIDs.push(checkboxes[i].value);
//            }
//        }
//        getAllExecutives(selectedIDs);
//    }

    function hideAssignedPerson() {
        $("#assignedPerson").hide();
    }
    function hideAssignedPersonMail() {
        $("#assignedPersonMail").hide();
    }
    function hideAssignedPersonReminder() {
        $("#setReminderId").hide();
    }
</script>

<script>

    function getAllExecutives(selectedIDs) {
        $.ajax({
            url: "EnquiryController",
            data: "task=GetAllExecutive",
            type: 'POST',
            success: function (alldata) {
                var keys = [];
                var result = $.parseJSON(alldata);
                if (result != '') {
                    $('#assign_to').empty();

//                    $('#allids').append('<input type="text" name="tottal_ids" value="' + selectedIDs + '">');

//                    $('#allids').append('<input type="hidden" name="tottal_ids" value="' + selectedIDs + '">');

                    $('#assign_to').append('<option value="">Assign to</option>');
                    for (var i = 0; i <= result.length; i++) {
                        keys.push(result[i]['key_person_name']);
                        $('#assign_to').append('<option value="' + result[i].key_person_id + '-' + result[i].key_person_name + '">' + result[i].key_person_name + '</option>');
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
    function getDataForEdit(id) {
        // alert(id);
        $.ajax({
            url: "EnquiryController",
            data: "task=GetEnquiryDataById&id=" + id,
            type: 'POST',
            success: function (alldata) {
                var result = $.parseJSON(alldata);
                //alert(alldata);
                if (result.length > 0) {
                    var data = result[0];
                    $('#edit_popup').find('[name="enquiry_table_id"]').val(id);
                    $('#edit_popup').find('[name="sender_name"]').val(data.sender_name);
                    if (data.sender_name !== null && data.sender_name !== '') {

                        $('#edit_popup').find('[name="sender_name"]').prop('readonly', true);
                    } else {

                        $('#edit_popup').find('[name="sender_name"]').prop('readonly', false);
                    }
                    $('#edit_popup').find('[name="sender_mob"]').val(data.sender_mob);
                    if (data.sender_mob !== null && data.sender_mob !== '') {

                        $('#edit_popup').find('[name="sender_mob"]').prop('readonly', true);
                    } else {

                        $('#edit_popup').find('[name="sender_mob"]').prop('readonly', false);
                    }
                    $('#edit_popup').find('[name="other_mobile"]').val(data.sender_alternate_mob);
                    $('#edit_popup').find('[name="sender_email"]').val(data.sender_email);
                    if (data.sender_email !== null && data.sender_email !== '') {

                        $('#edit_popup').find('[name="sender_email"]').prop('readonly', true);
                    } else {

                        $('#edit_popup').find('[name="sender_email"]').prop('readonly', false);
                    }
                    $('#edit_popup').find('[name="other_email"]').val(data.sender_alternate_email);
                    $('#edit_popup').find('[name="enquiry_source_table_id"]').val(data.enquiry_source);
                    $('#edit_popup').find('[name="enquiry_address"]').val(data.enquiry_address);

                    var decodedMessage = $("<div/>").html(data.enquiry_message).text();
                    $('#edit_popup').find('[name="enquiry_message"]').val(decodedMessage);

                    $('#edit_popup').find('[name="marketing_vertical_id"]').val(data.marketing_vertical_name);
                    $('#edit_popup').find('[name="enquiry_no"]').val(data.enquiry_no);
                    $('#edit_popup').find('[name="product_name"]').val(data.product_name);
                    $('#edit_popup').find('[name="sender_company_name"]').val(data.sender_company_name);
                    $('#edit_popup').find('[name="enquiry_city"]').val(data.enquiry_city);
                    $('#edit_popup').find('[name="enquiry_state"]').val(data.enquiry_state);
                    $('.bs-example-modal-lg-Edit-Enquery').modal('show');
                } else {

                }
            },
            error: function (e) {
                alert('Error:12212212112121 ' + e);
            }})
    }
</script>

<script>
    var subjectId = "";
    var selectedValue = "";
    $(document).ready(function () {
        $('#subject').on('change', function () {
            // Get the selected option value
            selectedValue = $(this).val();
            // Get the selected option ID
            subjectId = $(this).find('option[value="' + selectedValue + '"]').attr('id');
            console.log('Selected Option ID: ' + subjectId);
        });
    });
    function sendMail() {
        var toListIds = [];
        $(".toIds:selected").each(function () {
            var toId = $(this).attr('id');
            toListIds.push({"to_id": toId}); // Create an object with the ID
        });
        if (toListIds.length !== 0) {
            var body = $('#email-editor').val();
//        var ccListIds = [];
//        var bccListIds = [];
            fileData = []
            $(".docDetails:selected").each(function () {
                var document_id = $(this).attr('id');
                var path = $(this).val();
                fileData.push({"document_id": document_id, "path": path}); // Create an object with the ID
            });
            if (subjectId != "" && (body != "" || fileData.length > 0)) {
                var requestData = {
                    "to": toListIds,
                    "subjectId": subjectId,
                    "subjectName": selectedValue,
                    "body": body,
//            "cc": ccListIds,
//            "bcc": bccListIds,
                    "file_data": fileData,
                };
                $.ajax({
                    url: "http://localhost:8080/CModule/sendMail", // Replace with your API endpoint URL
//                    url: "http://120.138.10.251:8080/CModule/sendMail",
                    type: "POST", // Specify the HTTP method (POST, GET, etc.)
                    contentType: "application/json", // Specify the request content type
                    data: JSON.stringify(requestData), // Convert the payload to JSON string
                    success: function (response) {
//                        alert(response);
                        $("#myButton").attr("data-bs-dismiss", "modal");
                        $("#emailmessage").addClass("alert-success");
                        $("#emailmessage").html('<i class="mdi mdi-check-all me-2"></i>' + "email sent successfully!" + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>');
                        $("#yourDivId").show();
                    },
                    error: function (xhr, status, error) {
                        console.log("API call failed: " + error);
                        $("#myButton").attr("data-bs-dismiss", "modal");
                        $("#emailmessage").addClass("alert-danger");
                        $("#emailmessage").html('<i class="mdi mdi-check-all me-2"></i>' + "email not sent!" + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>');
                        $("#yourDivId").show();
                    }
                });
            } else {
                alert("please select Subject and (body or document)");
            }
        } else {
            alert("please select any Email!")
        }
    }

    function mailTo() {
        $('#containerId').empty();
        var checkedValues = [];
        var checkedIds = [];
        var checkedSenderName = [];
        $('input[type="checkbox"]:checked').each(function () {
            if ($(this).val() != "on") {
                checkedIds.push($(this).val());
                checkedValues.push($(this).attr('emailValue'));
                checkedSenderName.push($(this).attr('sender_name'));
            }
        });
        if (checkedValues.length > 0 && checkedIds.length > 0) {
//            $('#containerId').append('<option selected disabled>-------------Select To Mail---------------</option>');
            for (var i = 0; i < checkedValues.length; i++) {
                if (checkedValues[i] != '') {
                    var newInput = '<option id="' + checkedIds[i] + '" class="toIds" selected disabled>' + checkedValues[i] + '</option>';
                    $('#containerId').append(newInput);
                }
            }
        } else {
            $('#containerId').append('<option selected disabled>-------------To Mail Not Found-------------</option>');
        }
    }


    $(function () {
        $('#brochure_catalog').empty();
        var document;
        var documents_id = [];
        var path = [];
        var filename = [];
        var getFileNameFromField = [];
        $.ajax({
            type: "POST",
            url: 'EnquiryController?task1=getDocumentDetails',
            dataType: 'json',
            success: function (data) {
                console.log(data);
                document = data.list;
                if (document.length > 0) {
//                    $('#brochure_catalog').append('<option selected disabled>---Select Brochure & Catalog---</option>');
                    for (var i = 0; i < document.length; i++) {
                        documents_id[i] = document[i]["documents_id"];
                        path[i] = document[i]["path"];
                        filename[i] = document[i]["filename"];
                        getFileNameFromField[i] = document[i]["getFileNameFromField"];
                        if (getFileNameFromField[i] != '') {
                            var newInput1 = '<option id="' + documents_id[i] + '" value="' + path[i] + '" class="docDetails">' + getFileNameFromField[i] + '</option>';
                            $('#brochure_catalog').append(newInput1);
                        }
                    }
                } else {
                    $('#brochure_catalog').append('<option selected disabled>---Brochure & Catalog Data Not Found---</option>');
                }

            }

        });
    });
    function validateData(enquiry_table_id, el) {
        if ($("#hiddenInputId").length) {
//            el.checked = true;
            var isChecked = $(".checkboxsel" + enquiry_table_id).prop("checked");
//
            if (isChecked) {
                $(".checkBoxList").prop("checked", false);
                el.checked = true;
                $("#reminderfunid").removeAttr("disabled");
            } else {
                $("#reminderfunid").attr("disabled", "disabled");
                el.checked = false;
            }

//            el.checked = true;

//            $("#reminderfunid").removeAttr("disabled");
        } else {

            $(".hiddenInputClass").remove();
        }

//        var customAttribute = $("#assignedPersonMail").attr("custom-mail-attr");
//        alert(customAttribute);
        if ($("#assignedPersonMail").css('display') == 'inline') {
//        if (customAttribute == "mailcheck") {
            var email = $('#sender_email' + enquiry_table_id).html();
            if (email == "")
            {
                alert("mail is not found for this user..");
                $('.checkboxsel' + enquiry_table_id).prop('checked', false);
                return false;
            } else {
                return true;
            }
        }

//        }
    }



</script>


<script>
    $('#datatable').DataTable({
        "ordering": false
    });</script>

<script>
    function setReminderFun() {
////        alert("Hello");
    }



    $("#form1").on("submit", function (e) {
//    function setDateTime() {
        e.preventDefault();
        $("#yourDivId").hide();
        $("#emailmessage").removeClass("alert-success");
        $("#emailmessage").removeClass("alert-danger");
        var date = $('#date').val();
        var time = $('#time').val();
        document.querySelectorAll(".error-message").forEach(function (errorElement) {
            errorElement.textContent = "";
        });
        if (date.trim() === "") {
            alert("please select Date & Time!");
            e.preventDefault();
            document.getElementById("textInputErrorDate").textContent = "Text field is required.";
        } else if (time.trim() === "") {
            alert("please select Date & Time!");
            e.preventDefault();
            document.getElementById("textInputErrorTime").textContent = "Text field is required.";
        } else {
//            alert("else");
            var checkedIdsList = [];
            $('input[type="checkbox"]:checked').each(function () {
                if ($(this).val() != "on") {
                    checkedIdsList.push($(this).val());
                }
            });
            var requestData = {
                date: date,
                time: time,
                checkedIdsList: checkedIdsList
            };
            $.ajax({
                type: "POST",
                url: 'EnquiryController?task1=setReminder',
                data: JSON.stringify(requestData),
                contentType: "application/json",
                dataType: 'json',
                success: function (response) {
//                    alert(response.msg);
                    var msg = response.msg;
                    if (msg.trim() === "Reminder Set Successfully!") {
                        $("#setreminderid").attr("data-bs-dismiss", "modal");
                        $("#emailmessage").addClass("alert-success");
                        $("#emailmessage").html('<i class="mdi mdi-check-all me-2"></i>' + "Reminder Set!" + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>');
                        $("#yourDivId").show();
                        timeout();
                    } else {
                        $("#setreminderid").attr("data-bs-dismiss", "modal");
                        $("#emailmessage").addClass("alert-danger");
                        $("#emailmessage").html('<i class="mdi mdi-check-all me-2"></i>' + "Reminder Not Set!" + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>');
                        $("#yourDivId").show();
                        timeout();
                    }
                },
                error: function (xhr, status, error) {
//                    alert("Error");
                    console.log("API call failed: " + error);
                    $("#setreminderid").attr("data-bs-dismiss", "modal");
                    $("#emailmessage").addClass("alert-danger");
                    $("#emailmessage").html('<i class="mdi mdi-check-all me-2"></i>' + "Reminder Not Set!" + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>');
                    $("#yourDivId").show();
                }

            });
        }
    });
//    }
</script>

<script>
    function setImportant(id) {
        $("#yourDivId").hide();
        $("#emailmessage").removeClass("alert-success");
        $("#emailmessage").removeClass("alert-danger");
        $.ajax({
            url: "EnquiryController",
            data: "task1=setImportant&setImportantId=" + id,
            type: 'POST',
            dataType: 'json',
            success: function (response) {

//                debugger;
                var dataValue = response.data;
//                alert(dataValue);
                if (dataValue == "Important marked") {
                    $('#star' + id).addClass('text-warning');
                    $('#icon' + id).removeClass('fa-regular');
                    $('#icon' + id).addClass('fa-solid');
                    $("#emailmessage").addClass("alert-success");
                    $("#emailmessage").html('<i class="mdi mdi-check-all me-2"></i>' + dataValue + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>');
                    $("#yourDivId").show();
                    timeout();
                } else {
                    $("#emailmessage").addClass("alert-danger");
                    $("#emailmessage").html('<i class="mdi mdi-check-all me-2"></i>' + dataValue + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>');
                    $("#yourDivId").show();
                    if ($('#star' + id).hasClass('text-warning')) {
                        $('#star' + id).removeClass('text-warning');
                    }
                    if ($('#icon' + id).hasClass('fa-solid')) {
                        $('#icon' + id).removeClass('fa-solid');
                        $('#icon' + id).addClass('fa-regular');
//                        $('#icon' + id).addClass('fa-star');
                    }
                    timeout();
                }
            },
            error: function (e) {
                alert('Error:12212212112121 ' + e);
            }
        });
    }


    $(document).ready(function () {
//        BindItemTable();
//        PopulateItemsTable();
    });
//    function BindItemTable() {
//        myTable = $("#datatable").DataTable({
//            "ordering": false
//        });
//    }

    function PopulateItemsTable() {
        debugger;
        var dataTable = $('#datatable').DataTable();
//        dataTable.clear().draw();
//        dataTable.draw();
        $.ajax({
            type: "POST",
            url: "EnquiryController",
            data: "task1=testingTask",
            dataType: "json",
            success: function (response) {
                alert(response.data);
                alert("data len -" + response.data.length);
                var j = 0;
                $("#myBody").cleanData();
                for (var i = 0; i < 20; i++) {
                    $("#myBody").append('<tr><td class="fontFourteen">i</td><td class="fontFourteen">i<br>i</td><td class="fontFourteen" >i<br><p class="mb-0" id="i">i</p></td><td class="fontFourteen">i</td><td class="fontFourteen">i<br>i</td><td class="fontFourteen" >i<br><p class="mb-0" id="i">i</p></td><td class="fontFourteen">i</td><td class="fontFourteen">i<br>i</td><td class="fontFourteen">i<br>i</td></tr>');
//                    dataTable.row.add([oNameMore[i].innerHTML]);

//                    $('#datatable').dataTable().fnAddData([
//                        j,
//                        j,
//                        response.data[i].sender_name,
//                        response.data[i].sender_mob,
//                        response.data[i].product_name,
//                        response.data[i].enquiry_source,
//                        response.data[i].assigned_to,
//                        response.data[i].status
//                    ]
//                            );
                }
//                dataTable.rows.add(response);
//                dataTable.draw();
            },
            failure: function () {
                alert(121);
            }
        });
    }

    function timeout() {
        setTimeout(function () {
            $('#yourDivId').fadeOut('fast');
        }, 2000);
    }

    function callIndiamartSchedular() {
        $.ajax({
            type: "POST",
            url: "EnquiryController",
            data: "refresh=Refresh",
            success: function (response) {
                location.reload();
            }
        });
    }


</script>



<!--//                    $('#datatable').dataTable().fnAddData([
//                        j,
//                        response.data[i].sender_name,
//                        response.data[i].sender_mob,
//                        response.data[i].product_name,
//                        response.data[i].enquiry_source,
//                        response.data[i].assigned_to,
//                        response.data[i].status
//                    ]
//                            );-->

<!--debugger;
        var fuel_station=[];
        var fuel_station_active=[];
        var fuel_station_inactive=[];
        var dataTable=$('#dataTables_fuel_station').DataTable();
        dataTable.clear().draw();
        $.ajax({
                url: 'http://localhost:8081/FireBrigade123/map/dashFuelStation',
                contentType: "application/json",
                dataType: 'json',
                success: function(result){
                        console.log(result);
                        // alert("data len -"+result.data.length);
                        var j=0;
                        for(var i=0; i<result.data.length;i++){	j++;			
                        fuel_station.push(result.data[i].id);		
                        if(result.data[i].active_inactive=='Y' || result.data[i].active_inactive=='y'){
                                fuel_station_active.push(result.data[i].active_inactive);
                        }else{
                                fuel_station_inactive.push(result.data[i].active_inactive);
                        }
                        
                        // start for fuel_station tables
                        $('#dataTables_fuel_station').dataTable().fnAddData( [
                                j, 
                                result.data[i].name,
                                result.data[i].contact,
                                result.data[i].active_inactive,
                                result.data[i].latitude,
                                result.data[i].longitude,
                                result.data[i].type
                                ]
                        );   

                        $('.even').toggleClass('even class'+j+'').attr('onclick', 'viewDetailsForFuelStation('+result.data[i].latitude+','+result.data[i].longitude+',"'+result.data[i].name+'");');
                        $('.odd').toggleClass('odd class'+j+'').attr('onclick', 'viewDetailsForFuelStation('+result.data[i].latitude+','+result.data[i].longitude+',"'+result.data[i].name+'");');

                        // alert("data name -"+result.data[i].name);


                        }												
                        // var uniqueVehicleNo = getUnique(fire_station);
                        $('#filling_stations').text(fuel_station.length);
                        $('#working_stations').text(fuel_station_active.length);
                        // $('#inactive_vehicles').text(fuel_station_inactive.length);
                        // alert("in ac -"+vehicles_inactive);
                }
        });
        setTimeout(checkThisFunForDashAllData, 50000);
}-->


