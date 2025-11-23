package co.inventario.controller;

import co.inventario.dto.globalDto.MensajeDTO;
import co.inventario.dto.moveDto.NewMovementDTO;
import co.inventario.model.documents.Movement;
import co.inventario.model.documents.Product;
import co.inventario.services.interfaces.MovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "localhost",  allowCredentials = "true")
@RestController
@RequestMapping("/api/movement")
public class MovementController {

    private final MovementService movementService;


    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping("/mew")
    public ResponseEntity<MensajeDTO<Movement>> in(@RequestBody NewMovementDTO newMovementDTO){
        Movement mov= movementService.newMovement(newMovementDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false,mov));
    }



}
