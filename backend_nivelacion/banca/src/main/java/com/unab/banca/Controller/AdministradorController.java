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

import com.unab.banca.Dao.AdministradorDao;
import com.unab.banca.Models.Administrador;
import com.unab.banca.Security.Hash;
import com.unab.banca.Service.AdministradorService;


@RestController
@CrossOrigin("*")
@RequestMapping("/administrador")
public class AdministradorController {
    
    @Autowired
    private AdministradorDao dao; 
    @Autowired
    private AdministradorService servicio;
    
    @PostMapping(value="/")
    @ResponseBody
    public ResponseEntity<Administrador> agregar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario, @Validated @RequestBody Administrador dato){   
        Administrador objeto=new Administrador();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            return new ResponseEntity<>(servicio.save(dato), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
            
    }
   
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Administrador> eliminar(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador objeto=new Administrador();
        objeto=dao.login(usuario, Hash.sha1(clave));
       if (objeto!=null) {
            Administrador obj = servicio.findById(id); 
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
    public ResponseEntity<Administrador> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Validated @RequestBody Administrador dato){ 
        Administrador objeto=new Administrador();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            dato.setClave_administrador(Hash.sha1(dato.getClave_administrador()));
            Administrador obj = servicio.findById(dato.getId_administrador()); 
            if(obj!=null) { 
                obj.setNombre_administrador(dato.getNombre_administrador());
                obj.setClave_administrador(dato.getClave_administrador());
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
    public ResponseEntity<List<Administrador>> consultarTodo(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){
        Administrador objeto=new Administrador();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            return new ResponseEntity<>(servicio.findAll(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
          
    }
    
    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Administrador> consultaPorId(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador objeto=new Administrador();
        objeto=dao.login(usuario, Hash.sha1(clave));
        if (objeto!=null) {
            return new ResponseEntity<>(servicio.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }
    
    @GetMapping("/login")
    @ResponseBody
    public Administrador ingresar(@RequestParam ("usuario") String usuario,@RequestParam ("clave") String clave) {
        clave=Hash.sha1(clave);
        return servicio.login(usuario, clave);
    }
}

