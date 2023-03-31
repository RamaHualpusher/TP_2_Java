
# TP_2_Java

Este es un proyecto de ejemplo que utiliza Java con Maven para consumir la API RestCountries y almacenar los datos en una base de datos MySQL.

## Requisitos previos

Antes de ejecutar esta aplicación, asegúrate de tener los siguientes requisitos previos instalados:

- [Java 8+](https://www.java.com/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/)

## Configuración

Antes de ejecutar la aplicación, debes configurar la conexión a la base de datos MySQL. Para ello, edita las siguientes líneas en el archivo `App.java`:

```
private static final String DB_URL = "jdbc:mysql://localhost:3306/tp_2_java?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "mysql";
```

Reemplaza `DB_URL` con la URL de tu base de datos MySQL, `DB_USER` con el nombre de usuario de tu base de datos y `DB_PASSWORD` con la contraseña correspondiente.

Además, antes de ejecutar la aplicación, deberás crear la tabla "pais" en tu base de datos MySQL. Para ello, puedes utilizar el siguiente comando SQL:

```
CREATE TABLE pais (
  id INT(11) NOT NULL AUTO_INCREMENT,
  codigoPais VARCHAR(3) NOT NULL DEFAULT '---',
  nombrePais VARCHAR(100) NOT NULL DEFAULT 'unknown',
  capitalPais VARCHAR(100) NOT NULL DEFAULT 'unknown',
  region VARCHAR(100) NOT NULL DEFAULT 'unknown',
  poblacion BIGINT(15) NOT NULL DEFAULT 0,
  latitud DECIMAL(10, 8) NOT NULL DEFAULT 0,
  longitud DECIMAL(11, 8) NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
);
```

## Ejecución

Para ejecutar la aplicación, abre una terminal en la carpeta raíz del proyecto y ejecuta el siguiente comando:

```
mvn clean compile exec:java
```

Esto compilará y ejecutará el archivo `App.java`.

## Contribuyendo

Si deseas contribuir a este proyecto, ¡nos encantaría recibir tus ideas y sugerencias! Puedes hacerlo a través de GitHub mediante la apertura de un pull request o creando un issue.

## Autores

Este proyecto fue desarrollado por Ramiro Hualpa.

¡Gracias por usar nuestra aplicación!