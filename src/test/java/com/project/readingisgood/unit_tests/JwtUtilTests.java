package com.project.readingisgood.unit_tests;

import com.project.readingisgood.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtUtilTests {

    @MockBean
    JwtUtil jwtUtil;

    private String secret = "jwt_key";

    @Test
    void extractUsername_test_method_should_extract_username(){
        String expectedUserName = "mertcakmak2@gmail.com";
        String token = getSignedJwt(expectedUserName);
        Mockito.when(jwtUtil.extractUsername(token)).thenReturn(expectedUserName);
        String userName = jwtUtil.extractUsername(token);

        assertEquals(expectedUserName, userName);
    }

    @Test
    void extractExpiration_test_method_should_extract_expiration(){
        String expectedUserName = "mertcakmak2@gmail.com";
        String token = getSignedJwt(expectedUserName);
        Date expectedDate = new Date();
        Mockito.when(jwtUtil.extractExpiration(token)).thenReturn(expectedDate);
        Date date = jwtUtil.extractExpiration(token);

        assertNotNull(date);
    }

    @Test
    void extractAllClaims_test_method_should_extract_all_claims(){
        String expectedUserName = "mertcakmak2@gmail.com";
        String token = getSignedJwt(expectedUserName);
        var expectedClaims = extractAllClaims(token);
        Mockito.when(jwtUtil.extractAllClaims(token)).thenReturn(expectedClaims);
        var claims = jwtUtil.extractAllClaims(token);

        assertEquals(expectedUserName,claims.get("username"));
    }

    @Test
    void isTokenExpired_test_method_should_check_token_expire(){
        String expectedUserName = "mertcakmak2@gmail.com";
        String token = getSignedJwt(expectedUserName);
        var expectedTokenExpiredState = true;
        Mockito.when(jwtUtil.isTokenExpired(token)).thenReturn(expectedTokenExpiredState);
        var isTokenExpired = jwtUtil.isTokenExpired(token);

        assertEquals(expectedTokenExpiredState, isTokenExpired);
    }

    @Test
    void generateToken_test_method_should_generate_token(){
        String userName = "mertcakmak2@gmail.com";
        String expectedJwt = getSignedJwt(userName);
        Mockito.when(jwtUtil.generateToken(userName)).thenReturn(expectedJwt);
        var jwt = jwtUtil.generateToken(userName);

        assertEquals(expectedJwt, jwt);
    }

    @Test
    void createToken_test_method_should_create_token(){
        String userName = "mertcakmak2@gmail.com";
        String expectedJwt = getSignedJwt(userName);
        var expectedClaims = extractAllClaims(expectedJwt);
        Mockito.when(jwtUtil.createToken(expectedClaims,userName)).thenReturn(expectedJwt);
        var jwt = jwtUtil.createToken(expectedClaims,userName);

        assertEquals(expectedJwt, jwt);
    }

    @Test
    void getOneDayDate_test_method_should_get_date(){
        Mockito.when(jwtUtil.getOneDayDate()).thenReturn(new Date());
        var oneDayDate = jwtUtil.getOneDayDate();

        assertNotNull(oneDayDate);
    }

    @Test
    void validateToken_test_method_should_validate_token(){
        String userName = "mertcakmak2@gmail.com";
        String jwt = getSignedJwt(userName);
        Mockito.when(jwtUtil.validateToken(jwt,userName)).thenReturn(true);
        var isValidate = jwtUtil.validateToken(jwt,userName);

        assertTrue(isValidate);
    }

    String getSignedJwt(String subject){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",subject);

        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    Claims extractAllClaims(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }
}
