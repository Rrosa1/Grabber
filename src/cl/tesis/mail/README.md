# Mail protocols

## SMTP(Simple Mail Transfer Protocol)

### SMTP (25 TCP)

#### EHLO 
```
telnet 192.80.24.2 25
Trying 192.80.24.2...
Connected to 192.80.24.2.
Escape character is '^]'.
 > 220 sunsite.dcc.uchile.cl ESMTP sendmail
 > EHLO example.com
 > 250-sunsite.dcc.uchile.cl Hello , pleased to meet you
 > 250-ENHANCEDSTATUSCODES
 > 250-PIPELINING
 > 250-8BITMIME
 > 250-SIZE
 > 250-DSN
 > 250-STARTTLS
 > 250-DELIVERBY
 > 250 HELP
```

#### HELP
```
telnet 192.80.24.2 25
Trying 192.80.24.2...
Connected to 192.80.24.2.
Escape character is '^]'.
 > 220 sunsite.dcc.uchile.cl ESMTP sendmail
 > HELP
 > 214-2.0.0 
 > 214-2.0.0 You really should RTFM
 > 214-2.0.0 
 > 214 2.0.0 End of HELP info
```
#### STARTTLS
```
telnet 192.80.24.2 25
Trying 192.80.24.2...
Connected to 192.80.24.2.
Escape character is '^]'.
 > 220 sunsite.dcc.uchile.cl ESMTP sendmail
 > STARTTLS
 > 220 2.0.0 Ready to start TLS

```

#### TLS Handshake
```
	  Client                                 Server
        |                                      |
        |  ----------- ClientHello --------->  |
        |                                      |
        |  <---------- ServerHello ----------  |
        |  <---------- Certificate ----------  |
        |                 ...                  |
        |  <-------- ServerHelloDone --------  |
        |                 ...                  |

```
##### Client Hello
```
16 03 02 00 31 # TLS Header
01 00 00 2d # Handshake header
03 02 # ClientHello field: version number (TLS 1.1)
50 0b af bb b7 5a b8 3e f0 ab 9a e3 f3 9c 63 15 \
33 41 37 ac fd 6c 18 1a 24 60 dc 49 67 c2 fd 96 # ClientHello field: random
00 # ClientHello field: session id
00 04 # ClientHello field: cipher suite length
00 33 c0 11 # ClientHello field: cipher suite(s)
01 # ClientHello field: compression support, length
00 # ClientHello field: compression support, no compression (0)
00 00 # ClientHello field: extension length (0) (redundant)
```

#### Server Hello 
```
16 03 03 00 4A # TLS header
02 00 00 46 # Handshake header (02) type (00 00 46) message length
03 03 # Protocol version
55 FF 37 2B 3C 1C C0 CA 58 A6 0B E0 EF 6A C7 01\
12 9D 0E E6 4C E2 C8 99 E7 6F B0 38 77 68 9A FA # Random 4 byte timestamp, 28 byte random
20 # Session id length
AB 77 2D 68 3A E2 97 89 4B 98 BA B4 C5 41 49 ED \
B8 07 0F 54 FC 8C 53 92 FA 6A 58 6C 26 68 28 13 # Session id
00 33 # Cipher suits
00 # compresion methods
```

