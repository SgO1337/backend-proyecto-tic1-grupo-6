# proyecto-tic1-grupo-6
##Integrantes:
Rodrigo Quinke , Paula Strimber, Santiago García y Nicolás Wildbam

## Descripción del proyecto:
El proyecto consiste en la creación de una aplicación para
un cine que gestiones reservas y compras de entradas y 
alimentos. La aplicación debe permitir a los usuarios 
registrarse, loguearse, ver la cartelera, reservar y comprar entradas y alimentos, 
ver sus compras y reservas, y ver información sobre las películas.

Al entrar a la aplicacion, el usuario ve el home
![image](https://github.com/user-attachments/assets/cec58ab3-19b7-4f26-af85-01b1e127a70e)

En la esquina superior derecha esta el boton que permite al usuario registrarse o loguearse.
Se puede ver la informacion de las funciones disponibles sin estar logueado, pero no se permite reservar.

Pantalla de registro
![image](https://github.com/user-attachments/assets/2479fb57-3905-4752-98f3-bce61c8e3eb1)

Pantalla de login
![image](https://github.com/user-attachments/assets/6d351b25-f9ca-4abd-9efa-763f32fa8cf0)

Pantalla de home, del usuario logueado
![image](https://github.com/user-attachments/assets/f65add2d-5c0f-4671-a48d-b4cf46dc5e2e)

En la barra de navegacion superior, se puede elegir entre ver las peliculas o los snacks, ademas de ver el perfil del usuario o ver las reservas de funciones/snacks

Pantalla de my profile
![image](https://github.com/user-attachments/assets/0d1797ef-156f-46b7-9e03-c6060e231f08)

Pantalla de my purchases (bookings)
![image](https://github.com/user-attachments/assets/01dad89f-c155-4160-8c97-8ee19bb1c60d)

Pantalla de my purchases (snacks)
![image](https://github.com/user-attachments/assets/adac49bc-33c4-45ca-be02-7b295175e577)

Pantalla de reserva de snacks
![image](https://github.com/user-attachments/assets/f5ef5151-1f0c-4580-a134-5354127d3678)

Al seleccionar una pelicula en la pantalla home, lleva al menu de seleccion de funcion, en donde se podra seleccionar la sucursal, fecha, hora y sala donde ver la pelicula, asi como seleccionar la cantidad de asientos a reservar.

![Proyecto nuevo (5)](https://github.com/user-attachments/assets/abe509d4-4cf2-4f66-9167-3fc5ec1a25e6)

Al seleccionar la funcion, nos lleva a la pantalla de seleccion de butacas.

![Proyecto nuevo (6)](https://github.com/user-attachments/assets/3dd29dbc-2fe8-4039-8ee5-316929c56785)

Finalmente, al realizar la reserva correctamente, nos lleva a la pantalla de my bookings ya mostrada.

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
