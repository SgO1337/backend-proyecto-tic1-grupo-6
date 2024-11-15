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

## **Flujo de la Aplicación**
### **Pantallas Principales**
1. **Inicio:** Exploración pública de películas. Autenticación necesaria para reservas.  
2. **Registro e Inicio de Sesión:** Creación de cuenta y acceso seguro.  
3. **Perfil:** Gestión de información personal y reservas.  
4. **Reservas:** Historial detallado de reservas de funciones y snacks.  
5. **Catálogo:** Selección de películas, snacks y asientos interactivos.  

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
