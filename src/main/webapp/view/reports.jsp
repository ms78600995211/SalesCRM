<%-- 
    Document   : reports
    Created on : 25 Aug, 2023, 9:39:06 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>         
<jsp:include page="layout/header.jsp" />

<style>
    .label{
        color:black;
    }
</style>
<!-- Navbar -->
<nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" navbar-scroll="true">
    <div class="container-fluid py-1 px-3">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark" href="javascript:;">Pages</a></li>
                <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Tables</li>
            </ol>
            <h6 class="font-weight-bolder mb-0">Tables</h6>
        </nav>

    </div>
</nav>
<!-- End Navbar -->
<div class="container-fluid py-4">
    <div class="row">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header pb-0">
                    <div class="row">
                        <div class="col-lg-6 col-7">
                            <h6>Send Report Request</h6>
                        </div>
                    </div>
                </div>
                <div class="card-body px-0 pb-2 mx-5">
                    <form action="ReportsController" name="form" method="post" enctype="multipart/form-data">
                        <div class="row">
                            <div class="col-lg-7">
                                <div class="input-group input-group-outline">
                                    <input type="text" name="report_name" class="form-control" placeholder="Enter Report Name">
                                    <select name="report_type" id="report_type" class="form-control">
                                        <option>Select Report Type</option>
                                        <option value="pdf">PDF</option>
                                        <option value="excel">Excel</option>
                                        <option value="spreadsheet">Spreadsheet</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-lg-2">
                                <label for="files" class="btn">Select Sample file</label>
                                <input id="files" name="sample_file" style="visibility:hidden;" type="file">
                            </div>
                            <div class="col-lg-1"> 
                                <input type="submit" name="task" value="Send" class="btn btn-success">
                            </div>
                            <!--<input type="file" name="sample_file" placeholder="Sample Report">-->
                            <div class="col-lg-2">
                                <input type="submit" class="btn btn-primary" value="View Text File" name="task">
                            </div>
                        </div>
                    </form>

                    <hr>
                    
                </div>
            </div>
        </div>
    </div>
   
</div>

<jsp:include page="layout/footer.jsp" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js">
</script> <!-- MAKE SURE THIS IS LOADED -->

<link 
    rel="stylesheet" 
    type="text/css" 
    href="https://cdn.datatables.net/1.13.1/css/jquery.dataTables.css">

<script 
    type="text/javascript" 
    charset="utf8" 
    src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.js">
</script>
<script>
    $(document).ready(function () {
        $('#myDataTable').DataTable();
    });
</script>