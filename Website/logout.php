<!-- Destroys the session logs out and returns you to the login page -->
<?php
session_start();
session_destroy();
header('Location: /pages-login.php');