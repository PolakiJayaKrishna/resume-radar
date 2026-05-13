package com.jay.resumeradar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}") //Make Sure this Value is not a Lombok annotation
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String generateToken (UserDetails userDetails){
        Date now = new Date();
        Date expiryDate =new Date(now.getTime() + jwtExpiration);
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities()) //When the user logs in, the Frontend decodes the token. If it sees "role": "MANAGER", it shows the "Approve Leave" button. If it sees "EMPLOYEE", it hides it.
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    //Converts the String Password to the binary form
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    //3.Exact UserName
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public <G> G extractClaim(String token, Function<Claims, G> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

                                                                                    /*
                                                                                    <G> ->Generic Type Parameter[It tells Java: I don't know what type of data we are returning yet. It could be anything. Let's call this mystery type 'G'.]
                                                                                    G   ->This method will return a value of type 'G'.
                                                                                    Function<Claims, G >                    Input : Claims (The map of data: {sub: "John", exp: 12345} and Output: G (The specific piece you want).
                                                                                                |    |
                                                                                                v    v
                                                                                    Function<Input, Output>
                                                                                     */
    //5.THE GOGGLES.[Open the Box]
     private Claims extractAllClaims(String token) {
        return Jwts.parser()                                                //open the Box
                .verifyWith((SecretKey) getSignInKey())                     // Load our secret key to verify the signature
                .build()                                                    // If passes combine it
                .parseSignedClaims(token)                                   //Verify signature + decode the 3 parts
                .getPayload();                                              // Take only data from the Claims
    }


    //4.Logic Important
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Use your "exactUsername" method here
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Helper: Check if today is after the expiration date
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Helper: Get the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
