<!-- Destroys the session logs out and returns you to the login page -->
<?php
session_start();
session_destroy();
header('Location: http://localhost/Project-2.2/Website/pages-login.php');