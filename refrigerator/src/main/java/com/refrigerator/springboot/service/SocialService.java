package com.refrigerator.springboot.service;

import com.refrigerator.springboot.attributes.OAuthAttributes;
import com.refrigerator.springboot.constant.Role;
import com.refrigerator.springboot.dto.MemberDto;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.repository.SocialRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
public class SocialService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SocialRepository userRepository;
    private final HttpSession httpSession;

    public SocialService(SocialRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new MemberDto(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());

    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member user = userRepository.findByEmailAndLoginType(attributes.getEmail(), attributes.toEntity().getLoginType()) //이메일 있는지 없는지 찾고
                .map(entity -> entity.update(attributes.getName())) // 이메일이 있으면 업데이트
                .orElse(attributes.toEntity()); // 없으면 DB에 넣기

        return userRepository.save(user); // 정보 저장
    }
}
