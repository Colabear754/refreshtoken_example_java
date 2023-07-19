package com.colabear754.refreshtoken_example_java.service;

import com.colabear754.refreshtoken_example_java.dto.sign_in.request.SignInRequest;
import com.colabear754.refreshtoken_example_java.dto.sign_in.response.SignInResponse;
import com.colabear754.refreshtoken_example_java.dto.sign_up.request.SignUpRequest;
import com.colabear754.refreshtoken_example_java.dto.sign_up.response.SignUpResponse;
import com.colabear754.refreshtoken_example_java.entity.Member;
import com.colabear754.refreshtoken_example_java.entity.MemberRefreshToken;
import com.colabear754.refreshtoken_example_java.repository.MemberRefreshTokenRepository;
import com.colabear754.refreshtoken_example_java.repository.MemberRepository;
import com.colabear754.refreshtoken_example_java.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignService {
    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    @Transactional
    public SignUpResponse registMember(SignUpRequest request) {
        Member member = memberRepository.save(Member.from(request, encoder));
        try {
            memberRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        return SignUpResponse.from(member);
    }

    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        Member member = memberRepository.findByAccount(request.account())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getId(), member.getType()));
        String refreshToken = tokenProvider.createRefreshToken();
        memberRefreshTokenRepository.findById(member.getId())
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        () -> memberRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
                );
        return new SignInResponse(member.getName(), member.getType(), accessToken, refreshToken);
    }
}
