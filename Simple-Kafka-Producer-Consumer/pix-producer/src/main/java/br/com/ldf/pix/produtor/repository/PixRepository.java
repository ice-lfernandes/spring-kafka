package br.com.ldf.pix.produtor.repository;

import br.com.ldf.pix.produtor.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PixRepository extends JpaRepository<Pix, Integer> {

}
