// Copyright 2018 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.athenz.client.zts.bindings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yahoo.vespa.athenz.tls.X509CertificateUtils;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.time.Instant;

/**
 * @author bjorncs
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleCertificateResponseEntity {
    public final X509Certificate certificate;
    public final Instant expiry;

    @JsonCreator
    public RoleCertificateResponseEntity(@JsonProperty("token") @JsonDeserialize(using = X509CertificateDeserializer.class) X509Certificate certificate,
                                         @JsonProperty("expiryTime") Instant expiry) {
        this.certificate = certificate;
        this.expiry = expiry;
    }

    public static class X509CertificateDeserializer extends JsonDeserializer<X509Certificate> {
        @Override
        public X509Certificate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            return X509CertificateUtils.fromPem(parser.getValueAsString());
        }
    }
}
