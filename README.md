# **Proyecto TIC 1 - Grupo 6**

---

## **Integrantes**
- Rodrigo Quincke  
- Paula Strimber  
- Santiago García  
- Nicolás Wildbam  

---

## **Descripción del Proyecto**  
El proyecto consiste en el desarrollo de un sistema integral para la gestión de cines, diseñado específicamente para **What The Fun Cinema** en Uruguay. Este sistema permitirá administrar funciones, reservas y compras de entradas y snacks, ofreciendo una experiencia moderna y eficiente a los usuarios finales.  

### **Contexto del Proyecto**  
**What The Fun Cinema**, una empresa estadounidense, expande su modelo de negocio a Uruguay con una propuesta innovadora: durante los primeros seis meses, las entradas a las funciones serán gratuitas, generando ingresos exclusivamente a través de la venta de alimentos y bebidas.  

### **Lista de Ubicaciones Iniciales**

| **ID Sucursal** | **Barrio**       | **Cantidad de Salas** |
|------------------|------------------|------------------------|
| 1                | Punta Carretas   | 8                      |
| 2                | Ciudad Vieja     | 5                      |
| 3                | Pocitos          | 7                      |
| 4                | Carrasco         | 4                      |
| 5                | Tres Cruces      | 6                      |
| 6                | Centro           | 10                     |
| 7                | Malvín           | 3                      |
| 8                | Buceo            | 6                      |

Para responder a estas necesidades, se desarrollará una aplicación web que gestione las operaciones solicitadas. Inicialmente, se abrirán las 8 sucursales especificadas en la tabla superior, con salas estándar predefinidas de 15 filas y 10 asientos por fila.  

---

## **Requerimientos**
### **Funcionales**
- Registro, inicio y cierre de sesión.  
- Consulta de cartelera.  
- Gestión de reservas de funciones.  
- Cancelación de reservas.

### **Técnicos**
- API REST documentada.  
- Cobertura de tests unitarios: 80%.

---

## **Novedades Incluidas en el Proyecto**
Además de los requisitos iniciales, el sistema contará con:  
1. **Reserva de snacks y bebidas.**  
2. **Uso de cookies de sesión para todo el flujo de la aplicación final.**

---

## **Características Principales**
### **Usuarios**
- **Registro seguro:** Contraseñas protegidas mediante hashing.  
- **Autenticación y sesiones:** Inicio y cierre de sesión mediante tokens.  
- **Perfil de usuario:** Información personal, historial de compras y reservas.

### **Películas**
- **Cartelera completa:** Detalles de todas las películas disponibles.  
- **Gestión de funciones:** Selección de sucursal, sala, fecha y horario.  
- **Reserva de asientos:** Selección interactiva de asientos disponibles en cada función, para permitir la reserva.

### **Snacks**
- **Catálogo:** Listado de alimentos y bebidas con precios actualizados.  
- **Compra fácil:** Proceso simplificado para reservar snacks.  
- **Historial:** Registro de snacks reservados.

---

## **Tecnologías Utilizadas**
### **Frontend**
- **React:** Desarrollo de una interfaz dinámica y amigable.  
- **HTML, CSS, JavaScript:** Diseño y funcionalidad.  
- **Axios:** Consumo de APIs RESTful.  

### **Backend**
- **Java, Spring Boot:** Desarrollo de la lógica del sistema.  
- **PostgreSQL:** Almacenamiento de datos.  
- **Hibernate:** Gestión de entidades.  

### **Otros**
- **Patrón MVC:** Arquitectura organizada.  
- **Postman:** Documentación y pruebas de APIs.  
- **Hashing de contraseñas:** Seguridad de datos de usuarios.  

---

# **Flujo de la Aplicación**

### **Pantalla de Inicio (Home)**  
Cuando un usuario accede a la aplicación, se encuentra con la pantalla principal:  

