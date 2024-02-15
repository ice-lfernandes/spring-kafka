package br.com.ldf.pix.consumer.repository;

import br.com.ldf.pix.consumer.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PixRepository extends JpaRepository<Pix, Integer> {
    Pix findByCode(String code);
}
