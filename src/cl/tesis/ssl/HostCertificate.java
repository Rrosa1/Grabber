package cl.tesis.ssl;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HostCertificate extends Certificate implements CSVWritable, JsonWritable{
    private String ip;
    private boolean validation;
    private String certificateAuthority;
    private List<Certificate> chainAuthority;

    public HostCertificate(X509Certificate x509Certificate, String ip, boolean validation, X509Certificate[] chain) {
        super(x509Certificate);
        Map<String, String> issuerMap = parserX500Principal(x509Certificate.getIssuerX500Principal());

        this.ip = ip;
        this.validation = validation;
        this.certificateAuthority = issuerMap.get("CN");
        this.chainAuthority = parseChain(chain);
    }

    private List<Certificate> parseChain(X509Certificate[] chain) {
        if (chain == null) {
            return null;
        }

        ArrayList<Certificate> chainAuthority = new ArrayList<>();

        for (X509Certificate cert: chain) {
            chainAuthority.add(new Certificate(cert));
        }

        return chainAuthority;
    }

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("Ip");
        parameters.add("Validation");
        parameters.add("Signature Algorithm");
        parameters.add("Expired Time");
        parameters.add("Organization Name");
        parameters.add("Organization URL");
        parameters.add("Certificate Authority");
        parameters.add("Key Bits");
        parameters.add("PEM Certificate");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values = new ArrayList<>();

        values.add(this.ip);
        values.add(this.validation + "");
        values.add(this.getSignatureAlgorithm());
        values.add(this.getExpiredTime().toString());
        values.add(this.getOrganizationName());
        values.add(this.getOrganizationURL());
        values.add(this.certificateAuthority);
        values.add(this.getKeyBits() + "");
        values.add(this.getPemCert());

        return values;
    }

    @Override
    public String toString() {
        return "HostCertificate{" +
                "ip='" + ip + '\'' +
                ", validation=" + validation +
                ", certificateAuthority='" + certificateAuthority + '\'' +
                ", chainAuthority=" + chainAuthority +
                "} " + super.toString();
    }
}
