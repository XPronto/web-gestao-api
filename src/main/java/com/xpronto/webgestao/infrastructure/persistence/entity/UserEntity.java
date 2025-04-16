package com.xpronto.webgestao.infrastructure.persistence.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.xpronto.webgestao.utils.GeneratedUuidV7;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedUuidV7
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "email_confirmed")
    private boolean verified;

    @Column(name = "password_hash")
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;

    @ManyToMany
    @JoinTable(name = "user_permission_sets", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_set_id"))
    private List<PermissionSetEntity> permissionSets = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapAuthorities(UserEntity.mapPermissionsCodes(permissionSets));
    }

    public static Collection<? extends GrantedAuthority> mapAuthorities(List<String> permissions) {
        return permissions.stream().map(code -> new SimpleGrantedAuthority(code)).toList();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    private static List<String> mapPermissionsCodes(List<PermissionSetEntity> permissionSets) {
        return permissionSets.stream()
                .flatMap((permissionSet -> permissionSet.getPermissions().stream()
                        .map(permission -> permission.getCode())))
                .toList();
    }
}
