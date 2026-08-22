# Jakarta Security 5.0

The `security` module demonstrates combining a web form authentication mechanism
with GlassFish's built-in MicroProfile JWT mechanism through a custom
[`HttpAuthenticationMechanismHandler`](https://jakarta.ee/specifications/security/).

- Web pages are protected by `@CustomFormAuthenticationMechanismDefinition`.
- REST endpoints (`/api/*`) are protected by GlassFish's MicroProfile JWT
  mechanism, injected via the `@MicroProfileJwtAuthenticationMechanism` qualifier
  from `org.glassfish.main.common:glassfish-api`:

```java
@Inject
@MicroProfileJwtAuthenticationMechanism
private HttpAuthenticationMechanism restAuthenticationMechanism;
```

Source: [`security/`](https://github.com/hantsy/jakartaee12-sandbox/tree/master/security).

## RSA key generation

MicroProfile JWT verifies tokens against an RSA public key. Generate a key pair
with OpenSSL:

```bash
# Generate the private key (used to sign the JWT)
openssl genrsa -out privateKey.pem 2048

# Extract the public key (bundled with the app for verification)
openssl rsa -in privateKey.pem -pubout -out publicKey.pem
```

Place `publicKey.pem` on the classpath and configure the issuer and public key
location in `src/main/resources/META-INF/microprofile-config.properties`:

```properties
mp.jwt.verify.issuer=jakartaee12-sandbox
mp.jwt.verify.publickey.location=publickey.pem
```
