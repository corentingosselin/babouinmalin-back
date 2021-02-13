# BabouinMalin.com 

Back end part
Simple Barter website made with spring boot framework

You can check the front end part [here](https://github.com/corentingosselin/babouinmalin-client)

## Installation

You need to run the back end:

- mysql server
- [minio server](https://github.com/minio/minio) image database
- depencies from the pom.xml

## Usage

Rest API

No need authentifaction requests:
```
/auth/**
/stats
/categories/**
/barter/lastbarters
/barter/category/**
```

Authentifaction requests needed:
```
/barters
/users
```

Exemple:

You can use postman and send this request:
```
GET localhost:9000/users
```
display list of users


## License
[MIT](https://choosealicense.com/licenses/mit/)
