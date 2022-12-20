package com.unab.banca.Controller;

import com.unab.banca.Dao.ClienteDao;
import com.unab.banca.Models.Cliente;
import com.unab.banca.Models.Cuenta;
import com.unab.banca.Security.Hash;
import com.unab.banca.Service.CuentaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/cuenta")
public class CuentaController {
  
    @Autowired
    private ClienteDao dao;

    @Autowired
    private CuentaService servicio;
    
    @PostMapping(value="/")
    public ResponseEntity<Cuenta> agregar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Validated@RequestBody Cuenta dato){   
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            return new ResponseEntity<>(servicio.save(dato), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }  
    }
    
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Cuenta> eliminar(@PathVariable String id){ 
        Cuenta objeto = servicio.findById(id); 
        if(objeto!=null) 
        servicio.delete(id); 
        else 
            return new ResponseEntity<>(objeto, HttpStatus.INTERNAL_SERVER_ERROR); 
        return new ResponseEntity<>(objeto, HttpStatus.OK); 
    }

    @PutMapping(value="/") 
    public ResponseEntity<Cuenta> editar(@RequestBody Cuenta dato){ 
        Cuenta objeto = servicio.findById(dato.getId_cuenta()); 
        if(objeto!=null) {
            
            objeto.setFecha_apertura(dato.getFecha_apertura());
            objeto.setSaldo_cuenta(dato.getSaldo_cuenta());
            objeto.setCliente(dato.getCliente());
            
            servicio.save(objeto);
        } 
        else 
            return new ResponseEntity<>(objeto, HttpStatus.INTERNAL_SERVER_ERROR); 
        return new ResponseEntity<>(objeto, HttpStatus.OK); 
    }

    @PutMapping(value="/deposito") 
    public void deposito(@RequestParam ("idcta") String idcta,@RequestParam ("valor_deposito") Double valor_deposito,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            servicio.deposito(idcta, valor_deposito); 
        } 
    }

    @PutMapping(value="/retiro") 
    public void retiro(@RequestParam ("idcta") String idcta,@RequestParam ("valor_retiro") Double valor_retiro,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            servicio.retiro(idcta, valor_retiro); 
        }  
    }

    @GetMapping("/list")
    public List<Cuenta> consultarTodo(){
        return servicio.findByAll(); 
    }

    @GetMapping("/list/{id}") 
    public Cuenta consultaPorId(@PathVariable String id){ 
        return servicio.findById(id); 
    }

    @GetMapping("/consulta_cuenta")
    @ResponseBody
    public ResponseEntity<List<Cuenta>> consulta_cuenta(@RequestParam ("idc") String idc,@RequestHeader ("usuario") String usuario,@RequestHeader ("clave") String clave) { 
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            return new ResponseEntity<>(servicio.consulta_cuenta(idc),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
