package com.unab.banca.Service;

import com.unab.banca.Models.Cliente;
import com.unab.banca.Dao.ClienteDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClienteService {

    @Autowired
    private ClienteDao dao;
    
    public Cliente save(Cliente objeto) {
        return dao.save(objeto);
    }

    @Transactional(readOnly=false)
    public void delete(String id) {
        dao.deleteById(id);
    }

    @Transactional(readOnly=true)
    public Cliente findById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public List<Cliente> findAll() {
        return (List<Cliente>) dao.findAll();
    }

    @Transactional(readOnly=true)
    public Cliente login(String usuario, String clave) {
        return dao.login(usuario, clave);
    }
    
}
