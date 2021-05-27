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

            Team team = new Team();
            team.setName("team");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(11);
            member.changeTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(22);
            member2.changeTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select locate('de', 'abcdefg') from Member m";

            List<Integer> result = em.createQuery(query, Integer.class)
                    .getResultList();

            for (Integer s : result) {
                System.out.println("s = " + s);
            }
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
