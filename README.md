# Security Grabber [![Build Status](https://travis-ci.org/eacha/Grabber.svg?branch=master)](https://travis-ci.org/eacha/Grabber)


Programa que permite realizar un análisis en profundidad de la Ips detectadas con Zmap que mantienen cierto puerto abierto.

Modo de uso:
```sh
> java -jar Grabber_v1.3.jar --help
  --allCipherSuites           : Test 10 groups of cipher suites (default: false)
  --allTLSProtocols           : Test SSLv3.0, TLSv1.0, TLSv1.1, TLSv1.2
                                (default: false)
  --beast                     : Test beast (default: false)
  --heartbleed                : Test heartbleed (default: false)
  --list-output-modules       : Print all output modules (default: false)
  --list-probe-modules        : Print all probe modules (default: false)
  --scriptFile File           : Load packet from the file
  --startTLS                  : Start a TLS connection in non secure port
                                (default: false)
  --version                   : Print version and exit (default: false)
  -i (--input) File           : Input file
  -m (--module) String        : Set the probe module
  -o (--output) File          : Output file
  -om (--outputModule) String : Output file format (default: JSON)
  -p (--port) Int             : Port to scan
  -t (--threads) Int          : Threads used to send probes (default: 1)
```

## Instalación
Este proyecto se realizo utilizando Java7 y Maven para el manejo de las dependencias, basta instalar Maven y ejecutar `mvn install`.

## Releases
Para evitar la compilación del programa puede utilizarse los binarios en formato `jar` incluidos en los releases de github.
* [Grabber v1.0](https://github.com/eacha/Grabber/releases/tag/v1.0)
* [Grabber v1.1](https://github.com/eacha/Grabber/releases/tag/v1.1)
* [Grabber v1.2](https://github.com/eacha/Grabber/releases/tag/v1.2)
* [Grabber v1.3](https://github.com/eacha/Grabber/releases/tag/v1.3)
