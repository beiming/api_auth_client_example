import base64
from os import path
import os

import rsa
import rsa.randnum


def main():
    cipher_text = encrypt('测试test')
    decrypt(cipher_text)


def encrypt(clear_text):
    print(clear_text)
    pub_key = get_public_key()
    clear_text = clear_text.encode('utf-8')
    cipher_text = rsa.encrypt(clear_text, pub_key)
    cipher_text = base64.urlsafe_b64encode(cipher_text).decode()
    print(cipher_text)
    return cipher_text


def decrypt(cipher_text):
    cipher_text = base64.urlsafe_b64decode(cipher_text.encode())
    pri_key = get_private_key()
    message = rsa.decrypt(cipher_text, pri_key)
    print(message.decode('utf-8'))


def get_public_key():
    pub_key_file = open('keys/key_pub.pem', 'rb')
    pub_key_data = pub_key_file.read()
    pub_key = rsa.PublicKey.load_pkcs1(pub_key_data)
    return pub_key


def get_private_key():
    pri_key_file = open('keys/key_pri.pem', 'rb')
    pri_key_data = pri_key_file.read()
    pri_key = rsa.PrivateKey.load_pkcs1(pri_key_data)
    return pri_key


def generate_key():
    pub_key, pri_key = rsa.newkeys(1024)
    pub_key_data = pub_key.save_pkcs1()
    pri_key_data = pri_key.save_pkcs1()

    if not path.exists('keys'):
        os.mkdir('keys')
    pub_key_file = open('keys/key_pub.pem', 'wb+')
    pub_key_file.write(pub_key_data)
    pub_key_file.close()

    pri_key_file = open('keys/key_pri.pem', 'wb+')
    pri_key_file.write(pri_key_data)
    pri_key_file.close()

if __name__ == '__main__':
    # generate_key()
    main()
