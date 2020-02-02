<?php
session_start();
session_destroy();
header('Location: http://localhost/Project-2.2/Website/pages-login.php');