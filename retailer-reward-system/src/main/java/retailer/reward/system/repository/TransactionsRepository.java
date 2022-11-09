package retailer.reward.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionsRepository extends JpaRepository<TransactionsEntity, Long> {

   @Query(value = "SELECT * FROM TRANSACTIONS t where t.USER_ID = :userId", nativeQuery = true)
    List<TransactionsEntity> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT SUM(t.REWARD_POINTS) FROM TRANSACTIONS t where t.USER_ID = :userId and t.CREATED_DATE = :createdDate group by p.USER_ID", nativeQuery = true)
    Long findTotalRewardPoints(@Param("userId") Long userId, @Param("createdDate") LocalDate createdDate);

    @Query(value = "SELECT SUM(t.REWARD_POINTS) FROM TRANSACTIONS t where t.USER_ID = :userId and t.CREATED_DATE >= :toDate and t.CREATED_DATE <= :fromDate group by t.USER_ID", nativeQuery = true)
    Long findTotalFrequencyRewardPoints(@Param("userId") Long userId,
                                        @Param("fromDate") LocalDate fromDate,
                                        @Param("toDate") LocalDate toDate);

    @Query(value = "DELETE FROM TRANSACTIONS t where t.CREATED_DATE < :fromDate", nativeQuery = true)
    Long deleteByCreatedDateBefore(@Param("fromDate") LocalDate fromDate);


}
