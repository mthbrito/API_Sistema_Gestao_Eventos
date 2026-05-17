package ifpb.app_sistema_gestao_eventos.security;

import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String gerarToken(UserDetails userDetails) {
        UsuarioDetails usuarioDetails = (UsuarioDetails) userDetails;
        Usuario usuario = usuarioDetails.getUsuario();
        String role = usuario.getPerfis().get(0).getNome().name();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("id", usuario.getId())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean tokenValido(String token, UserDetails userDetails) {
        Claims claims = extrairClaims(token);
        String email = claims.getSubject();
        Date expiracao = claims.getExpiration();
        return email.equals(userDetails.getUsername()) && expiracao.after(new Date());
    }
}