package com.unab.banca.Controller;

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

import com.unab.banca.Dao.ClienteDao;
import com.unab.banca.Models.Cliente;
import com.unab.banca.Security.Hash;
import com.unab.banca.Service.ClienteService;


@RestController
@CrossOrigin("*")
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteDao dao; 
    @Autowired
    private ClienteService servicio;
    
    @PostMapping(value="/")
    @ResponseBody
    public ResponseEntity<Cliente> agregar(@RequestHeader("clave") final String clave,@RequestHeader("usuario")String usuario, @Validated @RequestBody Cliente dato){   
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            dato.setClave_cliente(Hash.sha1(dato.getClave_cliente()));
            return new ResponseEntity<>(servicio.save(dato), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }    
    }
   
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Cliente> eliminar(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
       if (objeto!=null) {
            Cliente obj = servicio.findById(id); 
            if(obj!=null) 
            servicio.delete(id);
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
      
       } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
    }
    
    @PutMapping(value="/") 
    @ResponseBody
    public ResponseEntity<Cliente> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Validated @RequestBody Cliente dato){ 
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            dato.setClave_cliente(Hash.sha1(dato.getClave_cliente()));
            Cliente obj = servicio.findById(dato.getId_cliente()); 
            if(obj!=null) { 
                obj.setNombre_cliente(dato.getNombre_cliente());
                obj.setClave_cliente(dato.getClave_cliente());
                servicio.save(dato); 
            } 
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }
   
    @GetMapping("/list") 
    @ResponseBody
    public ResponseEntity<List<Cliente>> consultarTodo(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            return new ResponseEntity<>(servicio.findAll(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
          
    }
    
    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Cliente> consultaPorId(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Cliente objeto=new Cliente();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            return new ResponseEntity<>(servicio.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }
    
    @GetMapping("/login")
    @ResponseBody
    public Cliente ingresar(@RequestParam ("usuario") String usuario,@RequestParam ("clave") String clave) {
        clave=Hash.sha1(clave);
        return servicio.login(usuario, clave); 
    }
}
