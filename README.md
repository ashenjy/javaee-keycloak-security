# javaee-keycloak-security

A simple JavaEE app which demonstrates how to secure a simple REST service using KEYCLOAK (IAM) by applying security constraints and roles to it's endpoint.

## Installation Steps

1. Deploy to Wildfly

2. Get the access token ( use your own keycloak credentials )
   
   curl -X POST \
  http://localhost:8080/auth/realms/JavaSecurity/protocol/openid-connect/token \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -H 'postman-token: e47beec0-2b7d-b4af-6504-bacf40d4d7bc' \
  -d 'grant_type=password&client_id=keycloak-demo&username=ashen&password=ashen'
  
3. Call Person API using above token
 
   curl -X POST \
  http://localhost:7070/keycloak-demo-service/keycloakDemoService/keycloak-demo/person/ashen \
  -H 'authorization: Bearer {accessToken}
  -H 'content-type: application/json' \
  -H 'postman-token: 04fec23a-acd7-a2d2-b76e-3882ba3f25e6' \
  -d '{}'
  
  4. Expected Resposne
  
  {
    "PERSON_NAME": "ashen",
    "CLIENT_ID": "keycloak",
    "SUCESS": true
  }
 
