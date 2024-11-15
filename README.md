# **Proyecto Tic 1 - Grupo 6**

---

## **Integrantes**
- Rodrigo Quinke  
- Paula Strimber  
- Santiago García  
- Nicolás Wildbam  

---

## **Descripción del Proyecto**  
El proyecto consiste en el desarrollo de un sistema integral para la gestión de cines, adaptado específicamente para las operaciones de **What The Fun Cinema** en Uruguay. Este sistema permitirá administrar funciones, reservas y compras de entradas y snacks, brindando una experiencia moderna y eficiente tanto para los usuarios como para los administradores.  

### **Contexto del Proyecto**  
La empresa estadounidense **What The Fun Cinema** ha decidido expandirse al mercado uruguayo con un modelo de negocio innovador: durante los primeros seis meses, las entradas a las funciones serán gratuitas, generando ingresos exclusivamente a través de la venta de alimentos y bebidas.  

Para adaptarse a las necesidades locales, se implementará un software completamente nuevo, diseñado para gestionar las particularidades de sus operaciones en Uruguay. Inicialmente, se inaugurarán 8 sucursales en los principales barrios de Montevideo, con salas estándar (15 filas y 10 asientos por fila) en cada cine.  

---

## **Objetivos del Proyecto**
- **Usuarios finales:**  
  - **Registro e inicio de sesión:** Permitir que los usuarios se registren de manera segura y accedan a su cuenta.  
  - **Gestión de reservas:** Facilitar la reserva de asientos en las funciones disponibles y la compra de snacks.  
  - **Consultas:** Brindar información detallada sobre películas, funciones y snacks.  
  - **Historial:** Proporcionar un historial de compras y reservas.  

- **Administradores:**  
  - Gestionar películas, funciones, salas y snacks.  
  - Visualizar las estadísticas de reservas y ventas.  

---

## **Novedades Incluidas en el Proyecto**
Además de los requisitos iniciales de la empresa, el sistema incluirá la funcionalidad de **reserva de snacks**. Los usuarios podrán:  
1. **Reservar snacks y bebidas**  
2. **Gestionar las reservas de peliculas y snacks**

---

## **Características Principales**
### **Usuarios**
- **Registro seguro:** Se utiliza hashing para proteger las contraseñas.  
- **Inicio y cierre de sesión:** Autenticación basada en tokens para mayor seguridad.  
- **Perfil del usuario:** Visualización de información personal, historial de compras y reservas.

### **Películas**
- **Catálogo completo:** Consulta de la cartelera disponible con información detallada de cada película.  
- **Gestión de funciones:** Selección de sucursal, fecha, sala y horario para realizar reservas.  
- **Reserva de asientos:** Visualización y selección de asientos disponibles en un plano interactivo.

### **Snacks**
- **Catálogo de snacks:** Listado de alimentos y bebidas disponibles con precios actualizados.  
- **Compra fácil:** Opcion para reservar snacks.  
- **Historial de compras:** Consultar detalles de los snacks reservados.

---

## **Ubicaciones Iniciales**
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

---

## **Tecnologías Utilizadas**
### **Frontend**
- **React** para una interfaz dinámica y amigable.  
- **HTML, CSS, JavaScript** para el diseño y funcionalidad de la aplicación.  
- **Axios** para el consumo de APIs RESTful.  

### **Backend**
- **Java, Spring, y Spring Boot** para la lógica de negocio.  
- **PostgreSQL** para la persistencia de datos.  
- **Hibernate** para la gestión de entidades.  

### **Otros**
- **Patrón MVC** para una arquitectura ordenada.  
- **Postman** para la documentación y pruebas de APIs.  
- **Render** para el hosting tanto del backend como del frontend.  
- **Hashing** de contraseñas para garantizar la seguridad de los datos de los usuarios.  

---

## **Requerimientos Funcionales**
  - Registro, inicio y cierre de sesión.
  - Consulta de cartelera de películas.
  - Gestión de reservas de funciones y snacks.  
  - Cancelación de reservas.

### **Requerimientos Técnicos**
- **API REST:** Documentación completa con Postman.  
- **Tests Unitarios:** Cobertura del 80% del código.  
- **Estilo de Código:** Buenas prácticas con variables descriptivas y uso de linter/formatter opcional.  

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
![image](https://github.com/user-attachments/assets/f65add2d-5c0f-4671-a48d-b4cf46dc5e2e)  

#### **Navegación superior:**  
- **Películas y Snacks:** El usuario puede alternar entre explorar la cartelera o la lista de snacks disponibles.  
- **Perfil:** Acceso al perfil del usuario.  
- **Reservas:** Visualización de las reservas de películas y snacks realizadas.

---

### **Pantalla de Perfil del Usuario**  
En esta sección, el usuario puede:  
- Visualizar su información personal.  
- Editar detalles de su cuenta si es necesario.  
![image](https://github.com/user-attachments/assets/0d1797ef-156f-46b7-9e03-c6060e231f08)  

---

### **Pantalla de Reservas (My Purchases)**  
#### **Reservas de Funciones:**  
Esta pantalla muestra un historial detallado de las reservas realizadas por el usuario para las funciones de cine.  
![image](https://github.com/user-attachments/assets/01dad89f-c155-4160-8c97-8ee19bb1c60d)  

#### **Reservas de Snacks:**  
Aquí se presentan las compras y reservas de snacks realizadas.  
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
   - **Selección de asientos:** Especificar la cantidad de asientos a reservar.  
   ![Proyecto nuevo (5)](https://github.com/user-attachments/assets/abe509d4-4cf2-4f66-9167-3fc5ec1a25e6)  

2. **Selección de Asientos:**  
   Una vez elegida la función, se accede a la pantalla de selección de butacas, donde el usuario puede elegir los asientos disponibles en un plano interactivo.  
   ![Proyecto nuevo (6)](https://github.com/user-attachments/assets/3dd29dbc-2fe8-4039-8ee5-316929c56785)  

3. **Confirmación de Reserva:**  
   Al completar la reserva exitosamente, se redirige a la pantalla de "My Bookings," donde el usuario puede revisar los detalles de su compra y reserva.  

---

### **Resumen de la Experiencia de Usuario**
Este flujo garantiza una navegación intuitiva y eficiente, permitiendo a los usuarios:  
- Explorar películas y snacks fácilmente.  
- Gestionar sus reservas y compras desde cualquier dispositivo.  
- Personalizar su experiencia a través de su perfil.  

## Configuración general del proyecto
###Para el backend:
El archivo application.properties contiene la configuración de la base de datos.  
Para entrar a modo desarollo, se debera crear un .env en la raiz del proyecto, configurando las variables de sesion DB_URL, DB_USERNAME y DB_PASSWORD.
Alternativamente, al realizar el deploy a produccion, se deberan configurar en el mismo servicio de deploy, sin crear un archivo aparte.
###Para el frontend:
Con npm, ejecutar npm install en la raiz del proyecto para descargar e instalar todas las dependencias necesarias. 
Luego con npm start se inicia un servidor de desarrollo, y con con npm run build se genera una build optimizada para produccion.

## Modelo Entidad Relacion (MER) final del proyecto:
![image](https://github.com/user-attachments/assets/6f86d108-7424-4bf1-9ed4-457595e36c11)
