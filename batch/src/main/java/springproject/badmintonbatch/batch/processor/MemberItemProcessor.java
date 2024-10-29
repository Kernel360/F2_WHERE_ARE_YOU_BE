package springproject.badmintonbatch.batch.processor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.badminton.domain.domain.member.entity.Member;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MemberItemProcessor implements ItemProcessor<Member, Member> {

    @Override
    public Member process(Member member) throws Exception {
        LocalDateTime lastConnectionAt = member.getLastConnectionAt();
        if (Before7Days(lastConnectionAt)) {
            return member;
        }
        return null;
    }

    private boolean Before7Days(LocalDateTime lastConnectionAt) {
        return lastConnectionAt != null && ChronoUnit.DAYS.between(lastConnectionAt, LocalDateTime.now()) > 7;
    }
}

