#  Outlook Mail Reader API (Spring Boot + WebFlux)

Este proyecto permite consultar correos electronicos de una cuenta corporativa de Outlook 365 (Microsoft Graph) mediante una API REST, usando `client_credentials` para autenticacion automatica via Azure AD.

---

##  Tecnologias

- Java 21 (OpenJDK)
- Spring Boot 3.4.5
- Spring WebFlux (reactivo)
- WebClient
- Microsoft Graph API
- OAuth2 `client_credentials`
- SLF4J + Logback
- Maven

---

##  Caracteristicas

- 100% no bloqueante (reactivo)
- Token de acceso se genera automaticamente con WebClient
- Consulta segura a Graph API con Bearer Token
- Logging estructurado y trazable
- Compatible con producci贸n y desarrollo

---

## Ь Requisitos previos

### Azure AD (Microsoft Entra)

1. **Registrar una aplicacion** en [Azure Portal](https://portal.azure.com)
2. Guardar:
   - `client_id`
   - `client_secret`
   - `tenant_id`
3. Agregar permisos API:
   - `Mail.Read` (tipo: `Application`)
4. Hacer clic en **"Conceder consentimiento de administrador"**

---

##  Configuración

###  `application.yml`

```yaml
server:
  port: 9060

spring:
  application:
    name: outlookreader

  security:
    oauth2:
      client:
        registration:
          graph:
            client-id: YOUR_CLIENT_ID
            client-secret: YOUR_CLIENT_SECRET
            authorization-grant-type: client_credentials
            scope: https://graph.microsoft.com/.default
        provider:
          graph:
            token-uri: https://login.microsoftonline.com/YOUR_TENANT_ID/oauth2/v2.0/token
```

> Reemplaza `YOUR_CLIENT_ID`, `YOUR_CLIENT_SECRET` y `YOUR_TENANT_ID` con los valores reales de Azure.

---

##  Estructura del Proyecto

```
src/main/java
├── config/
│   └── GraphApiProperties.java
│   └── SecurityConfig.java
├── controller/
│   └── MailController.java
├── dto/
│   └── MailMessageDTO.java
│   └── EmailAddressDTO.java
├── mapper/
│   └── MailMapper.java
├── model/
│   └── MailMessage.java
│   └── EmailAddress.java
├── service/
│   └── OutlookMailService.java
├── util/
│   └── TokenUtil.java
└── OutlookReaderApplication.java

```

---

##  Endpoint principal

### `GET /api/mail/{userEmail}`

Consulta los correos del usuario indicado (mediante Microsoft Graph API).

#### Parametros

- `userEmail` Didireccion de correo del usuario (URL-encoded)

#### Respuesta

```json
[
  {
    "id": "AAMkAD...",
    "subject": "Informe mensual",
    "conversationId": "AAQkAD...",
    "receivedDateTime": "2025-05-14T10:30:00Z",
    "from": { "name": "Lilia Ruiz", "address": "lilia@peru.com" },
    "toRecipients": [...],
    "ccRecipients": [...],
    "body": "<p>Contenido HTML</p>"
  }
]
```

---

## Como probar

### Postman

- Metodo: `GET`
- URL:

```
http://localhost:9060/api/mail/rudy@peru.pe
```

- Headers:

```http
Content-Type: application/json
```

> Nota: usar `%40` en lugar de `@` en la URL.

---

### cURL

```bash
curl -X GET "http://localhost:9060/api/mail/rudy@peru.pe"
```

---

##  Pruebas automatizadas

###  `OutlookreaderApplicationTests.java`

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OutlookreaderApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetEmailsForRudy() {
        webTestClient.get()
                .uri("/api/mail/rudy@peru.pe")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isNumber()
                .jsonPath("$[0].subject").exists();
    }
}
```

> Ejecuta con: `mvn test`

---

##  Buenas practicas aplicadas

- WebFlux sin bloqueos (`block()` eliminado)
- Token OAuth2 generado correctamente como `application/x-www-form-urlencoded`
- Codigo desacoplado por capas (`Controller`, `Service`, `DTO`, `Model`, `Mapper`)
- Logging configurado con `logback-spring.xml`
- Seguridad habilitada y personalizada con `SecurityWebFilterChain`

---

##  Seguridad

Este endpoint esta **abierto (permitAll)** para facilitar pruebas. En produccion:

- Usa JWT o API Keys para protegerlo.
- Usa roles con `.pathMatchers("/api/**").hasRole("ADMIN")` si es necesario.

---

##  Construccion y ejecucion

```bash
mvn clean install
mvn spring-boot:run
```

---

##  Logs

Los logs se guardan en:

```
logs/outlook-reader-YYYY-MM-DD.log
```

Y tambien se imprimen por consola con niveles `INFO`, `DEBUG`, `ERROR`.

---

## Contacto

2R.
