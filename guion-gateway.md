# Guion Explicativo: API Gateway para Microservicios de Libros y Pagos
## Introducción
Hola, en este video explicaremos el funcionamiento de nuestra API Gateway, que actúa como un punto de entrada unificado para nuestros microservicios. Esta API Gateway está configurada con Spring Cloud Gateway y gestiona el enrutamiento de las solicitudes a distintos microservicios.

## Descripción General
Nuestra API Gateway se ejecuta en el puerto 8080 y es responsable de redirigir las solicitudes entrantes a los microservicios adecuados. También está registrada en Eureka Server, lo que le permite descubrir dinámicamente los servicios disponibles.

## Rutas Configuradas
Hemos definido varias rutas en la configuración del API Gateway:

### Gestión de Catálogo de Libros
Listar libros: `GET /book` → Redirige al microservicio ms-books-catalogue
Crear un libro: `POST /book/create` → Redirige al microservicio ms-books-catalogue
Actualizar un libro: `PUT /book/{id}/update` → Redirige al microservicio ms-books-catalogue
Eliminar un libro: `DELETE /book/{id}/delete` → Redirige al microservicio ms-books-catalogue

### Gestión de Pagos
Registrar un pago: `POST /payment` → Redirige al microservicio ms-books-payments
Cada ruta usa predicados de ruta para identificar el endpoint correcto y filtros que aseguran que los métodos HTTP sean los apropiados para cada acción.

## Explicación del Filtro MethodChangeFilter
Un aspecto importante de nuestra API Gateway es el filtro personalizado llamado MethodChangeFilter. Este filtro se utiliza en nuestras rutas para modificar dinámicamente el método HTTP de una solicitud antes de que sea enviada al microservicio correspondiente.

### ¿Cómo funciona?
- Intercepta la solicitud antes de enviarla al servicio de destino.
- Verifica si el método HTTP está permitido (GET, POST, PUT, DELETE, PATCH).
- Modifica el método HTTP según la configuración definida en application.yml.
- Reenvía la solicitud modificada al microservicio correspondiente.

Código clave en MethodChangeFilter
``` java
var mutatedExchange = exchange.mutate()
    .request(request.mutate().method(HttpMethod.valueOf(config.getMethod())).build())
    .build();
return chain.filter(mutatedExchange);
```
Este fragmento de código cambia el método HTTP original al nuevo método especificado en la configuración.

### Beneficios de este filtro
Permite mayor flexibilidad en el manejo de rutas sin modificar el código de los clientes.
Facilita la gestión y estandarización de las solicitudes en la API Gateway.
Reduce la complejidad en los microservicios, ya que la API Gateway se encarga de las transformaciones necesarias.