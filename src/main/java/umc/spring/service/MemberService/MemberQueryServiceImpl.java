package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.GeneralException;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.ReviewRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService{
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Optional<Member> findMember(Long id) {
        return memberRepository.findById(id);
    }
    @Override
    public Page<Review> getMyReviews(Long memberId, int page) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return reviewRepository.findAllByMember(member,
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
