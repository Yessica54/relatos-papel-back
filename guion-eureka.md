# Guion Explicativo: Configuración del Servidor Eureka
## Introducción
Hola, en este video explicaremos el funcionamiento de Eureka Server, un servicio de descubrimiento de microservicios que forma parte del ecosistema de Spring Cloud Netflix.

## ¿Qué es Eureka?
Eureka es un registro de servicios que permite que los microservicios se registren y se descubran dinámicamente entre sí. Funciona como un directorio centralizado donde los servicios pueden consultar qué otros servicios están disponibles en la red.

## Configuración del Servidor Eureka
Nuestro servidor Eureka está configurado en el archivo application.yml de la siguiente manera:

1. Configuración del Servidor
``` yaml
server:
  port: '8761'
```
El servidor se ejecuta en el puerto 8761, que es el puerto por defecto para Eureka Server.
2. Configuración de la Aplicación
``` yaml
spring:
  application:
    name: eureka-server
```

El nombre de la aplicación se define como eureka-server, lo que facilita su identificación en el ecosistema de microservicios.
3. Configuración de Eureka
```yaml
eureka:
  client:
    fetchRegistry: false
    register-with-eureka: false
    service-url:
      defaultZone: ${EUREKA_CLIENT_SERVICEURL_DEFAULTZONE:http://eureka-server:8761/eureka/}
```
`fetchRegistry: false`: Indica que este servicio no buscará otros servicios en el registro, ya que actúa como el servidor principal.
`register-with-eureka: false`: Evita que el propio servidor Eureka se registre dentro de sí mismo.
`defaultZone`: Define la URL donde los clientes deben registrarse y descubrir servicios, que en este caso es http://eureka-server:8761/eureka/.

## ¿Cómo Funciona Eureka Server?
- Arrancamos Eureka Server y comienza a ejecutarse en el puerto 8761.
- Los microservicios se registran en Eureka enviando solicitudes a la URL configurada.
- Los clientes consultan Eureka para obtener la lista de servicios disponibles y así poder comunicarse entre ellos sin necesidad de conocer direcciones IP fijas.