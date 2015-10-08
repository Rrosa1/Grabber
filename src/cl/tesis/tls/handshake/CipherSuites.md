# Type of Cipher Suites 

### eNULL, NULL
    The "NULL" ciphers that is those offering no encryption. Because these offer no encryption at all and are a security risk they are disabled unless explicitly included.
    
### aNULL
    The cipher suites offering no authentication. This is currently the anonymous DH algorithms and anonymous ECDH algorithms. These cipher suites are vulnerable to a "man in the middle" attack and so their use is normally discouraged.
    
### ADH
    Anonymous DH cipher suites, note that this does not include anonymous Elliptic Curve DH (ECDH) cipher suites.
    