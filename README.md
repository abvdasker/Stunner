[![Codacy Badge](https://api.codacy.com/project/badge/grade/4e851a87f23945f8b1b697367f06b619)](https://www.codacy.com/app/hal-c-rogers/Stunner)
[![Circle CI](https://circleci.com/gh/abvdasker/Stunner.svg?style=shield)](https://circleci.com/gh/abvdasker/Stunner)

Stunner
===
This is an incomplete STUN server implementation aiming for compliance with with [RFC 5389](https://tools.ietf.org/html/rfc5389). TLS support likely will not be provided (this can probably be achieved using a reverse proxy). A release after 1.0 may support [RFC 5780](https://tools.ietf.org/html/rfc5780).

Roadmap to 1.0
---
1. [x] Data modeling, request/response business logic
2. [x] UDP Support
3. [x] TCP support
4. [x] Integration testing with [RFC 5769](https://tools.ietf.org/html/rfc5769) test vectors & simple client
5. [x] Improved CLI
6. [ ] Logging
7. [ ] Threadpooled request handling
8. [ ] General cleanup and refactoring

Building
---
This project was designed to require no dependencies beyond the Java standard libraries.

### JDK >= 6
```bash
$ git clone https://github.com/abvdasker/Stunner
$ cd Stunner
$ ./build.sh
```
### Ant
Building with Ant will allow you to run the test suite as part of the build process.
```bash
$ git clone https://github.com/abvdasker/Stunner
$ cd Stunner
$ ant build
```
OR, without tests:
```bash
$ ant dist
```

Running
---
```bash
$ java -jar Stunner.jar
```
