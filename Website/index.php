<?php
include "checklogin.php";
?>

<?php

include './assets/js/php/tableTempGulf.php';
include './assets/js/php/top10CaribbeanSea.php';
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <title>TITEL</title>
    <!-- jvectormap -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <link href="plugins/jvectormap/jquery-jvectormap-2.0.2.css" rel="stylesheet">
    <link href="assets/css/metismenu.min.css" rel="stylesheet" type="text/css">
    <link href="assets/css/style.css" rel="stylesheet" type="text/css">
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="https://kit.fontawesome.com/a6dd52fde2.js" crossorigin="anonymous"></script>
</head>

<body>

<!-- Begin page -->
<div id="wrapper">

    <!-- Top Bar Start -->
    <div class="topbar">

        <!-- LOGO -->
        <div class="topbar-left">
            <a href="index.php" class="logo">
                        <span class="logo-light">
                            <img src="assets/images/logo-light.png" alt="" height="60">
                        </span>
                <span class="logo-sm">
                            <img src="assets/images/logo-sm.png" alt="" height="22">
                        </span>
            </a>
        </div>

        <nav class="navbar-custom">
            <ul class="navbar-right list-inline float-right mb-0">
                <!-- full screen -->
                <li class="dropdown notification-list list-inline-item d-none d-md-inline-block">
                    <a class="nav-link waves-effect" href="#" id="btn-fullscreen">
                        <i class="ion ion-md-qr-scanner noti-icon"></i>
                    </a>
                </li>
            </ul>
        </nav>

    </div>
    <!-- Top Bar End -->

    <!-- ========== Left Sidebar Start ========== -->
    <div class="left side-menu">
        <div class="slimscroll-menu" id="remove-scroll">

            <!--- Sidemenu -->
            <div id="sidebar-menu">
                <!-- Left Menu Start -->
                <ul class="metismenu" id="side-menu">
                    <li class="menu-title">Settings</li>
                    <li>
                        <a href="index.php" class="waves-effect">
                            <i class="fas fa-chart-line"></i>
                            <span> Dashboard </span>
                        </a>
                    </li>
                    <li>
                        <a href="logout.php" class="waves-effect">
                            <i class="fas fa-sign-out-alt"></i>
                            <span> Logout </span>
                        </a>
                    </li>
                </ul>

            </div>
            <!-- Sidebar -->
            <div class="clearfix"></div>

        </div>
        <!-- Sidebar -left -->

    </div>
    <!-- Left Sidebar End -->

    <!-- ============================================================== -->
    <!-- Start right Content here -->
    <!-- ============================================================== -->
    <div class="content-page">
        <!-- Start content -->
        <div class="content dasboard-content">
            <div class="container-fluid">
                <div class="page-title-box">
                    <div class="row align-items-center">
                        <div class="col-sm-6">
                            <h4 class="page-title">Dashboard</h4>
                            <ol class="breadcrumb">
                                <!-- <li class="breadcrumb-item"><a href="javascript:void(0);"><i class="mdi mdi-home-outline"></i></a></li> -->
                                <li class="breadcrumb-item active">Dash</li>
                            </ol>
                        </div>
                        <div class="col-sm-6">
                            <div class="float-right d-none d-md-block">
                                <div class="dropdown">
                                    <button class="btn btn-primary dropdown-toggle arrow-none waves-effect waves-light"
                                            type="button" data-toggle="dropdown" aria-haspopup="true"
                                            aria-expanded="false">
                                        <i class="fas fa-cog"></i> Settings
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right">
                                        <a class="dropdown-item" href="indexHT.php">HT</a>
                                        <a class="dropdown-item" href="indexNL.php">NL</a>
                                        <a class="dropdown-item" href="index.php">ENG</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="#">°F</a>
                                        <a class="dropdown-item" href="#">°C</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm">
                                <div class="float-right d-none d-md-block">
                                    <div class="button-items">
                                        <button class="btn btn-primary" type="button" id="export_button" data-toggle="button"
                                                style="margin-top: 0;">
                                            <i class="fas"></i> Export Data
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> <!-- end row -->
            </div>
            <!-- end page-title -->

            <!--            <div class="row">-->
            <!--                <div class="col-lg-4">-->
            <!--                    <div class="card mini-stat bg-pattern">-->
            <!--                        <div class="card-body mini-stat-img">-->
            <!--                            <div class="mini-stat-icon">-->
            <!--                                <i class="fas fa-cloud-rain fa-2x"></i>-->
            <!--                            </div>-->
            <!--                            <h6 class="text-uppercase mb-3 mt-0">Rainfall</h6>-->
            <!--                            <h5 class="mb-3">1,687 MM</h5>-->
            <!--                            <p class="text-muted mb-0"><span class="text-success mr-2"> 12% <i-->
            <!--                                            class="mdi mdi-arrow-up"></i> </span> From previous period</p>-->
            <!--                        </div>-->
            <!--                    </div>-->
            <!--                </div>-->
            <!--                <div class="col-lg-4">-->
            <!--                    <div class="card mini-stat bg-pattern">-->
            <!--                        <div class="card-body mini-stat-img">-->
            <!--                            <div class="mini-stat-icon">-->
            <!--                                <i class="fas fa-temperature-low fa-2x"></i>-->
            <!--                            </div>-->
            <!--                            <h6 class="text-uppercase mb-3 mt-0">Temperature</h6>-->
            <!--                            <h5 class="mb-3">23 °C</h5>-->
            <!--                            <p class="text-muted mb-0"><span class="text-danger mr-2"> -26% <i-->
            <!--                                            class="mdi mdi-arrow-down"></i> </span> From previous period</p>-->
            <!--                        </div>-->
            <!--                    </div>-->
            <!--                </div>-->
            <!---->
            <!--            </div>-->
            <!-- end row -->

            <div class="row">
                <div class="col-xl-12">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="mt-0 header-title mb-4">Heatmap</h4>
                            <iframe src="heatmap.php" width="100%" height="600px" style="border: none" scrolling="no"></iframe>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end row -->

            <div class="row">
                <!-- end col -->
                <div class="col-xl-6">
                    <div class="card messages">
                        <div class="card-body">
                            <h4 class="mt-0 header-title mb-4">Top-10 temp per 10 seconds</h4>
                            <div class="table-rep-plugin">
                                <div class="table-responsive mb-0" data-pattern="priority-columns">
                                    <table id="tech-companies-1" class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>Station</th>
                                            <th data-priority="1">Temperature(C)</th>
                                        </tr>
                                        </thead>
                                        <tbody id="data-temp">

                                        <script>
                                            $("#data-temp").load("./assets/js/php/loadTemp.php");
                                            var auto_refresh = setInterval(
                                                (function () {
                                                    $("#data-temp").load("./assets/js/php/loadTemp.php"); //Load the content into the div
                                                }), 1000);
                                        </script>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- end tab-content -->
                            </div>
                        </div>
                    </div>
                    <!-- end col -->

                </div>
                <!-- end row -->


                <div class="col-xl-6">
                    <div class="card messages">
                        <div class="card-body">
                            <h4 class="mt-0 header-title mb-4">Top-10 rain per 10 seconds</h4>
                            <div class="table-rep-plugin">
                                <div class="table-responsive mb-0" data-pattern="priority-columns">
                                    <table id="tech-companies-1" class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>Station</th>
                                            <th data-priority="1">Rainfall(mm)</th>
                                        </tr>
                                        </thead>
                                        <tbody id="data-rain">

                                        <script>
                                            $("#data-rain").load("./assets/js/php/loadRain.php");
                                            var auto_refresh = setInterval(
                                                (function () {
                                                    console.log("oef");
                                                    $("#data-rain").load("./assets/js/php/loadRain.php"); //Load the content into the div
                                                }), 1000);
                                        </script>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- end tab-content -->
                            </div>
                        </div>
                    </div>
                    <!-- end col -->

                </div>
            </div>
        </div>
        <!-- container-fluid -->
    </div>
</div>
<!-- content -->

<footer class="footer">
    © 2020 GROEP UNO DE MAYO
</footer>

</div>
<!-- ============================================================== -->
<!-- End Right content here -->
<!-- ============================================================== -->

</div>
<!-- END wrapper -->

<!-- jQuery  -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/metismenu.min.js"></script>
<script src="assets/js/jquery.slimscroll.js"></script>
<script src="assets/js/waves.min.js"></script>

<!-- datepicker -->
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<!-- vector map  -->
<script src="plugins/jvectormap/jquery-jvectormap-2.0.2.min.js"></script>
<script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>

<!-- apexcharts -->
<script src="plugins/apexcharts/apexcharts.min.js"></script>

<!-- Peity JS -->
<script src="plugins/peity-chart/jquery.peity.min.js"></script>

<script src="assets/pages/dashboard.js"></script>

<!-- App js -->
<script src="assets/js/app.js"></script>
<
</body>

</html>
