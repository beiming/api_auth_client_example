# Client logic Python(3.4.3) example:

### config
```
app_id = '{app_id}'
secret_key = '{secret_key}'
```


### generate token

**url parameters:**

- api: api_route
- aid: app_id
- ts: time_stamp
- t: token

**Target url:**

```http:host/{api_route}?aid={app_id}&ts={time_stamp}&t={token}```

**Token:**

```token_url = {api_route}?aid={app_id}&ts={time_stamp}```

```token = urlsafe_b64encode(md5(token_url + secret_key))```

python code:

```
api = 'external-api/v1/orgs/{}/courses/{}/scores'.format({org_id}, {course_code})
postfix = '?aid={}&ts={}'.format(app_id, int(time.time()))
api += postfix

token = '&t=' + base64.urlsafe_b64encode(hashlib.md5((api + secret_key).encode()).digest()).decode()
url = 'http://host/{}{}'.format(api, token)
```

### test get api
```
request = Request(url)
request.add_header('Accept', 'application/json')
request.get_method = lambda: 'GET'
page = urlopen(request)

json_text = page.readall()
print(json_text)
```


# Client logic Ruby(2.0.0p481) example:

### config
```
$app_id = '{app_id}'
$secret_key = '{secret_key}'
```

### generate token
```
api = 'external-api/v1/orgs/%s/courses/%s/scores' % [{org_id}, {course_code}]
postfix = "?aid=#{$app_id}&ts=%s" % Time.now.to_i
api += postfix

token = '&t=' + Base64.urlsafe_encode64(Digest::MD5.digest((api + $secret_key)))
url = "http://host/#{api}#{token}"
```

### test get api
```
Net::HTTP.version_1_2
	Net::HTTP.start('host', port) {|http|
	  response = http.get("/#{api}#{token}")
	  puts response.body
	}
```

# Client logic Java(1.8.0_25) example:

### config
```
String APP_ID = "{app_id}";
String SECRET_KEY = "{secret_key}";
```

### generate token
```	
String api = String.format("external-api/v1/orgs/%s/courses/%s/scores", {org_id}, {course_code});
String postfix = String.format("?aid=%s&ts=%s", APP_ID, (int)(System.currentTimeMillis()/1000));
api += postfix;

String token = "&t=";
try
{
	MessageDigest mdInst = MessageDigest.getInstance("MD5");
	mdInst.update((api + SECRET_KEY).getBytes());
	// import java.util.Base64; JAVA8 feature, or use custom base64encode, replace '+/' to '-_' for url safe
 	token += Base64.getUrlEncoder().encodeToString(mdInst.digest());
}
catch(Exception e)
{
	e.printStackTrace();
}
String url = String.format("http://host/%s%s", api, token);
```

### test get api
```
BufferedReader in = null;
try
{
	URL realUrl = new URL(url);
	URLConnection connection = realUrl.openConnection();
	connection.setRequestProperty("accept", "*/*");
    connection.setRequestProperty("connection", "Keep-Alive");
    connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    connection.connect();
    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	String line;
	while ((line = in.readLine()) != null)
	{
    	System.out.println(line);
   	}
}
catch (Exception e)
{
	e.printStackTrace();
}
finally
{
	try
	{
        if (in != null) 
        {
            in.close();
        }
	}
    catch (Exception e2)
    {
        e2.printStackTrace();
	}
}
```

# Client logic PHP(5.5.27) example:

### config
```
static $app_id = '{app_id}';
static $secret_key = '{secret_key}';
```

### generate token
```
$api = sprintf('external-api/v1/orgs/%d/courses/%s/scores', {org_id}, {course_code});
$postfix = "?aid={$app_id}&ts=".time();
$api .= $postfix;

$token = '&t='.strtr(base64_encode(md5($api.$secret_key, true)), '+/', '-_');
$url = "http://host/{$api}{$token}";
```

### test get api
```
$result = file_get_contents($url);
echo $result;
```


# Client logic Javascript example:

### config
```
var app_key = 'app_key';
var secret_key = 'secret_key';
```

### url_safe_base64_encode
```
Base64 = {
	base64EncodeChars: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
	url_safe_encode: function (bytesStr) {
		return Base64.encode(bytesStr).replace(/\+/g, '-').replace(/\//g, '_');
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
	    return string;
	},
}
```

### generate token
```
var timestamp = ~~(Date.now() / 1000);
var host = 'host';
var uri = "/uri";

var url_partial = "{uri}?app_key={app_key}&ts={timestamp}".replace(/\{uri\}/g, uri).replace(/\{app_key\}/g, app_key).replace(/\{timestamp\}/g, timestamp);

var md5_bytes = md5(url_partial + secret_key, {asBytes: true});
var token = Base64.url_safe_encode(md5_bytes)
console.log(token);
var url = "http://{host}{url_partial}&token={token}".replace(/\{host\}/g, host).replace(/\{url_partial\}/g, url_partial).replace(/\{token\}/g, token);
console.log(url);
```
