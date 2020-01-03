package com.rf.springsecurity.repository;

import com.rf.springsecurity.domain.ports.Port;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortRepository extends JpaRepository<Port, Long> {
    //List<Port> findPortsByCruises();
}
