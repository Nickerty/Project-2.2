<?php
session_start();
if(!isset($_SESSION['login'])) {
    header('Location: http://localhost/Project-2.2/Website/pages-login.php');
}

?>