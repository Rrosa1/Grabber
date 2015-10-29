# Heartbeat and Heartbleed
## Extension
### Format
#### Hello Extension

```c
struct {
  ExtensionType extension_type;
  opaque extension_data<0..2^16-1>;
} Extension;

enum {
  signature_algorithms(13), (65535)
} ExtensionType;
```

#### Heartbeat Hello Extension *(extension data)*

```c
enum {
      peer_allowed_to_send(1),
      peer_not_allowed_to_send(2),
      (255)
   } HeartbeatMode;

   struct {
      HeartbeatMode mode;
   } HeartbeatExtension;
```

### Extension type

Extension name       | Value
:------------------- | :----
supported_groups     | 10
ec_point_formats     | 11
srp                  | 12
signature_algorithms | 13
use_srtp             | 14
heartbeat            | 15

### Extension Example

```c
0x00 0x0F // Extension type (2 bytes)
0x00 0x01 // Extension data length (2 bytes)
0x01 // Heartbeat mode (1 byte)
```

### Server Hello Response

```c
0x16 0x03 0x02 0x00 0x51 // tls header (5 bytes)
0x02 0x00 0x00 0x4D // handshake header (4 bytes)
0x03 0x02 // tls version (2 bytes)
0x19 0x20 0x86 0x29 0xEB 0xEE 0xE8 0x58 0x67 0xEC 0x65 0xA3 0x39 0xD0 0xCF 0x16
0x07 0x60 0x05 0xDB 0xAB 0x4A 0xC7 0x50 0x5B 0x7D 0xA6 0x98 0xAF 0x59 0x98 0x3F // random (32 bytes)
0x20 // session length (1 byte)
0x3A 0x1C 0xE1 0x63 0x1E 0x4F 0x5A 0x54 0x3E 0xC0 0xCC 0x8B 0xA3 0x60 0x9B 0x29
0x3A 0x0C 0xDE 0x5E 0xA7 0x52 0x6B 0x16 0x5A 0x15 0xA3 0x68 0x1E 0x23 0x1C 0x6A // session id (32 bytes)
0x00 0x33 //cipher suites (2 bytes)
0x00 // compression methods (1 byte)
0x00 0x05 // extension length (2 bytes)
0x00 0x0F 0x00 0x01 0x01 // heartbeat extension (5 bytes)
```

## Heartbleed
### Message Format

```c
enum {
     heartbeat_request(1),
     heartbeat_response(2),
     (255)
  } HeartbeatMessageType;

struct {
    HeartbeatMessageType type;
    uint16 payload_length;
    opaque payload[HeartbeatMessage.payload_length];
    opaque padding[padding_length];
 } HeartbeatMessage;
```

### Heartbleed Message

```c
0x18  // Content Type (Heartbeat)
0x03 0x02  // TLS version
0x00 0x03  // Length
// Payload
0x01       // Type Request
0x00 0x00  // Payload length
```
