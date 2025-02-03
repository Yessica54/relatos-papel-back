# Guion Explicativo: Microservicio ms-books-catalogue
## Introducción
Hola, en este video explicaremos el microservicio ms-books-catalogue, encargado de gestionar el catálogo de libros. Utilizaremos Spring Boot, H2 Database, Spring Data JPA y Eureka.

## Configuración del Microservicio (application.yml)
- **Servidor**: Se ejecuta en el puerto 8081.
- **Base de Datos**: Usa H2 en memoria para pruebas.
- **JPA y Hibernate**: Configurados para mostrar SQL y evitar la regeneración automática de tablas.
- **Paginación**: Permite consultas con size, page y sort.
- **Eureka**: Se registra automáticamente en Eureka Server para descubrimiento de servicios.

## Capa de Persistencia: BookRepository
El BookRepository es una interfaz que maneja la comunicación con la base de datos.

```java

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> { }
```
### ¿Qué hace?
- Extiende JpaRepository<Book, Long>:
    -- Permite operaciones CRUD (`save`, `findById`, `deleteById`, etc.).
    -- Soporta paginación automática.
- Extiende JpaSpecificationExecutor<Book>:
-- Permite realizar búsquedas dinámicas con filtros personalizados, como en `getMatchingBooks()`.

### Ejemplo de Uso en BookService:
```java
return bookRepository.findAll(search(parm), pageable);
```
Esto permite buscar libros por título, categoría, ISBN o autor con un solo método.

## Lógica de Negocio: BookService
- **getMatchingBooks(parm, pageable)**: Busca libros con o sin filtro de texto.
- **getBook(id)**: Obtiene un libro por ID o devuelve un error 404.
- **save(book)**: Guarda un nuevo libro en la base de datos.
- **update(id, book)**: Modifica un libro existente sin sobrescribir valores nulos.
- **delete(id)** : Elimina un libro y devuelve su información.

## Controlador BooksController
Expone los endpoints REST para interactuar con el catálogo:

✅  `GET /book  `→ Lista libros con paginación y búsqueda.
✅ `GET /book/{id} `→ Retorna un libro específico.
✅ `POST /book/create `→ Agrega un nuevo libro.
✅ `PUT /book/{id}/update `→ Modifica un libro existente.
✅ `DELETE /book/{id}/delete `→ Elimina un libro.

Todos los métodos llaman a BookService, que a su vez usa BookRepository para acceder a la base de datos.

## Conclusión
Con este microservicio podemos crear, leer, actualizar y eliminar libros con búsquedas avanzadas y paginación. Además, BookRepository optimiza la interacción con la base de datos, y Eureka permite su descubrimiento dentro de la arquitectura.