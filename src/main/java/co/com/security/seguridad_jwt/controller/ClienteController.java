package co.com.security.seguridad_jwt.controller;

import co.com.security.seguridad_jwt.dto.ClienteRequest;
import co.com.security.seguridad_jwt.entity.Cliente;
import co.com.security.seguridad_jwt.services.ClienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/clientes")
public class ClienteController {

    private Logger log = LoggerFactory.getLogger(ClienteController.class);
    @Autowired
    private ClienteService clienteService;


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/listado")
    public ResponseEntity<List<Cliente>> consultarClientes() {
        try {
            List<Cliente> clientes = clienteService.obtenerClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping(value = "/crear", produces = "application/json")
    public ResponseEntity<?> clearCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        try {
            Cliente cliente = clienteService.crearcliente(clienteRequest);
            return ResponseEntity.ok(cliente);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().body("No fue posible crear el cliente");
        }
    }


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping(value = "/editar/{id}", produces = "application/json")
    public ResponseEntity<?> editarCliente(@Valid @RequestBody ClienteRequest clienteRequest, @PathVariable("id") long id) {
        try {
            Cliente cliente = clienteService.editarCliente(clienteRequest, id);
            return ResponseEntity.ok(cliente);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().body("No fue posible editar el cliente");
        }
    }
}
