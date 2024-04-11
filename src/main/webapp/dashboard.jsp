<%-- 
    Document   : dashboard
    Created on : 6 Jun, 2023, 1:25:09 PM
    Author     : Akshay
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
                        <h4 class="mb-sm-0 font-size-18">Dashboard</h4>
                        <div class="page-title-right">
                            <ol class="breadcrumb m-0">
                                <li class="breadcrumb-item"><a href="javascript: void(0);">Dashboards</a></li>
                                <li class="breadcrumb-item active">Dashboard</li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end page title -->

            <div class="row">
                <div class="col-xl-5">
                    <div class="row">
                        <c:forEach var="beanTypes" items="${requestScope['lists']}"
                                   varStatus="loopCounter">
                            <div class="col-xl-12">
                                <div class="card overflow-hidden">
                                    <div class="bg-primary bg-soft">
                                        <div class="row">
                                            <div class="col-5">
                                                <div class="avatar-md profile-user-wid1 mb-3">
                                                    <img src="assets/images/product/sample.jpg" alt="" class="img-thumbnail rounded-circle ms-2 mt-2" style="margin-top: 0%;">
                                                </div>
                                            </div>
                                            <div class="col-7 align-self-center">
                                                <div class="text-primary p-3 pt-2 pb-0">
                                                    <h5 class="text-primary">${beanTypes.key_person_name}</h5>
                                                    <p>${beanTypes.designation_name}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body pt-0">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="row pt-4">
                                                    <div class="col-3">
                                                        <h5 class="font-size-15">${beanTypes.notansweringcount}</h5>
                                                        <p class="text-muted mb-0 text-truncate">Not Answering</p>
                                                    </div>
                                                    <div class="col-3">
                                                        <h5 class="font-size-15">${beanTypes.followupcount}</h5>
                                                        <p class="text-muted mb-0">Follow up Again</p>
                                                    </div>
                                                    <div class="col-3">
                                                        <h5 class="font-size-15">${beanTypes.democount}</h5>
                                                        <p class="text-muted mb-0">Demo Required</p>
                                                    </div>
                                                    <div class="col-3">
                                                        <h5 class="font-size-15">${beanTypes.convertedcount}</h5>
                                                        <p class="text-muted mb-0">Success(Converted)</p>
                                                    </div>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>                      
                    </div>
                </div>
                <div class="col-xl-7">
                    <div class="row">
                        <c:forEach var="reminder" items="${requestScope['allRemindersDetails']}"
                                   varStatus="loopCounter">

                            <div class="col-md-6">                            
                                <div class="card bg-dark text-light position-relative">
                                    <a href="">
                                        <div class="card-body">
                                            <button type="button" class="btn" style="color:white;color: white;background-color: red;position: absolute;right: -9px;top: -14px;border-radius: 50%;" data-bs-dismiss="modal" aria-label="Close" 
                                                    onclick="deleteReminder(${reminder.chat_msg_table_id})">X</button>
                                            <div class="d-flex1">
                                                <div class="flex-grow-11">
                                                    <input type="hidden" id="${reminder.chat_msg_table_id}" name="fname">
                                                    <h4 class="text-light fw-medium">${reminder.sender_name}</h4>
                                                    <p class="mb-1 text-light font-size-14">${reminder.sender_mob}</p>
                                                    <p class="text-light mb-2">${reminder.enquiry_address}</p>
                                                    <p class="mb-0 text-danger"><strong>Call Reminder set for ${reminder.date} , ${reminder.time}</strong></p>
                                                </div>
                                            </div>                                        
                                        </div>
                                    </a>
                                </div>                            
                            </div>
                        </c:forEach>

                        <div class="col-md-6">
                            <div class="card mini-stats-wid"> 
                                <div class="card-body">
                                    <div class="d-flex">
                                        <div class="flex-grow-1">
                                            <p class="text-muted fw-medium">Total Leads</p>
                                            <h4 class="mb-0">${total_enquiries}</h4>
                                        </div>

                                        <div class="flex-shrink-0 align-self-center">
                                            <div class="mini-stat-icon avatar-sm rounded-circle bg-primary">
                                                <span class="avatar-title">
                                                    <i class="bx bx-copy-alt font-size-24"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card mini-stats-wid">
                                <div class="card-body">
                                    <div class="d-flex">
                                        <div class="flex-grow-1">
                                            <p class="text-muted fw-medium">Pending Leads</p>
                                            <h4 class="mb-0">${pendinglist}</h4>
                                        </div>
                                        <div class="flex-shrink-0 align-self-center">
                                            <div class="avatar-sm rounded-circle bg-primary mini-stat-icon">
                                                <span class="avatar-title rounded-circle bg-primary">
                                                    <i class="bx bx-purchase-tag-alt font-size-24"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card mini-stats-wid">
                                <div class="card-body">
                                    <div class="d-flex">
                                        <div class="flex-grow-1">
                                            <p class="text-muted fw-medium">This Month Sold</p>
                                            <h4 class="mb-0">${soldthismonth}</h4>
                                        </div>

                                        <div class="flex-shrink-0 align-self-center">
                                            <div class="mini-stat-icon avatar-sm rounded-circle bg-primary">
                                                <span class="avatar-title">
                                                    <i class="bx bx-copy-alt font-size-24"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card mini-stats-wid">
                                <div class="card-body">
                                    <div class="d-flex">
                                        <div class="flex-grow-1">
                                            <p class="text-muted fw-medium">Total Sold</p>
                                            <h4 class="mb-0">${totalsold}</h4>
                                        </div>

                                        <div class="flex-shrink-0 align-self-center ">
                                            <div class="avatar-sm rounded-circle bg-primary mini-stat-icon">
                                                <span class="avatar-title rounded-circle bg-primary">
                                                    <i class="bx bx-archive-in font-size-24"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="card">
                        <div class="card-body">
                            <div class="d-sm-flex flex-wrap">
                                <h4 class="card-title mb-4">Performace Report</h4>
                                <div class="ms-auto">
                                    <ul class="nav nav-pills">
                                        <li class="nav-item">
                                            <a class="nav-link" href="#">Week</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="#">Month</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link active" href="#">Year</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div id="stacked-column-chart" class="apex-charts" data-colors='["--bs-primary", "--bs-warning", "--bs-success","--bs-purple"]' dir="ltr"></div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title mb-4">Latest Enquiry</h4>
                            <div class="table-responsive">
                                <table class="table align-middle table-nowrap mb-0">
                                    <thead class="table-light">
                                        <tr>
                                            <th class="align-middle">Sender Name</th>
                                            <th class="align-middle">Mobile No.</th>
                                            <th class="align-middle">Product</th>
                                            <th class="align-middle">Assign</th>
                                            <th class="align-middle">Status</th>
                                            <th class="align-middle"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="beanType" items="${requestScope['list']}"
                                                   varStatus="loopCounter">
                                            <tr>
                                                <td class="fontFourteen">${beanType.sender_name}</td>
                                                <td class="fontFourteen">${beanType.sender_mob}</td>
                                                <td class="fontFourteen">${beanType.product_name}</td>
                                                <td class="fontFourteen">${beanType.assigned_to}</td>

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
                            <!--<td class="fontFourteen"><a href="EnquiryListController?id=${beanType.enquiry_table_id}">Details</a></td>-->
                                                <td><a href="EnquiryListController?id=${beanType.enquiry_table_id}"><span class="badge badge-pill badge-soft-primary font-size-14"><i class="far fa-list-alt"></i></span></a>
                                                </td> 
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- end table-responsive -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- end row -->
        </div>
        <!-- End Page-content -->




        <!-- Transaction Modal -->
        <div class="modal fade transaction-detailModal" tabindex="-1" role="dialog" aria-labelledby="transaction-detailModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="transaction-detailModalLabel">Order Details</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p class="mb-2">Product id: <span class="text-primary">#SK2540</span></p>
                        <p class="mb-4">Billing Name: <span class="text-primary">Neal Matthews</span></p>

                        <div class="table-responsive">
                            <table class="table align-middle table-nowrap">
                                <thead>
                                    <tr>
                                        <th scope="col">Product</th>
                                        <th scope="col">Product Name</th>
                                        <th scope="col">Price</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th scope="row">
                                            <div>
                                                <img src="assets/images/product/img-7.png" alt="" class="avatar-sm">
                                            </div>
                                        </th>
                                        <td>
                                            <div>
                                                <h5 class="text-truncate font-size-14">Wireless Headphone (Black)</h5>
                                                <p class="text-muted mb-0">$ 225 x 1</p>
                                            </div>
                                        </td>
                                        <td>$ 255</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">
                                            <div>
                                                <img src="assets/images/product/img-4.png" alt="" class="avatar-sm">
                                            </div>
                                        </th>
                                        <td>
                                            <div>
                                                <h5 class="text-truncate font-size-14">Phone patterned cases</h5>
                                                <p class="text-muted mb-0">$ 145 x 1</p>
                                            </div>
                                        </td>
                                        <td>$ 145</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <h6 class="m-0 text-right">Sub Total:</h6>
                                        </td>
                                        <td>
                                            $ 400
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <h6 class="m-0 text-right">Shipping:</h6>
                                        </td>
                                        <td>
                                            Free
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <h6 class="m-0 text-right">Total:</h6>
                                        </td>
                                        <td>
                                            $ 400
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="layout/footer.jsp" />
        <script>
            function getChartColorsArray(e) {
                if (null !== document.getElementById(e)) {
                    var t = document.getElementById(e).getAttribute("data-colors");
                    if (t)
                        return (t = JSON.parse(t)).map(function (e) {
                            var t = e.replace(" ", "");
                            if (-1 === t.indexOf(",")) {
                                var r = getComputedStyle(document.documentElement).getPropertyValue(t);
                                return r || t
                            }
                            var a = e.split(",");
                            return 2 != a.length ? t : "rgba(" + getComputedStyle(document.documentElement).getPropertyValue(a[0]) + "," + a[1] + ")"
                        })
                }
            }

            setTimeout(function () {
                $("#subscribeModal").modal("show")
            }, 2e3);
            var linechartBasicColors = getChartColorsArray("stacked-column-chart");
            linechartBasicColors && (options = {
                chart: {
                    height: 360,
                    type: "bar",
                    stacked: !0,
                    toolbar: {
                        show: !1
                    },
                    zoom: {
                        enabled: !0
                    }
                },
                plotOptions: {
                    bar: {
                        horizontal: !1,
                        columnWidth: "15%",
                        endingShape: "rounded"
                    }
                },
                dataLabels: {
                    enabled: !1
                },
                series: [{
                        name: "Follow up Again",
                        data: [44, 55, 41, 67, 22, 43, 36, 52, 24, 18, 36, 48]
                    }, {
                        name: "Not Answering",
                        data: [13, 23, 20, 8, 13, 27, 18, 22, 10, 16, 24, 22]
                    }, {
                        name: "Demo Required",
                        data: [11, 17, 15, 15, 21, 14, 11, 18, 17, 12, 20, 18]
                    }, {
                        name: "Pending",
                        data: [11, 17, 15, 15, 21, 14, 11, 18, 14, 12, 20, 18]
                    }],
                xaxis: {
                    categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
                },
                colors: linechartBasicColors,
                legend: {
                    position: "bottom"
                },
                fill: {
                    opacity: 1
                }
            }, (chart = new ApexCharts(document.querySelector("#stacked-column-chart"), options)).render());

        </script>

        <script>
            function deleteReminder(reminderId) {
                var result = confirm("Are you sure?");
                if (result) {
                    $.ajax({
                        url: "DashboardController",
                        data: {task: "deleteReminder", reminder_id: reminderId},
                        datatype: JSON,
                        success: function (alldata) {
//                            alert("success");
                            window.location.reload();
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
