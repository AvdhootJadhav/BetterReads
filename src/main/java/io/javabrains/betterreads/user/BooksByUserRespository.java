package io.javabrains.betterreads.user;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksByUserRespository extends CassandraRepository<BooksByUser,String> {
    

    Slice<BooksByUser> findAllById(String userId, Pageable pageable);
}
