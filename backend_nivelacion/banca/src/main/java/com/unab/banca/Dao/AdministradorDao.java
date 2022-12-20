package com.unab.banca.Dao;

import com.unab.banca.Models.Administrador;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdministradorDao extends CrudRepository<Administrador,String> {
    
    @Transactional(readOnly=true)
    @Query(value="SELECT * FROM administrador WHERE id_administrador= :usuario AND clave_administrador= :clave", nativeQuery=true)
    public Administrador login(@Param("usuario") String usuario, @Param("clave") String clave); 
}

