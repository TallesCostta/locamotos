package br.com.donatti.locamotos.moto.repository;

import br.com.donatti.locamotos.moto.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long>
{
    List<Moto> findByModeloContainingIgnoreCase(String modelo);

    Moto findByPlacaContainingIgnoreCase(String placa);
}