package ua.opnu.practice1_template.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.User;
import ua.opnu.practice1_template.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);

        String email = oAuth2User.getAttribute("email");

        Optional<User> existingUser = userRepository.findByUsername(email);
        if (existingUser.isEmpty()) {
            User user = User.builder()
                    .username(email)
                    .password(passwordEncoder.encode("oauth2user"))
                    .role("USER")
                    .build();
            userRepository.save(user);
        }

        return oAuth2User;
    }
}
