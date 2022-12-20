package com.unab.banca.Service;

import com.unab.banca.Models.Cuenta;
import com.unab.banca.Dao.CuentaDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuentaService {
    @Autowired
    private CuentaDao dao;
    
    @Transactional(readOnly=false)
    public Cuenta save(Cuenta cuenta) {
        return dao.save(cuenta);
    }
    @Transactional(readOnly=false)
    public void delete(String id) {
        dao.deleteById(id);;
    }
    @Transactional(readOnly=true)
    public Cuenta findById(String id) {
       return dao.findById(id).orElse(null);
    }
    @Transactional(readOnly=true)
    public List<Cuenta> findByAll() {
        return (List<Cuenta>) dao.findAll();
    }
    @Transactional(readOnly=true)
    public List<Cuenta> consulta_cuenta(String idc) {
        return (List<Cuenta>) dao.consulta_cuenta(idc);
    }

    @Transactional(readOnly=false)
    public void deposito(String idcta,Double valor_deposito) {
        dao.deposito(idcta, valor_deposito);
    }

    @Transactional(readOnly=false)
    public void retiro(String idcta,Double valor_retiro) {
        dao.retiro(idcta, valor_retiro);
    }
}
