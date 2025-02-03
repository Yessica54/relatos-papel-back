# Guion Explicativo: Microservicio ms-books-payments

## Introducción
Hola, en este video explicaremos el microservicio ms-books-payments, encargado de gestionar los pagos. Utilizaremos Spring Boot, H2 Database, Spring Data JPA y Eureka.

## Configuración del Microservicio (application.yml)
- **Servidor**: Se ejecuta en el puerto 8082.
- **Base de Datos**: Usa H2 en memoria para pruebas.
- **JPA y Hibernate**: La aplicación está utilizando JPA para interactuar con la base de datos, y la configuración específica está optimizada para desarrollo. Por ejemplo, el parámetro `hibernate.ddl-auto` está establecido en create, lo que significa que la estructura de la base de datos se creará automáticamente cada vez que se inicie la aplicación.
- **Eureka**: La aplicación está configurada para ser un cliente de Eureka, lo que significa que se registrará automáticamente con un servidor de Eureka. En la sección `eureka.client`, podemos ver que la URL del servidor de Eureka está configurada como `http://eureka-server:8761/eureka/`. Esto permite que otras aplicaciones en la red descubran y se comuniquen con esta aplicación a través de Eureka.

## Capa de Persistencia: PaymentRepository
Este es un componente fundamental de la capa de persistencia, ya que es responsable de interactuar con la base de datos para almacenar y recuperar entidades `Payment`.

```java
public interface PaymentRepository extends JpaRepository<Payment, Long> { }
```
### ¿Qué hace?
- Extiende JpaRepository<Payment, Long>:
    -- Permite operaciones CRUD (`save`, `findById`, `deleteById`, etc.).

## Consumer BookConsumerImpl

Tenemos el BookConsumerImpl, que es un servicio responsable de obtener información sobre libros de un servicio externo, como un microservicio de catálogo de libros. Este servicio utiliza RestTemplate para realizar solicitudes HTTP a un endpoint externo.

En el método getBook, el consumidor realiza una solicitud GET al endpoint `http://ms-books-catalogue/book/{id}`, donde `{id}` es el identificador del libro que queremos obtener. El método devuelve una respuesta de tipo `ResponseEntity<BookDTO>`, que contiene la información del libro en formato BookDTO, se utilizar el nombre del servicio registrado en ureka.

El servicio BookConsumerImpl es inyectado en el PaymentService para que cuando se procesen los pagos, se pueda obtener la información del libro y validarla. Si hay un problema al obtener la información del libro, como un error de red o un libro no encontrado, el servicio maneja el error y responde con un mensaje adecuado, asegurando que la aplicación sea robusta y fácil de depurar.

## Lógica de Negocio: PaymentService

El PaymentService es el corazón de la lógica de negocio de esta aplicación. El servicio comienza con la configuración de un logger para registrar errores y eventos importantes en el proceso.

Dentro del método save, primero se iteran los libros de la solicitud (`RequestPaymentDto`), y por cada uno se realiza una llamada al servicio externo BookConsumer para obtener información sobre el libro. Si el libro no es válido o no se puede obtener la información, el servicio devuelve una respuesta con el estado adecuado (404 o 400, dependiendo del error).

Si se obtiene la información correctamente, el servicio también valida que el libro tenga un estado válido y actualiza la cantidad solicitada.

Si todo está en orden, el servicio mapea la solicitud de pago a una entidad Payment usando ModelMapper y guarda el pago en el repositorio PaymentRepository. Finalmente, devuelve una respuesta exitosa `(200 OK)` indicando que el pago fue procesado correctamente.

Además, el servicio maneja errores, registrando cualquier problema con los libros a través de excepciones HTTP o errores generales, asegurando que los fallos se registren y se notifiquen adecuadamente al usuario.

## Controlador BooksController
Ahora, pasemos a la parte funcional de la aplicación: el controlador REST. En este caso, tenemos un controlador llamado PaymentController, que está mapeado en la ruta `/payment` para recibir y procesar solicitudes HTTP.

Este controlador tiene un método savePayment, que está mapeado a un endpoint POST. Este método recibe una solicitud con los datos de pago en el cuerpo de la petición, representados por el objeto `RequestPaymentDto`. Al recibir la solicitud, el controlador llama al servicio PaymentService para procesar el pago y luego retorna una respuesta con un código `HTTP 201 Created`, indicando que el pago ha sido guardado correctamente.

