# cuenta-ms

# Prerequisitos

- Instancia mysql aws
- SQS aws
- Cuenta AWS permisos IAM sobre SQS

# Instrucciones

- Agregar las definiciones de los permisos (KEY Y SECRET-KEY)
- Agregar la conexion a la instancia de bases de datos de mysql
- agregar la url del sqs
- application.yml [aplication.yml](/src/main/resources/application.yml)


# El proyecto esta dividido en en 2 micro servicios
- [cliente-ms](https://github.com/faustospina/cliente-ms)
- [cuenta-ms](https://github.com/faustospina/cuenta-ms)


# Ambos proyectos estan diseñados en java jdk 17, y Maven

- [cliente-ms](https://github.com/faustospina/cliente-ms) aca tenemos los endpoints:
    - gestionar cliente-persona
    - agregar movimientos a la cola

- [cuenta-ms](https://github.com/faustospina/cuenta-ms) aca tenemos los endpoints:
    - gestionar cuentas
    - gestionar movimientos
    - gestionar reporte
    - proceso en background afectacion de saldos cuentas y traza movimientos

# Instalacion docker
- En la raiz del proyecto ejecutar para [cliente-ms](https://github.com/faustospina/cliente-ms) docker build -t "spring-boot-cliente-ms" .
- En la raiz del proyecto ejecutar para [cuenta-ms](https://github.com/faustospina/cuenta-ms) docker build -t "spring-boot-cuenta-ms" .
- Despues de verificar las imagenes cargadas en docker seguir con el siguiente item
- En la raiz del proyecto ejecutar para [cliente-ms](https://github.com/faustospina/cliente-ms) docker-compose up

