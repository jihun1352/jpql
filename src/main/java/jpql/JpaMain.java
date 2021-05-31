package jpql;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.changeTeam(teamB);
            em.persist(member3);

            //em.flush();
            //em.clear();

            //FLUSH가 실행 된다.
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            // 위에서 벌크연산 후 영속성 컨텍스트를 초기화 해줌으로써
            // 아래의 em.find()는 DB에서 데이터를 가져온다.
            em.clear();

            System.out.println("resultCount = " + resultCount);
            // DB에서 업데이트된 정보를 가져오는 것이 아니라 영속성 컨텍스트에 있는 기존 age값을 가져온다
            Member findMember = em.find(Member.class, member1.getId());

            System.out.println("findMember = " + findMember.getAge());
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
