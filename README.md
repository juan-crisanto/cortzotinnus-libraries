# Cortzotinnus Libraries

## Requerimientos

| Requerimiento | Versión |
|-------------|---------|
| Java JDK | 1.11+ |
| Maven | 3.6+ |

## Librería de seguridad web

### Usando el ejemplo

#### Pasos previos

Es necesario compilar e instalar la librería ```cortzotinnus-api-security-spring-boot-starter``` 
para que el proyecto ```cortzotinnus-sample``` pueda obtener dicha dependencia.

```sh
mvn clean compile install
```

#### Ejecutar el proyecto

Ejecutar el proyecto ```cortzotinnus-sample```, utilizando el IDE.

> cortzotinnuz-sample >> src/.../MainAppSample.java >> (click derecho) >> Run 'MainAppSample'

#### Probar

**Autenticación (Obtener Tokens)**

```sh 
curl -X POST -H "Content-Type: application/json" \ 
-d '{"username": "usuario@gmail.com", "password": "pass"}' \ 
http://localhost:8080/api/auth/login
```

**Autorización JWT**

```sh 
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer <rawToken> \ 
http://localhost:8080/api/test
```

**Rutas sin protección**

```sh 
curl -X POST -H "Content-Type: application/json" \ 
http://localhost:8080/api/excluded
```

### Configuración

Toda la configuración se encuentra en el archivo ```application.yml``` del proyecto ```cortzotinnus-sample```

#### CORS

Para configurar el filtro CORS se debe agregar la siguiente configuración:

```yaml
com:
  cortzotinnus:
    security:
      web:
        cors:
          allowed-origins: '*'
          allowed-methods:
            - GET
            - POST
            - PATCH
            - PUT
            - OPTIONS
          allowed-headers:
            - '*'
          allow-credentials: true
          register-cors-configuration-path: /**
```

#### Autenticación

Habilita la autenticación, que permite obtener el par de tokens de acceso (incluyendo el ```REFRESH TOKEN```), y configura las URLs que serán utilizadas.
Para esto, se debe agregar la siguiente configuración:

```yaml
com:
  cortzotinnus:
    security:
      web:
        config:
          token-duration-in-minutes: 30
          authorization-cookie-name: X-Authorization-token
          cookie-http-only: false
          use-cookie: false
          header-authorization-name: Authorization
          header-authorization-prefix: Bearer
        authentication:
          enabled: true
          login-processing-url: /api/auth/login
          logout-url: /api/auth/logout
          url-entry-point-pattern: /api/auth/**
```

#### Autorización por ```Bearer token```

Habilita la protección de las URLs con el esquema ```Bearer token```, agregando la siguiente configuración:

```yaml
com:
  cortzotinnus:
    security:
      web:
        config:
          token-duration-in-minutes: 30
          authorization-cookie-name: X-Authorization-token
          cookie-http-only: false
          use-cookie: false
          header-authorization-name: Authorization
          header-authorization-prefix: Bearer
        token-authorization:
          enabled: true
          url-token-protected-pattern: /api/**
          excluded-urls:
            - /api/excluded/**
            - /api/token/refresh
```

#### Basic Auth

Para habilitar el esquema de Basic Auth se agrega lo siguiente:

```yaml
com:
  cortzotinnus:
    security:
      web:
        basic:
          enabled: true
          url-entry-point-pattern: /internal-api/**
          username: basic-auth-user
          password: clave
          roles:
            - ROLE_ADMIN
```