#### Server Certificate
```
16 03 03 03 65 # TLS header 
0B 00 03 61 # Handshake header (0B) type (00 03 61) message length
00 03 5E # Numbers of bytes of certificate list
<Certificate-List>
    00 03 5B # Numbers of bytes of certificate (859)
    30 82 03 57 30 82 02 3F A0 03 02 01 02 02 09 00 C6 5A 2E BC 81 EB 41 D2 
    30 0D 06 09 2A 86 48 86 F7 0D 01 01 05 05 00 30 6B 31 11 30 0F 06 03 55 
    04 0A 13 08 53 65 6E 64 6D 61 69 6C 31 18 30 16 06 03 55 04 0B 13 0F 53 
    65 6E 64 6D 61 69 6C 20 53 65 72 76 65 72 31 1E 30 1C 06 03 55 04 03 13 
    15 73 75 6E 73 69 74 65 2E 64 63 63 2E 75 63 68 69 6C 65 2E 63 6C 31 1C 
    30 1A 06 09 2A 86 48 86 F7 0D 01 09 01 16 0D 61 64 6D 69 6E 40 73 75 6E 
    73 69 74 65 30 1E 17 0D 31 35 30 35 31 34 31 37 31 36 34 30 5A 17 0D 32 
    35 30 35 31 31 31 37 31 36 34 30 5A 30 6B 31 11 30 0F 06 03 55 04 0A 13 
    08 53 65 6E 64 6D 61 69 6C 31 18 30 16 06 03 55 04 0B 13 0F 53 65 6E 64 
    6D 61 69 6C 20 53 65 72 76 65 72 31 1E 30 1C 06 03 55 04 03 13 15 73 75 
    6E 73 69 74 65 2E 64 63 63 2E 75 63 68 69 6C 65 2E 63 6C 31 1C 30 1A 06 
    09 2A 86 48 86 F7 0D 01 09 01 16 0D 61 64 6D 69 6E 40 73 75 6E 73 69 74 
    65 30 82 01 22 30 0D 06 09 2A 86 48 86 F7 0D 01 01 01 05 00 03 82 01 0F 
    00 30 82 01 0A 02 82 01 01 00 E7 9D 26 82 2A C8 50 D6 86 90 62 24 60 A8 
    A3 33 21 5C AC 50 41 97 2F 19 50 9C AB DA FA 76 78 0A BE B8 E0 2B F4 13 
    12 DE 43 12 F0 38 E6 B2 D5 16 3A E3 5F 31 1D AF ED D3 A0 F8 79 49 F0 22 
    50 4D 53 74 2C E1 73 3C A8 B6 44 D8 15 B5 D6 7C CE A1 56 E3 C0 41 61 B1 
    9D 76 65 D5 43 48 9F 9B CE DC 81 67 6E A7 F3 A8 8C C2 A3 EF B5 0F 5A 5C 
    EC B7 6A 4B 6F A0 55 0D 77 45 60 D9 44 04 51 D1 D5 34 5C BD EB B9 F1 9F 
    92 FD 7C 5D C1 AA 0C D0 54 CE D2 5B 7F DA 45 80 D6 E8 F0 DD 35 31 F0 E2 
    B6 D8 5D 40 45 B2 69 AD 21 3B 53 24 56 94 90 88 23 0A E3 10 FF 06 90 4F 
    ED 8F 56 A8 F8 5C 3A 07 5E 0C FE C6 B7 5A 89 0D A6 EB 2D CD 28 F8 D0 56 
    12 7B 2B 30 34 A4 27 A7 D5 73 DD 8F D7 21 F9 93 AD 1B 96 63 5A BB 71 AD 
    8B 1A AD A2 87 91 08 31 3E C2 6F 4C 4B E2 31 AA 59 C9 2F 50 E2 C0 71 44 
    5F 67 02 03 01 00 01 30 0D 06 09 2A 86 48 86 F7 0D 01 01 05 05 00 03 82 
    01 01 00 8D AA 85 3A A1 84 59 1B A2 D4 B3 18 E3 47 E1 75 EA 8A 66 2C E1 
    1C B8 3E 65 44 7E B4 16 8B DE 53 63 6F 62 DA 1F 43 50 6C EA DC C4 5D D5 
    C3 5A 41 24 9E A0 C0 4C 0D 11 BC 71 B1 83 F9 F0 EC 7F 90 CD 55 BD 8F 1F 
    C3 FA 11 EF A0 F8 A2 20 2E 22 CE C7 DC A4 B6 2D 2B 24 E5 7F 1D 85 D3 99 
    6F 4C 7A 53 3A DB D3 1C 3A D0 21 1C 49 E4 2D 0B 93 AC B2 E2 79 82 2F 9F 
    A0 AC 45 80 D3 29 A4 BC 2B 58 47 DB 1E BB A3 C2 18 21 3C 89 CB 9D 1E 6B 
    DE 55 7D 1B E3 E4 A4 3B EC DC 9A 18 31 E3 4D 49 CB 1C 69 83 F1 E3 34 83 
    1A 3B C1 67 38 51 A9 6F F4 0A 83 F0 84 57 4B 09 F9 B0 E7 B0 28 63 E3 09 
    D7 AC 58 7E FC 09 C0 E0 23 F9 58 C3 69 6A 85 7B DC 5B 11 D5 74 58 99 64 
    7E CA EB 5A E0 64 0A 3E 10 5B 78 E3 F4 84 2A D1 DE 27 9F 85 DC B1 E8 C3 
    8C D3 74 B9 EA 39 0C A4 58 B8 DB BD 27 5D 7B D4 2C D6 5F # Certificate
</Certificate-List>
```
### SMTP over SSL (465 TCP)

