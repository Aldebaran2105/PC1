package com.example.pc1.user.domain;

import com.example.pc1.exceptions.ResourceNotFoundException;
import com.example.pc1.user.infrastructure.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BaseUserRepository<User> userRepository;




    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }

    /*
        public User getByEmail(String email, String role) {
        if (!"ROLE_ADMIN".equals(role)) {
        throw new AccessDeniedException("Only admins are allowed to access this method");
        }
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFound("Admin user not found"));
    }

    public UserResponseDTO getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new ResourceIsNullException("User not authenticated");
        }

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("Only admins can access this resource");
        }

        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        String role = auth.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new ResourceIsNullException("Role not found"));

        User user = getByEmail(email, role);

        return modelMapper.map(user, UserResponseDTO.class);
    }
     */

}
