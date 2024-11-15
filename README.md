# **Proyecto TIC 1 - Grupo 6**

---

## **Integrantes**
- Rodrigo Quinke  
- Paula Strimber  
- Santiago García  
- Nicolás Wildbam  

---

## **Descripción del Proyecto**  
El proyecto consiste en el desarrollo de un sistema integral para la gestión de cines, diseñado específicamente para **What The Fun Cinema** en Uruguay. Este sistema permitirá administrar funciones, reservas y compras de entradas y snacks, ofreciendo una experiencia moderna y eficiente tanto para usuarios como para administradores.  

### **Contexto del Proyecto**  
**What The Fun Cinema**, una empresa estadounidense, expande su modelo de negocio a Uruguay con una propuesta innovadora: durante los primeros seis meses, las entradas a las funciones serán gratuitas, generando ingresos exclusivamente a través de la venta de alimentos y bebidas.  

Para responder a estas necesidades, se desarrollará un software nuevo que gestione las operaciones locales. Inicialmente, se abrirán 8 sucursales en Montevideo, con salas estándar de 15 filas y 10 asientos por fila.  

---

## **Novedades Incluidas en el Proyecto**
Además de los requisitos iniciales, el sistema contará con:  
1. **Reserva de snacks y bebidas.**  
2. **Gestión unificada de reservas de películas y snacks.**

---

## **Características Principales**
### **Usuarios**
- **Registro seguro:** Contraseñas protegidas mediante hashing.  
- **Autenticación:** Inicio y cierre de sesión mediante tokens.  
- **Perfil de usuario:** Información personal, historial de compras y reservas.

### **Películas**
- **Cartelera completa:** Detalles de todas las películas disponibles.  
- **Gestión de funciones:** Selección de sucursal, fecha y horario.  
- **Reserva de asientos:** Selección interactiva de asientos disponibles.

### **Snacks**
- **Catálogo:** Listado de alimentos y bebidas con precios actualizados.  
- **Compra fácil:** Proceso simplificado para reservar snacks.  
- **Historial:** Registro de snacks reservados.

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

## **Requerimientos**
### **Funcionales**
- Registro, inicio y cierre de sesión.  
- Consulta de cartelera.  
- Gestión de reservas de funciones y snacks.  
- Cancelación de reservas.  

### **Técnicos**
- API REST documentada.  
- Cobertura de tests unitarios: 80%.  
- Buenas prácticas de código (uso de linters).  

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

## **Configuración del Proyecto**
### **Backend**
- Configuración principal en `application.properties`.  
- Variables de entorno en `.env` para modo desarrollo (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).  
- Configuración en hosting para despliegue en producción.  

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

--- 
