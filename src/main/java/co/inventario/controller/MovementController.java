package co.inventario.controller;

import co.inventario.dto.globalDto.MensajeDTO;
import co.inventario.dto.moveDto.NewMovementDTO;
import co.inventario.model.documents.Movement;
import co.inventario.services.interfaces.MovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movement")
public class MovementController {

    private final MovementService movementService;


    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping("/create")
    public ResponseEntity<MensajeDTO<Movement>> movement(@RequestBody NewMovementDTO newMovementDTO){
        Movement mov= movementService.newMovement(newMovementDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false,mov));
    }

    @GetMapping("/getAll")
    public ResponseEntity<MensajeDTO<List<Movement>>> getAllMovements(){
        List<Movement> movs= movementService.getAllMovements();
        return ResponseEntity.ok(new MensajeDTO<>(false,movs));
    }



}
