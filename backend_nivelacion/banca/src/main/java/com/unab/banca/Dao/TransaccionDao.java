package com.unab.banca.Dao;

import com.unab.banca.Models.Transaccion;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransaccionDao extends CrudRepository< Transaccion, Integer> {
     
     @Transactional(readOnly=true)
     @Query(value="SELECT * FROM transaccion WHERE id_cuenta= :idcta", nativeQuery=true)
     public List<Transaccion> consulta_transaccion(@Param("idcta") String idcta); 
   
     @Transactional(readOnly=false)
     @Modifying
     @Query(value="INSERT INTO transaccion(fecha_transaccion,valor_transaccion,tipo_transaccion,id_cuenta) VALUES (current_date(), :valor_transaccion, :tipo, :idcta)", nativeQuery=true)
     public void crear_transaccion(@Param("idcta") String idcta,@Param("valor_transaccion") Double valor_transaccion,@Param("tipo") String tipo);
 }