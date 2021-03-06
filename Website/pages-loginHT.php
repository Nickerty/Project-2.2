<!-- Checks if the given input are the right credentials to login and redirect you to de weather dashboard -->
<?php
if(isset($_POST['username'])) {
    if($_POST['username'] == "admin" && $_POST['userpassword'] == "test") {
        session_start();
        $_SESSION['login'] = true;
        header("Location: /indexHT.php");
    }
}
?>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
        <title>Ouvri sesyon an</title>

        <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/metismenu.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/style.css" rel="stylesheet" type="text/css">
    </head>

    <body class="bg-primary">
        <div class="home-btn d-none d-sm-block">
            <a href="indexHT.php" class="text-white"><i class="fas fa-home h2"></i></a>
        </div>

        <div class="account-pages my-5 pt-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-8 col-lg-6 col-xl-5">
                        <div class="card bg-pattern shadow-none">
                            <div class="card-body">
                                <div class="text-center mt-4">
                                    <div class="mb-3">
                                        <a href="indexHT.php" class="logo"><img src="assets/images/logo-dark.png" height="120" alt="logo"></a>
                                    </div>
                                </div>
                                <!-- Dropdown menu for choosing languages -->
                                <div class="float-right d-none d-md-block">
                                    <div class="dropdown">
                                        <button class="btn btn-primary dropdown-toggle arrow-none waves-effect waves-light" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fas fa-cog"></i> Langaj
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <a class="dropdown-item" href="pages-login.php">ENG</a>
                                            <a class="dropdown-item" href="pages-loginNL.php">NL</a>
                                            <a class="dropdown-item" href="pages-loginHT.php">HT</a>
                                        </div>
                                    </div>
                                </div>
                                <!-- Username and password input -->
                                <div class="p-3"> 
                                    <h4 class="font-18 text-center">Akeyi!</h4>
                                    <p class="text-muted text-center mb-4">Siyen an pou wè tablodbò ou</p>
                                    <form class="form-horizontal" action="" method="post">
                
                                        <div class="form-group">
                                            <label for="username">Non itilizatè</label>
                                            <input type="text" class="form-control" name="username" placeholder="Antre non itilizatè">
                                        </div>
                
                                        <div class="form-group">
                                            <label for="userpassword">Modpas</label>
                                            <input type="password" class="form-control" name="userpassword" placeholder="Antre modpas">
                                        </div>

                                        
                                        <div class="mt-3">
                                            <button class="btn btn-primary btn-block waves-effect waves-light" type="submit">Ouvri sesyon an</button>
                                        </div>
                                    </form>
                
                                </div>
                    
                            </div>
                        </div>
                        <div class="mt-5 text-center text-white-50">
                            <p>© 2020 GROUPO UNO DE MAYO</p>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery  -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/metismenu.min.js"></script>
        <script src="assets/js/jquery.slimscroll.js"></script>
        <script src="assets/js/waves.min.js"></script>

        <!-- App js -->
        <script src="assets/js/app.js"></script>
        
    </body>

</html>