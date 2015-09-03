import base64
import hashlib
from urllib.request import Request, urlopen
import time

app_id = 'UirA_RPyTpueOTL4VZZ4CA=='
secret_key = 'e2ccdc1b188fc712ed497be825c5cd4a'


def get_request():
    api = 'external-api/v1/orgs/{}/courses/{}/scores'.format(1, '2014SA01')
    postfix = '?aid={}&ts={}'.format(app_id, int(time.time()))
    api += postfix

    token = '&t=' + base64.urlsafe_b64encode(hashlib.md5((api + secret_key).encode()).digest()).decode()
    url = 'http://127.0.0.1:5000/{}{}'.format(api, token)

    print(url)

    request = Request(url)
    request.add_header('Accept', 'application/json')
    request.get_method = lambda: 'GET'
    page = urlopen(request)

    json_text = page.readall()
    print(json_text)

if __name__ == '__main__':
    get_request()
