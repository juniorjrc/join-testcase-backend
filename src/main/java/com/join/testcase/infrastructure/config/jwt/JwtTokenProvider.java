package com.join.testcase.infrastructure.config.jwt;

import com.join.testcase.infrastructure.exceptions.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

	public static final String INVALID_OR_EXPIRED_TOKEN = "Invalid or expired token!!";

	@Value("${security.jwt.token.secret-key:joinSecretKey123}")
	private String secretKey;

	@Value("${security.jwt.token.expire-lenght:28800000}")
	private long validityInMilliseconds;

	private final UserDetailsService userDetailService;

	@PostConstruct
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String userName, Collection<? extends GrantedAuthority> roles) {
		Claims claims = Jwts.claims().setSubject(userName);
		claims.put("roles", roles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailService.loadUserByUsername(getUserName(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getUserName(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException(INVALID_OR_EXPIRED_TOKEN);
		}
	}

}
