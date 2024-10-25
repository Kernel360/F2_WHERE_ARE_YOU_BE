package springproject.badmintonbatch.batch.reader;

import java.util.List;

import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.infrastructures.member.repository.MemberRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemReader implements ItemReader<Member> {

    private final MemberRepository memberRepository;
    private List<Member> membersToDelete;

    @Override
    public Member read() {
        if (membersToDelete == null || membersToDelete.isEmpty()) {
            membersToDelete = memberRepository.findAllByIsDeletedTrue();
        }
        return membersToDelete.isEmpty() ? null : membersToDelete.remove(0);
    }
}
