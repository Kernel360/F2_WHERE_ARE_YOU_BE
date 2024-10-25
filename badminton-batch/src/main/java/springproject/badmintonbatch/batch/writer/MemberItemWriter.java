package springproject.badmintonbatch.batch.writer;

import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.infrastructures.member.repository.MemberRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemWriter implements ItemWriter<Member> {

    private final MemberRepository memberRepository;

    @Override
    public void write(Chunk<? extends Member> members) throws Exception {
        for (Member member : members) {
            if (member != null) {
                memberRepository.delete(member);
            }
        }
    }
}
