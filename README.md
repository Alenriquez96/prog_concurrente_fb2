# Student Analytics

### Instrucciones de instalación

Clonar el proyecto: https://github.com/Alenriquez96/prog_concurrente_fb2.git

Proyecto pensado para correr en IntelliJ, aunque se puede correr en Eclipse.

Seguir las instrucciones del IDE para arrancar el spingbootapplication.

🟢 Cómo abrirlo en Eclipse
1️⃣ Abrir Eclipse
2️⃣ File → Import
3️⃣ Existing Maven Projects
4️⃣ Seleccionar la carpeta raíz (donde está el pom.xml)

En eclipse ignorar archivos como .idea/ o .iml

🟢 Cómo abrirlo en Intellyj
1️⃣ Abrir Proyecto con Intellij
2️⃣ Maven -> Reload project (para actualizar dependencias)
3️⃣ Run JobServer (Botón verde)

### Como arrancar la aplicacion

Para iniciar la app, es necesario correr el StudentAnalyticsServer, esto iniciará el servidor web.

Ahora, podremos entrar a la base de datos h2 accedindo a: http://localhost:8080/h2-console

Aquí, introducimos las credenciales de:
 - user: sa
 - password: sa

Deberíamos poder ver ahora la base de datos:
![img.png](assets/bbdd.png)

![img.png](assets/studentstble.png)