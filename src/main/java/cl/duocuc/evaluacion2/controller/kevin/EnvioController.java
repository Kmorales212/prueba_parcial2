package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
public class EnvioController {

 @Autowired
    private EnvioService envioService;

    @GetMapping
    public List<EnvioModelo> getAllEnvios() {
        return envioService.getAllEnvios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioModelo> getEnvioById(@PathVariable String id) {
        return envioService.getEnvioById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EnvioModelo createEnvio(@RequestBody EnvioModelo envio) {
        return envioService.createEnvio(envio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioModelo> updateEnvio(@PathVariable String id, @RequestBody EnvioModelo envioDetails) {
        return envioService.updateEnvio(id, envioDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable String id) {
        boolean deleted = envioService.deleteEnvio(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


}