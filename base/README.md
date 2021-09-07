## Laboratorio de Programación y Lenguajes

### Departamento de Informática - FI-UNPSJB-PM

## Instalación y configuración del entorno de desarrollo

> ESTE LABORATORIO ESTÁ PREPARADO Y PROBADO PARA SER DESARROLLADO SOBRE SISTEMAS **LINUX**, ES REQUERIMIENTO DE LA ASIGNATURA FAMILIARIZARSE CON EL MISMO.
>
> Quien no cuente con el mismo en sus máquinas la cátedra sugiere dos posibles aproximaciones:
> 1. (Recomendada) Instalar cualquier distro Linux dual-boot.
> 2. Instalar el sistema en una máquina virtual

> En la raíz de este directorio existe el script ´labprog´ para facilitar la ejecución de varios comandos. En el presente instructivo se indicará en cada paso, si corresponde, la opción de ejecución mediante este script. 

## Setup

### Software necesario previamente

1. Instalar [Git](https://git-scm.com/download/linux)

1. Instalar [Docker](https://docs.docker.com/install/linux/docker-ce/ubuntu/) y [Docker Compose](https://docs.docker.com/compose/install/)

1. Iniciar servicio docker `sudo systemctl start docker`
    > Este comando puede variar según la distro de linux utilizada.

1. Instalar [Postman](https://www.postman.com/downloads/) y [DBeaver](https://dbeaver.io/download/)

1. Instalar un editor de texto para escribir el código, se recomienda [VS Code](https://code.visualstudio.com/download).

### Configuración de usuario de Gitlab

1. Genera una clave pública y agregarla al repo desde settings/ssh keys en Gitlab. Seguir este [instructivo](https://git.fi.mdn.unp.edu.ar/help/ssh/README#generating-a-new-ssh-key-pair)

### Obtener el código para trabajar

1. Realizar el **Fork** y dirigirse al repositorio nuevo.

1. Desde la línea de comandos, clonar este repositorio con sus submodulos `git clone --recursive <repo_url>` con la url ssh.

1. Ir al directorio clonado `cd <repo_dir>`

1. Actualizar submodulo base a la última versión `git submodule update --remote base` 
    > Este paso debe realizarse cada vez que se actualice el repo de infraestructura por la cátedra

1. Instalar node_modules en el cliente `./labprog install` 
    > Para la opción del seguimiento de Google elegir N


Aquí finaliza la instalación y configuración del ambiente de desarrollo, a continuación se detallan los pasos para comenzar con el desarrollo.

## Desarrollar con Docker

Para los siguientes pasos asegurarse de que el servicio de Docker esté corriendo, se puede ejecutar el comando `docker ps`.

### Levantar los servidores
Desde la carpeta raíz ```./labprog up```

> La primera vez que se ejecute este comando descargará las imagenes asociadas de Docker.
>
> Luego, es recomendable esperar un minuto a que inicien los servicios.

Para verificar su funcionamiento debería poder ingresar a `http://localhost:8080` y ver la página de bienvenida de Glassfish.
Para ingresar a la aplicación acceder a `http://localhost:4200` y ver la página de bienvenida de Angular.

En algunas ocasiones, puede ser necesario observar el administrador de Glassfish en `https://localhost:4848/` con usuario _admin_ y clave _admin_.


### Conectarse a los servidores por línea de comandos

Para conectarse al servidor **back-end** de Glassfish, una vez corriendo los servicios, ejecutar: ```./labprog bash server```

Para conectarse al servidor **front-end** de Angular y ejecutar los test de Cucumber, una vez corriendo los servicios, ejecutar: ```./labprog bash client```

### Detener los servicios

Para detener los servicios configurados en el archivo de docker-compose ejecutar: ```./labprog down```

El siguiente comando es para detener por completo el servicio de docker. En este caso, si los servicios están corriendo se detendrán y cuando docker sea iniciado nuevamente, estos contenedores serán levantados de forma automática.

`sudo systemctl stop docker`

## Desarrollar en Java en el servidor

Al conectarse al servidor `server`

1. Crear la base de datos `ant createdb`. Una única vez.

1. Para compilar el código `ant compile`.

1. Una vez compilado el código, desplegar la aplicación con `ant deploy`.

## Staging de datos

Para establecer un staging de la base de datos mediante sql, se deben agregar los archivos .sql en el directorio `src/server/etc/sql`.

Para efectivamente carga el staging en la base de datos, es necesario ejecutar en el server `ant stage -Dstage <nombre de archivo sql>`.

## Stack tecnológico
Además de cumplir con los requerimientos funcionales planteados en cada TP, el desarrollo de la aplicación deberá garantizar las siguientes premisas:
* Usar JPA como método de persistencia del modelo de datos. Para las consultas a la base de datos se deberá utilizar JPQL.
* Diseñar la aplicación utilizando los principios de los patrones de Separación en capas &rarr; Layered y N-Tiers.
* La aplicación deberá garantizar transacciones ACID. Especialmente para los procesos.
* Siempre que se pueda y deba, garantizar los principios SOLID de la programación Orientada a Objetos. (SRP, OCP, LSP, ISP, DIP).
* El stack tecnológico requerido para la solución contempla el uso de:
  + **Angular**  para la capa front end en clientes web
  + **JAX-RS** para la capa de servicios RESTfull. Capa presenter.
  + **JavaEE** para la capa de controladores y servicios.
  + **JPA** como ORM para la capa model.
  + **Derby** como motor de persistencia de datos administrada por Glassfish. Capa de base de datos.
* La gestión de tablas se realizará exclusivamente desde el modelo provisto a continuación y generado desde el ORM. **No se permite ingeniería inversa desde la DB.**

## Forma de entrega
* El trabajo será realizado en forma individual. Se podrá trabajar colaborativamente con otros compañeros.
* El trabajo práctico deberá ser entregado de la siguiente forma:
  * Todo el sitema completo debe ser entrega mediante el proyecto en Git.
  * Bitacora del desarrollo que incluya: Toma de decisiones de la arquitectura de la solución, restricciones de uso y relato del detalle de la evolución del desarrollo. En formato Wiki o Markdown. Este informe debería ser evolutivo en el transcurso del desarrollo del TP.
  * Toda la bibliografía utilizada deberá ser referenciada indicando título y autor, en una sección dedicada a tal efecto.
  * El diseño con el que se aborda la solución al problema planteado. En el caso de utilizar patrones, cuales de ellos utilizó y en qué contexto.
  * El programa de aplicación que implementa la solución mediante el cumplimiento efectivo de los test planteados en las features de BDD.
  * El código fuente debe estar sincronizado en git todo el tiempo para que la cátedra acceda al mismo y pueda verificar permanenetente los avances.

## Forma de aprobación
Se tendrá en cuenta para la aprobación del trabajo práctico y los integrantes del grupo:
1. Planificación del desarrollo de la aplicación. Cumplimiento de las etapas previstas.
2. Funcionamiento de la aplicación desarrollada. Se evaluará si la funcionalidad cumple con lo solicitado, en función de test de Criterios de Aceptación escritos en las features BDD.
3. Estructura general de la presentación, su legibilidad y facilidad de lectura y comprensión.
4. Contenido del informe y el uso de la información técnica para elaborarlo.





