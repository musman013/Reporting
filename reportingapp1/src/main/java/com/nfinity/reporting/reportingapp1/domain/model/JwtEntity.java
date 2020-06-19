package com.nfinity.reporting.reportingapp1.domain.model; 
 
import org.hibernate.validator.constraints.Length; 
import org.springframework.data.redis.core.RedisHash; 
import org.springframework.data.redis.core.index.Indexed; 
import javax.persistence.*; 
import javax.validation.constraints.NotNull; 
import java.io.Serializable; 

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@RedisHash("JwtEntity") 
public class JwtEntity implements Serializable { 
 
	private static final long serialVersionUID = 1L;
    
    @Id 
    @Column(name = "Id", nullable = false) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
 
    @Basic 
    @Column(name = "UserName", nullable = false, length = 32) 
    @NotNull 
    @Length(max = 32, message = "Username must be less than 32 characters")
    private String userName; 
 
    @Basic 
    @Column(name = "Token", nullable = false, length=10485760) 
    @NotNull 
    @Length(max = 10485760, message = "Token must be less than 10485760 characters")
    private @Indexed String token; 
 
} 