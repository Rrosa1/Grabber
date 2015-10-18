package cl.tesis.tls.handshake;

/**
 * Created by eduardo on 18-10-15.
 */
public enum ExtensionType {
    SERVER_NAME((byte)0x00),
    MAX_FRAGMENT_LENGTH((byte) 0x01),
    client_certificate_url((byte) 0x02),
    trusted_ca_keys((byte) 0x03),
    truncated_hmac((byte) 0x04),
    status_request((byte) 0x05),
    user_mapping((byte) 0x06),
    client_authz((byte) 0x07),
    server_authz((byte) 0x08),
    cert_type((byte) 0x09),
    supported_groups((byte) 0x0A),
    ec_point_formats((byte) 0x0B),
    srp((byte) 0x0C),
    signature_algorithms((byte) 0x0D),
    use_srtp((byte) 0x0E),
    heartbeat((byte) 0x0F);
//     TODO copy all extension type from  http://www.iana.org/assignments/tls-extensiontype-values/tls-extensiontype-values.xhtml

    private byte value;

    ExtensionType(byte value) {
        this.value = value;
    }
}
