# Client logic Python3 example:

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

print(url)
```

### test
```
request = Request(url)
request.add_header('Accept', 'application/json')
request.get_method = lambda: 'GET'
page = urlopen(request)

json_text = page.readall()
print(json_text)
```


# Client logic Ruby example:

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
puts url
```

### test
```
Net::HTTP.version_1_2
	Net::HTTP.start('host', port) {|http|
	  response = http.get("/#{api}#{token}")
	  puts response.body
	}
```

