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

