package org.brokers.guiders.web.mentoring;

import org.brokers.guiders.web.member.follower.Follower;
import org.brokers.guiders.web.member.guider.Guider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {
    List<Mentoring> findByGuiderAndFollower(Guider guider, Follower follower);

    List<Mentoring> findByGuider(Guider guider);

    List<Mentoring> findByGuiderInAndFollower(List<Guider> guiderList, Follower follower);
}
