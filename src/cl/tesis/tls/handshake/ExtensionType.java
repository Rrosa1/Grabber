package cl.tesis.tls.handshake;


import java.util.Arrays;

public enum ExtensionType {
    SERVER_NAME("server_name", new byte[] {(byte)0x00, (byte) 0x00}),
    MAX_FRAGMENT_LENGTH("max_fragment_length", new byte[] {(byte) 0x00, (byte) 0x01}),
    CLIENT_CERTIFICATE_URL("client_certificate_url", new byte[] {(byte) 0x00, (byte) 0x02}),
    TRUSTED_CA_KEYS("trusted_ca_keys", new byte[] {(byte) 0x00, (byte) 0x03}),
    TRUNCATED_HMAC("truncated_hmac", new byte[] {(byte) 0x00, (byte) 0x04}),
    STATUS_REQUEST("status_request", new byte[] {(byte) 0x00, (byte) 0X05}),
    USER_MAPPING("user_mapping", new byte[] {(byte) 0x00, (byte) 0x06}),
    CLIENT_AUTHZ("client_authz", new byte[] {(byte) 0x00, (byte) 0x07}),
    SERVER_AUTHZ("server_authz", new byte[] {(byte) 0x00, (byte) 0x08}),
    CERT_TYPE("cert_type", new byte[] {(byte) 0x00, (byte) 0x09}),
    SUPPORTED_GROUPS("supported_groups", new byte[] {(byte) 0x00, (byte) 0x0A}),
    EC_POINT_FORMATS("ec_point_formats", new byte[] {(byte) 0x00, (byte) 0x0B}),
    SRP("srp", new byte[] {(byte) 0x00, (byte) 0x0C}),
    SIGNATURE_ALGORITHMS("signature_algorithms", new byte[] {(byte) 0x00, (byte) 0x0D}),
    USE_SRTP("use_srtp", new byte[] {(byte) 0x00, (byte) 0x0E}),
    HEARTBEAT("heartbeat", new byte[] {(byte) 0x00, (byte) 0x0F}),
    APPLICATION_LAYER_PROTOCOL_NEGOTIATION("application_layer_protocol_negotiation", new byte[] {(byte) 0x00, (byte) 0x10}),
    STATUS_REQUEST_V2("status_request_v2", new byte[] {(byte) 0x00, (byte) 0x11}),
    SIGNED_CERTIFICATE_TIMESTAMP("↑", new byte[] {(byte) 0x00, (byte) 0x12}),
    CLIENT_CERTIFICATE_TYPE("client_certificate_type", new byte[] {(byte) 0x00, (byte) 0x13}),
    SERVER_CERTIFICATE_TYPE("server_certificate_type", new byte[] {(byte) 0x00, (byte) 0x14}),
    PADDING("padding", new byte[] {(byte) 0x00, (byte) 0x15}),
    ENCRYPT_THEN_MAC("encrypt_then_mac", new byte[] {(byte) 0x00, (byte) 0x16}),
    EXTENDED_MASTER_SECRET("extended_master_secret", new byte[] {(byte) 0x00, (byte) 0x17}),
    SESSION_TICKET_TLS("session_ticket_tls", new byte[] {(byte) 0x00, (byte) 0x23}),
    RENEGOTIATION_INFO("renegotiation_info", new byte[] {(byte) 0xFF, (byte) 0x01});

    private String name;
    private byte[] value;


    ExtensionType(String name, byte[] value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public byte[] getValue() {
        return value;
    }

    public static String getNameByByte(byte[] version) {
        for (ExtensionType e : ExtensionType.values()) {
            if (Arrays.equals(version, e.getValue())) {
                return e.getName();
            }
        }
        return null;
    }
}
