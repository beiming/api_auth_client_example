require 'digest'
require 'base64'
require 'net/http'

$compare = false
$target_time_stamp = 1441278377 #compare with $target_url



$app_id = 'UirA_RPyTpueOTL4VZZ4CA=='
$secret_key = 'e2ccdc1b188fc712ed497be825c5cd4a'

####### client logic
api = 'external-api/v1/orgs/%s/courses/%s/scores' % [1, '2014SA01']
if $compare
	postfix = "?aid=#{$app_id}&ts=%s" % $target_time_stamp
else
	postfix = "?aid=#{$app_id}&ts=%s" % Time.now.to_i
end

api += postfix

token = '&t=' + Base64.urlsafe_encode64(Digest::MD5.digest((api + $secret_key)))
url = "http://127.0.0.1:5000/#{api}#{token}"
####### client logic

puts url

if $compare
	$target_url = 'http://127.0.0.1:5000/external-api/v1/orgs/1/courses/2014SA01/scores?aid=UirA_RPyTpueOTL4VZZ4CA==&ts=1441278377&t=onE1DK9sXPEG9VTXvwwJhQ=='

	puts $target_url
	puts url == $target_url
else
	Net::HTTP.version_1_2
	Net::HTTP.start('127.0.0.1', 5000) {|http|
	  response = http.get("/#{api}#{token}")
	  puts response.body
	}
end