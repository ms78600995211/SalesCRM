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
            <c:if test="${not empty message}">
                <div class="msgWrap">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="mdi mdi-check-all me-2"></i>
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>                
                </div>
            </c:if>

            <div class="msgWrap">
                <div class="alert alert-success alert-dismissible fade show" role="alert" style="display: none">
                    <i class="mdi mdi-check-all me-2"></i>
                    File Not Added !
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
                        <!-- end col-->
                        <div class="col-lg-12">
                            <div class="card">

                                <div class="mt-2">
                                    <div class="row ps-3">                                 
                                        <div class="col-md-5">
                                            <button type="button" id="clearButton" class="btn btn-success waves-effect waves-light" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files"> <i class="bx bx-plus font-size-16 align-middle me-2"></i> Add File</button>
                                        </div>      
                                    </div>
                                </div>

                                <div class="card-body pt-3">
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
                                                    <table id="datatable" class="table align-middle table-nowrap table-hover mb-0">
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

                                                            <c:forEach var="documentData" items="${requestScope['documentListData']}"
                                                                       varStatus="loopCounter">
                                                                <tr>
                                                                    <td>${loopCounter.count}</td>
                                                                    <td class="text-capitalize">
                                                                        <a href="FileUploadController?task=viewDocuments&documents_id=${documentData.documents_id}" target="_blank">
                                                                            <span class="badge badge-pill badge-soft-success font-size-11"> View </span>
                                                                        </a>
                                                                    </td>
                                                                    <td>
                                                                        <p class="mb-0">${documentData.file_name}</p>
                                                                    </td>
                                                                    <td>${documentData.file_type}</td>
                                                                    <td>
                                                                        ${documentData.created_at}
                                                                    </td>
                                                                    <td>
                                                                        <!--<a href="FileUploadController?task=getSingleDocumentDetails&documents_id=${documentData.documents_id}" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files" ><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>-->
                                                                        <a href="" onclick="getSingleDocDetails(${documentData.documents_id})" data-bs-toggle="modal" data-bs-target=".bs-example-modal-lg-Add-Files" ><span class="badge badge-pill badge-soft-success font-size-14"><i class="fas fa-edit"></i> </span></a>
                                                                        <a href="" onclick="deleteDoc(${documentData.documents_id})" class="deleteBtn"><span class="badge badge-pill badge-soft-danger font-size-14"><i class="fas fa-trash-alt"></i></span></a>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
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
                        <form class="custom-validation" action="FileUploadController" method="post" enctype="multipart/form-data" id="form1">
                            <div class="row">                      
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="" class="form-label">File Name:<span class="text-danger">*</span> </label>
                                        <input type="text" class="form-control" name="fileNameByField" placeholder="Name" 
                                               id="fileName" value="${filename}">
                                        <span class="error-message" id="textInputErrorFileName" style="color: red"></span>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">File Type:<span class="text-danger">*</span></label>
                                        <select class="form-control" name="fileType" id="fileType">
                                            <option value="0" selected disabled>---Select One---</option>
                                            <c:forEach var="fileType" items="${requestScope['fileTypeList']}"
                                                       varStatus="loopCounter">
                                                <option id ="${fileType.file_type}" value="${fileType.doc_type_id}" >${fileType.file_type}</option>
                                            </c:forEach>
                                        </select>
                                        <span class="error-message" id="textInputErrorFileType" style="color: red"></span>
                                    </div>
                                </div>            
                                <div class="col-md-12">
                                    <div class="mb-3">
                                        <label for="" class="form-label">File/Image:<span class="text-danger">*</span></label>
                                        <input type="file" class="form-control" name="fileName" placeholder="Mobile" id="fileupload">
                                        <span class="error-message" id="textInputErrorFileupload" style="color: red"></span>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="mb-3">
                                        <label for="" class="form-label">About File: </label>
                                        <textarea class="form-control" name="fileDescription" placeholder="About File" rows="5" maxlength="255" id="description"></textarea>
                                        <span class="error-message" id="textInputErrorDescription" style="color: red"></span>
                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary waves-effect" data-bs-dismiss="modal">Close</button>
                                <button class="btn btn-primary" type="submit" name="submitFormBtn" value="Submit">Submit form</button>
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
    <script src="assets/libs/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="assets/libs/datatables.net-responsive-bs4/js/responsive.bootstrap4.min.js"></script>
    <script src="assets/js/pages/datatables.init.js"></script>

    <script src="assets/libs/sweetalert2/sweetalert2.min.js"></script>

    <script>
                                                                            function deleteDoc(documents_id) {
                                                                                var result = confirm("Are you sure?");
                                                                                if (result) {
                                                                                    $.ajax({
                                                                                        url: "FileUploadController",
                                                                                        data: 'task=deleteDocuments&documents_id=' + documents_id + '',
                                                                                        type: 'POST',
                                                                                        success: function (alldata) {
//                                                                                           alert(alldata);
                                                                                        },
                                                                                        error: function (e) {
                                                                                            alert('Error:12212212112121 ' + e);
                                                                                        }
                                                                                    });
                                                                                } else {
                                                                                    return false;
                                                                                }
                                                                            }
    </script>

    <script>
        $("#form1").on("submit", function (e) {
            var fileType = $('#fileType').val();
            var fileName = $('#fileName').val();
            var fileupload = $('#fileupload').val();
            var description = $('#description').val();

            document.querySelectorAll(".error-message").forEach(function (errorElement) {
                errorElement.textContent = "";
            });

            if (fileName.trim() === "") {
                e.preventDefault();
                document.getElementById("textInputErrorFileName").textContent = "Text field is required.";
                return false;
            } else if (fileType == null || fileType == "" || fileType == "Select One" || fileType == "undefined") {
                e.preventDefault();
                document.getElementById("textInputErrorFileType").textContent = "Text field is required.";
                return false;
            } else if (fileupload.trim() === "") {
                e.preventDefault();
                document.getElementById("textInputErrorFileupload").textContent = "Text field is required.";
                return false;
            } else if (description.trim() === "") {
                e.preventDefault();
                document.getElementById("textInputErrorDescription").textContent = "Text field is required.";
                return false;
            } else {
                return true;
            }
        });
    </script>
    <script>
        function getSingleDocDetails(documents_id) {
            $(".hiddenInputClass").remove();
//            alert(documents_id);
            var document;
            var filePath = [];
            var remark = [];
            var file_name_from_field = [];
            var file_type = [];
            var document_id = [];
            var optionIdToSelect = 0;
            $.ajax({
                url: "FileUploadController",
                data: 'task=getSingleDocumentDetails&documents_id=' + documents_id + '',
                type: 'POST',
                dataType: 'json',
                success: function (alldata) {
                    debugger;
                    document = alldata.data;
                    if (document.length > 0) {
                        for (var i = 0; i < document.length; i++) {
//                            filePath[i] = document[i]["filePath"];
                            remark[i] = document[i]["remark"];
                            $("#description").val(remark);
                            file_name_from_field[i] = document[i]["file_name_from_field"];
                            $("#fileName").val(file_name_from_field);

                            file_type[i] = document[i]["file_type"];
                            if (file_type == "Brochure") {
                                optionIdToSelect = "1";
                            }
                            if (file_type == "Catalog") {
                                optionIdToSelect = "2";
                            }
                            if (file_type == "Image") {
                                optionIdToSelect = "3";
                            }
                            $("#fileType").val(optionIdToSelect);

                            //Add hidden field for get id
                            document_id[i] = document[i]["documents_id"];
                            var newInput = $("<input>").attr({
                                type: "hidden",
                                id: "hiddenInputId",
                                class: "hiddenInputClass",
                                name: "forEditingNameId",
                                placeholder: "Enter something",
                                value: document_id
                            });

                            // Append the new input element to the form
                            $("#form1").append(newInput);
                        }
                    }
                },
                error: function (e) {
                    alert('Error:12212212112121 ' + e);
                }
            });
        }

    </script>

    <script>
        // Get references to the input and button elements
        var description = document.getElementById("description");
        var fileName = document.getElementById("fileName");
        var clearButton = document.getElementById("clearButton");

        // Add a click event listener to the button
        clearButton.addEventListener("click", function () {
            // Clear the input's value
            description.value = "";
            fileName.value = "";
            $("#fileType").val("0");

            $(".hiddenInputClass").remove();
        });
    </script>
    
    <script>
    $('#datatable').DataTable({
        "ordering": false
    });</script>












