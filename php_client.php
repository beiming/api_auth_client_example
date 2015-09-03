<?php
static $compare_mode = false;
static $target_time_stamp = 1441278377; // compare with $target_url


static $app_id = 'UirA_RPyTpueOTL4VZZ4CA==';
static $secret_key = 'e2ccdc1b188fc712ed497be825c5cd4a';


$api = sprintf('external-api/v1/orgs/%d/courses/%s/scores', 1, '2014SA01');
if($compare_mode)
{
	$postfix = "?aid={$app_id}&ts=".$target_time_stamp;
}
else
{
	$postfix = "?aid={$app_id}&ts=".time();
}

$api .= $postfix;

$token = '&t='.strtr(base64_encode(md5($api.$secret_key, true)), '+/', '-_');
$url = "http://127.0.0.1:5000/{$api}{$token}";

echo $url;
echo "\n";

if($compare_mode)
{
	$target_url = 'http://127.0.0.1:5000/external-api/v1/orgs/1/courses/2014SA01/scores?aid=UirA_RPyTpueOTL4VZZ4CA==&ts=1441278377&t=onE1DK9sXPEG9VTXvwwJhQ==';

	echo $target_url;
	echo "\n";
	echo $url == $target_url;
	echo "\n";
}
else
{
    $json_ret = file_get_contents($url);
    echo $json_ret;
    // $result = json_decode($json_ret, true);
    // print_r($result);
}
echo "\n";
?>