![image](https://github.com/user-attachments/assets/cec58ab3-19b7-4f26-af85-01b1e127a70e)  

- **Acceso rápido:** En la esquina superior derecha, se encuentran los botones para registrarse o iniciar sesión.  
- **Exploración pública:** Los usuarios pueden ver la cartelera de películas disponibles sin necesidad de estar autenticados. Sin embargo, para realizar reservas, es obligatorio iniciar sesión.  

---

### **Pantalla de Registro**  
La pantalla de registro permite a los usuarios crear una cuenta ingresando información personal básica. Una vez registrados, pueden iniciar sesión para acceder a todas las funcionalidades de la aplicación.  

![image](https://github.com/user-attachments/assets/2479fb57-3905-4752-98f3-bce61c8e3eb1)  

---

### **Pantalla de Inicio de Sesión (Login)**  
Desde aquí, los usuarios existentes pueden autenticarse ingresando sus credenciales.  

![image](https://github.com/user-attachments/assets/6d351b25-f9ca-4abd-9efa-763f32fa8cf0)  

---

### **Pantalla de Inicio para Usuarios Logueados**  
Una vez autenticado, el usuario accede a una versión personalizada del Home:  

![image](https://github.com/user-attachments/assets/5dc556d5-75b4-42cb-b3a4-9e7f36a95d16)

#### **Navegación superior:**  
- **Películas y Snacks:** El usuario puede alternar entre explorar la cartelera o la lista de snacks disponibles.  
- **Perfil:** Acceso al perfil del usuario.  
- **Reservas:** Accediendo mediante el menu dropdown, se permite el acceso al menu de visualización de las reservas de películas y snacks realizadas.

---

### **Pantalla de Perfil del Usuario**  
En esta sección, el usuario puede:  
- Visualizar su información personal.  

![image](https://github.com/user-attachments/assets/0d1797ef-156f-46b7-9e03-c6060e231f08)  

---

### **Pantalla de Reservas (My Purchases)**  
#### **Reservas de Funciones:**  
Esta pantalla muestra un historial detallado de las reservas realizadas por el usuario para las funciones de cine, y se permite cancelar las reservas realizadas.  

![image](https://github.com/user-attachments/assets/01dad89f-c155-4160-8c97-8ee19bb1c60d)  

#### **Reservas de Snacks:**  
Aquí se presentan las reservas de snacks realizadas.  

![image](https://github.com/user-attachments/assets/adac49bc-33c4-45ca-be02-7b295175e577)  

---

### **Pantalla de Reserva de Snacks**  
El usuario puede seleccionar alimentos y bebidas para reservar, viendo un listado con precios actualizados.  

![image](https://github.com/user-attachments/assets/f5ef5151-1f0c-4580-a134-5354127d3678)  

---

### **Flujo de Reserva de Funciones**
1. **Selección de Película:**  
   Al seleccionar una película en el Home, se redirige al menú de selección de función:  
   - **Opciones a elegir:** Sucursal, fecha, horario, y sala.  
   - **Cantidad de asientos:** Especificar la cantidad de asientos a reservar.
   
   ![Proyecto nuevo (5)](https://github.com/user-attachments/assets/abe509d4-4cf2-4f66-9167-3fc5ec1a25e6)  

2. **Selección de Asientos:**  
   Una vez elegida la función, se accede a la pantalla de selección de butacas, donde el usuario puede elegir los asientos disponibles en un plano interactivo.
   
   ![Proyecto nuevo (6)](https://github.com/user-attachments/assets/3dd29dbc-2fe8-4039-8ee5-316929c56785)  

4. **Confirmación de Reserva:**  
   Al completar la reserva exitosamente, se redirige a la pantalla de "My Bookings" donde el usuario puede revisar los detalles de su compra y reserva.  

---

# **Endpoints disponibles**

El repositorio del backend incluye varias colecciones de Postman que contienen los detalles completos de todos los endpoints detallados a continuación, asi como ejemplos de uso.

| **Método** | **Endpoint**                                                                                          | **Descripción**                                                                                                                                                             |
|------------|------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST       | `api/booking-screening/create`                                                                        | Crea una nueva reserva de función, incluyendo la selección de asientos.                                                                                                   |
| GET        | `api/booking-screening/`                                                                               | Obtiene todas las reservas de funciones.                                                                                                                                     |
| GET        | `api/booking-screening/get-by-user-id/{id}`                                                            | Obtiene las reservas de funciones realizadas por un usuario específico, dado su ID.                                                                                        |
| DEL        | `api/booking-screening/delete/{id}`                                                                    | Elimina una reserva de función específica, identificada por su ID.                                                                                                        |
| POST       | `api/movies/create`                                                                                   | Crea una nueva película en el sistema.                                                                                                                                       |
| GET        | `api/movies`                                                                                          | Obtiene la lista de todas las películas disponibles en el sistema.                                                                                                        |
| GET        | `api/movies/view/{id}`                                                                                 | Obtiene los detalles de una película específica, dado su ID.                                                                                                              |
| PUT        | `api/movies/update/{id}`                                                                               | Actualiza la información de una película específica, dado su ID.                                                                                                         |
| DEL        | `api/movies/delete/{id}`                                                                               | Elimina una película específica del sistema, dado su ID.                                                                                                                 |
| GET        | `api/movies/currently-available`                                                                       | Obtiene la lista de películas que actualmente están disponibles para ver.                                                                                                 |
| GET        | `api/movies/{id}/branches`                                                                              | Obtiene las sucursales (distintas) donde se está proyectando una película específica, dado su ID.                                                                          |
| GET        | `api/movies/{idMovie}/branches/{idBranch}/screening-dates`                                              | Obtiene las fechas (distintas) en que una película específica se está proyectando en una sucursal específica.                                                              |
| GET        | `api/movies/{idMovie}/branches/{idBranch}/dates/{date}/screening-times`                                | Obtiene las horas (distintas) en que se está proyectando una película en una sucursal y fecha específica.                                                                  |
| GET        | `api/movies/{idMovie}/branches/{idBranch}/dates/{date}/screening-times/{screeningTime}/get-available-rooms` | Obtiene las salas disponibles (rooms) para una función específica de una película, en una sucursal y fecha determinadas.                                                     |
| POST       | `api/movies/get-screening-from-cascade-dropdown`                                                       | Obtiene el ID de la función (screeningId) basada en los datos de los dropdowns para realizar una reserva de asientos posterior.                                              |
| POST       | `auth/register`                                                                                        | Registra un nuevo usuario en el sistema.                                                                                                                                     |
| POST       | `auth/login`                                                                                           | Inicia sesión para un usuario existente.                                                                                                                                     |
| POST       | `api/branches/create`                                                                                 | Crea una nueva sucursal en el sistema.                                                                                                                                       |
| GET        | `api/branches`                                                                                         | Obtiene la lista de todas las sucursales.                                                                                                                                     |
| GET        | `api/branches/view/{id}`                                                                                | Obtiene los detalles de una sucursal específica, dado su ID.                                                                                                              |
| PUT        | `api/branches/update/{id}`                                                                              | Actualiza la información de una sucursal específica, dado su ID.                                                                                                         |
| DEL        | `api/branches/delete/{id}`                                                                              | Elimina una sucursal específica del sistema, dado su ID.                                                                                                                 |
| POST       | `api/food/create`                                                                                      | Crea un nuevo tipo de comida (snack) en el sistema.                                                                                                                        |
| GET        | `api/food`                                                                                             | Obtiene la lista de todos los snacks disponibles en el sistema.                                                                                                           |
| GET        | `api/food/view/{id}`                                                                                   | Obtiene los detalles de un snack específico, dado su ID.                                                                                                                  |
| PUT        | `api/food/update/{id}`                                                                                 | Actualiza la información de un snack específico, dado su ID.                                                                                                             |
| DEL        | `api/food/delete/{id}`                                                                                 | Elimina un snack específico del sistema, dado su ID.                                                                                                                     |
| POST       | `api/orders/create`                                                                                   | Crea un nuevo pedido de comida (snack) en el sistema.                                                                                                                      |
| GET        | `api/orders`                                                                                           | Obtiene la lista de todos los pedidos realizados.                                                                                                                           |
| GET        | `api/orders/view/{id}`                                                                                 | Obtiene los detalles de un pedido específico, dado su ID.                                                                                                                 |
| PUT        | `api/orders/update/{id}`                                                                               | Actualiza la información de un pedido específico, dado su ID.                                                                                                            |
| DEL        | `api/orders/delete/{id}`                                                                               | Elimina un pedido específico del sistema, dado su ID.                                                                                                                    |
| POST       | `api/rooms/create`                                                                                     | Crea una nueva sala (room) en el sistema.                                                                                                                                   |
| GET        | `api/rooms`                                                                                            | Obtiene la lista de todas las salas disponibles en el sistema.                                                                                                            |
| GET        | `api/rooms/view/{id}`                                                                                  | Obtiene los detalles de una sala específica, dado su ID.                                                                                                                  |
| PUT        | `api/rooms/update/{id}`                                                                                | Actualiza la información de una sala específica, dado su ID.                                                                                                             |
| DEL        | `api/rooms/delete/{id}`                                                                                | Elimina una sala específica del sistema, dado su ID.                                                                                                                     |
| POST       | `api/screenings/create`                                                                                | Crea una nueva proyección (screening) en el sistema.                                                                                                                       |
| GET        | `api/screenings`                                                                                       | Obtiene la lista de todas las proyecciones (screenings) disponibles.                                                                                                      |
| GET        | `api/screenings/view/{id}`                                                                              | Obtiene los detalles de una proyección específica, dado su ID.                                                                                                           |
| PUT        | `api/screenings/update/{id}`                                                                            | Actualiza la información de una proyección específica, dado su ID.                                                                                                       |
| DEL        | `api/screenings/delete/{id}`                                                                            | Elimina una proyección específica del sistema, dado su ID.                                                                                                               |
| GET        | `api/seats/booked-seats/{idScreening}`                                                                 | Obtiene todos los asientos reservados para una proyección específica, dada su ID.                                                                                        |
| GET        | `api/users`                                                                                             | Obtiene la lista de todos los usuarios registrados en el sistema.                                                                                                         |
| GET        | `api/users/view/{id}`                                                                                    | Obtiene los detalles de un usuario específico, dado su ID.                                                                                                                |
| PUT        | `api/users/update/{id}`                                                                                 | Actualiza la información de un usuario específico, dado su ID.                                                                                                           |
| DEL        | `api/users/delete/{id}`                                                                                 | Elimina un usuario específico del sistema, dado su ID.                                                                                                                   |

---

## **Configuración del Proyecto**
### **Backend**
- Configuración principal en `application.properties`.  
- Variables de entorno en `.env` para modo desarrollo (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).  
- Configuración en hosting para despliegue en producción (sin archivo .env local).  

### **Frontend**
1. Instalar dependencias:  
   ```bash
   npm install
   ```
2. Levantar servidor local:  
   ```bash
   npm start
   ```
3. Crear versión para producción:  
   ```bash
   npm run build
   ```  

---

## **Modelo Entidad-Relación**
El diseño garantiza la representación y conexión eficiente de datos relacionados con usuarios, funciones, reservas y snacks.  

![Diagrama MER](https://github.com/user-attachments/assets/6f86d108-7424-4bf1-9ed4-457595e36c11)  
