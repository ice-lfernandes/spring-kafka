package br.com.ldf.pix.produtor.repository;

import br.com.ldf.pix.produtor.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
}
