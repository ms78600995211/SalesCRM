<%-- 
    Document   : emergency_operator
    Created on : 25 May, 2023, 10:01:35 AM
    Author     : Vikranth
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">

        <style>
            .selected_row {
                font-weight: bolder;
                color: blue;
                border: 3px solid black;
            }
            table.dataTable {
                border-collapse: collapse;
            }
        </style>

        <script>
            $(document).ready(function () {
                $('#mytable tbody').on('click', 'tr', function () {
                    if ($(this).hasClass('selected_row')) {
                        $(this).removeClass('selected_row');
                    } else {
                        $("#mytable").DataTable().$(
                                'tr.selected_row').removeClass(
                                'selected_row');
                        $(this).addClass('selected_row');
                    }
                });


                $('#mytable').DataTable({
//                    dom: 'Bfrtip', // to hide page count dropdown
                    buttons: [
                        {
                            extend: 'copyHtml5',
                            exportOptions: {
                                columns: [0, ':visible']
                            }
                        },
                        {
                            extend: 'excelHtml5',
                            exportOptions: {
                                columns: ':visible'
                            }
                        },
                        {
                            extend: 'pdfHtml5',
                            exportOptions: {
                                columns: ':visible'
                            }
                        },
                        'colvis'
                    ]
                });

            });

            function fillColumn(id, count) {
                $('#user_name').val($("#" + count + '2').html());
                $('#main_id').val(id);
            }

            function displayImage(id) {
                var requestData = {
                    id: id,
                    type: "DL"
                };
                var imageElement = document.getElementById('imageData');

//                $('#imageData').attr("src", "http://localhost:8080/EmergencyModule/ImageServletController?task=viewImage&id=" + id);
                $('#imageData').attr("src", "ImageServletController?id=" + id);

//                $.ajax({
//                    type: "GET",
//                    url: "ImageServletController",
//                    data: requestData,
//                    success: function (response) {
////                        $('#imageData').attr('src', 'data:image/jpeg;base64,' + data);
//                        imageElement.src = 'data:image/jpeg;base64,' + response;
//                    }
//                });
            }

        </script>
    </head>
    <body>
        <section>
            <div class="container-fluid page_heading sectionPadding35">
                <h1>Emergency Request</h1>
            </div>
        </section>

        <section class="marginTop30 ">
            <div class="container">
                <div class="row">                
                    <div class="col-md-8">
                        <div class=" organizationBox">
                            <div class="headBox">
                                <h5 class="">Emergency List</h5>
                            </div>
                            <div class="row mt-3 myTable">
                                <div class="col-md-12">
                                    <div class="table-responsive verticleScroll">
                                        <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                                            <thead>
                                                <tr>
                                                    <th class="heading">S.No</th>
                                                    <th class="heading">Name</th>
                                                    <th class="heading">Emergency Type</th>
                                                    <th class="heading">File Type</th>
                                                    <th class="heading">File Name</th>
                                                    <th class="heading">Location</th>
                                                    <th class="heading">Status</th>
                                                    <th class="heading">File</th>
                                                </tr>
                                            </thead>
                                            <tbody>                            
                                                <c:forEach var="emergencyUserBean" items="${requestScope['emergencyUserBean']}"  varStatus="loopCounter">
                                                    <tr  
                                                        onclick="fillColumn('${emergencyUserBean.user_data_id}', '${loopCounter.count }');">
                                                        <td>${loopCounter.count }</td>                                    
                                                        <td id="${loopCounter.count }2">${emergencyUserBean.user_name}</td>
                                                        <td id="${loopCounter.count }3">${emergencyUserBean.emergency_type}</td>
                                                        <td id="${loopCounter.count }4">${emergencyUserBean.pic_type}</td>                                            
                                                        <td id="${loopCounter.count }5">${emergencyUserBean.file_name}</td>
                                                        <td id="${loopCounter.count }6">${emergencyUserBean.location}</td>
                                                        <td id="${loopCounter.count }7">${emergencyUserBean.user_status}</td>
                                                        <td id="${loopCounter.count }8"> <button class="btn normalBtn" id="addBtn" onclick="displayImage(${emergencyUserBean.user_data_id});">View</button></td>
                                                    </tr>
                                                </c:forEach>



                                            </tbody>
                                        </table>    
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <img id="imageData" class="img-fluid">
                    </div>
                </div>
            </div>
        </section>


        <section class="marginTop30">
            <div class="container organizationBox">
                <div class="headBox">
                    <h5 class="">User Details</h5>
                </div>
                <form name="form" method="POST" action="EmergencyOperatorController" >
                    <div class="row mt-3">                        

                        <div class="col-md-3">
                            <div class="">
                                <div class="form-group">
                                    <label>Name<span class="text-danger">*</span></label>
                                    <input class="form-control myInput" type="text" id="user_name" name="user_name" value="" >
                                </div>
                            </div>
                        </div>                        

                        <div class="col-md-3">
                            <div class="">
                                <div class="form-group">
                                    <label>ID Number<span class="text-danger">*</span></label>
                                    <input class="form-control myInput" type="text" id="id_number" name="id_number" value="" >
                                </div>
                            </div>
                        </div>                        

                        <div class="col-md-3">
                            <div class="">
                                <div class="form-group">
                                    <label>Remark<span class="text-danger">*</span></label>
                                    <input class="form-control myInput" type="text" id="remark" name="remark" value="" >
                                </div>
                            </div>
                        </div>

                    </div>      
                    <hr>
                    <div class="row">
                        <div id="message">
                            <c:if test="${not empty message}">
                                <div class="col-md-12 text-center">
                                    <label style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                                </div>
                            </c:if>
                        </div>
                        <input type="hidden" id="clickedButton" value="">
                        <div class="col-md-12 text-center">                       
                            <input type="submit" class="btn normalBtn" name="task" id="save" value="Proceed">                            
                            <input type="hidden" name="main_id" id="main_id" value="">
                        </div>
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>