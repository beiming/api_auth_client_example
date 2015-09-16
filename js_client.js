var md5 = require('md5');

Base64 = {
	base64EncodeChars: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
	url_safe_encode: function (bytesStr) {
		return Base64.encode(bytesStr).replace(/\+/g, '-').replace(/\//g, '_')	
	},
	encode: function (bytesStr) {
	    var c1, c2, c3;
	    var i = 0,
	        len = bytesStr.length,
	        string = '';

	    while (i < len) {
	        c1 = bytesStr[i++] & 0xff;
	        if (i == len) {
	            string += Base64.base64EncodeChars.charAt(c1 >> 2);
	            string += Base64.base64EncodeChars.charAt((c1 & 0x3) << 4);
	            string += "==";
	            break;
	        }
	        c2 = bytesStr[i++];
	        if (i == len) {
	            string += Base64.base64EncodeChars.charAt(c1 >> 2);
	            string += Base64.base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
	            string += Base64.base64EncodeChars.charAt((c2 & 0xF) << 2);
	            string += "=";
	            break;
	        }
	        c3 = bytesStr[i++];
	        string += Base64.base64EncodeChars.charAt(c1 >> 2);
	        string += Base64.base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
	        string += Base64.base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
	        string += Base64.base64EncodeChars.charAt(c3 & 0x3F)
	    }
	    return string
	},
}

var app_key = 'UirA_RPyTpueOTL4VZZ4CA==';
var secret_key = 'e2ccdc1b188fc712ed497be825c5cd4a';

//var timestamp = ~~(Date.now() / 1000);
var timestamp = 1442330120;
var host = 'host';
var uri = "/external-api/v1/students/student_no/post-lesson-tasks/todo";

var url_partial = "{uri}?app_key={app_key}&ts={timestamp}".replace(/\{uri\}/g, uri).replace(/\{app_key\}/g, app_key).replace(/\{timestamp\}/g, timestamp);

console.log(url_partial)
var md5_bytes = md5(url_partial + secret_key, {asBytes: true});
var token = Base64.url_safe_encode(md5_bytes)
console.log(token);

var url = "http://{host}{url_partial}&token={token}".replace(/\{host\}/g, host).replace(/\{url_partial\}/g, url_partial).replace(/\{token\}/g, token);
var target_url = "http://host/external-api/v1/students/student_no/post-lesson-tasks/todo?app_key=UirA_RPyTpueOTL4VZZ4CA==&ts=1442330120&token=Pm9WJGNEqfe5nlwxwB-zeA==";

console.log(url);
console.log(target_url)
console.log(url == target_url)