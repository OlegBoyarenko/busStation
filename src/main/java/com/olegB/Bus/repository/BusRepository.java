package com.olegB.Bus.repository;


import com.olegB.Bus.model.Bus;
import com.olegB.Bus.util.Type;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    @Modifying
    @Transactional
    @Query("delete from Bus b where b.type=:type and b.startTime=:startTime and b.endTime=:endTime")
    void deleteBus(@Param("type") Type type,
                   @Param("startTime") String startTime,
                   @Param("endTime") String endTime);
}
