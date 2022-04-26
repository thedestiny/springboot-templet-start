
openssl genrsa -out rsa_private.pem 1024

openssl rsa -in rsa_private.pem -pubout -out rsa_public.pem