package cl.tesis.ssl;

import cl.tesis.input.ListWritable;

import javax.security.auth.x500.X500Principal;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Certificate implements ListWritable{
    private String ip;
    private boolean validation;
    private String sigantureAlgorithm;
    private Date expiredTime;
    private String organizationName;
    private String organizationURL;
    private String certificateAuthority;
    private String keyBits;


    public Certificate(X509Certificate x509Certificate, String ip, boolean validation) {
        Map<String, String> subjectMap = parserX500Principal(x509Certificate.getSubjectX500Principal());
        Map<String, String> issuerMap = parserX500Principal(x509Certificate.getIssuerX500Principal());

        this.ip = ip;
        this.validation = validation;
        this.sigantureAlgorithm = x509Certificate.getSigAlgName();
        this.expiredTime = x509Certificate.getNotAfter();
        this.organizationName = subjectMap.get("O");
        this.organizationURL = subjectMap.get("CN");
        this.certificateAuthority = issuerMap.get("CN");
        this.keyBits = parserPublicKeyBits(x509Certificate);
    }

    private Map<String, String> parserX500Principal(X500Principal x500Principal) {
        String[] values = x500Principal.getName().split(",");

        Map<String, String> principalMap = new HashMap<>();

        for (String value : values) {
            String[] elements = value.split("=");
            principalMap.put(elements[0], elements[1]);
        }

        return principalMap;
    }

    private String parserPublicKeyBits(X509Certificate x509Certificate) {
        String[] publicKey = x509Certificate.getPublicKey().toString().split("[\r\n]+");
        Pattern pattern =  Pattern.compile("(.*), (.*) bits");
        Matcher matcher = pattern.matcher(publicKey[0]);
        boolean success = matcher.find();

        if (success) {
            return matcher.group(2);
        }
        return null;


    }

    @Override
    public String toString() {
        return "Certificate{" +
                "ip='" + ip + '\'' +
                ", validation=" + validation +
                ", sigantureAlgorithm='" + sigantureAlgorithm + '\'' +
                ", expiredTime=" + expiredTime +
                ", organizationName='" + organizationName + '\'' +
                ", organizationURL='" + organizationURL + '\'' +
                ", certificateAuthority='" + certificateAuthority + '\'' +
                ", keyBits='" + keyBits + '\'' +
                '}';
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

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values = new ArrayList<>();

        values.add(this.ip);
        values.add(this.validation + "");
        values.add(this.sigantureAlgorithm);
        values.add(this.expiredTime.toString());
        values.add(this.organizationName);
        values.add(this.organizationURL);
        values.add(this.certificateAuthority);
        values.add(this.keyBits + "");

        return values;

    }
}
