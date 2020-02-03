<!-- Checks if credentials are accepted-->
<?php
session_start();
if(!isset($_SESSION['login'])) {
    header('Location: /pages-login.php');
}

?>