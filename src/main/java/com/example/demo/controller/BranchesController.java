package com.example.demo.controller;

import com.example.demo.model.Branches;
import com.example.demo.service.BranchesService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // RestController para respuestas en JSON
@RequestMapping("/api/branches")
//@CrossOrigin(origins = "http://localhost:3000") // Descomentar cuando llevemos a prod
public class BranchesController {

    private final BranchesService branchesService;

    public BranchesController(BranchesService branchService) {
        this.branchesService = branchService;
    }

    /**
     * Obtiene la lista completa de sucursales.
     * @return ResponseEntity con la lista de todas las sucursales en formato JSON.
     */
    @GetMapping
    public ResponseEntity<List<Branches>> listBranches() {
        List<Branches> branches = branchesService.getAllBranches();
        return ResponseEntity.ok(branches); // Retorna la lista de sucursales como JSON
    }

    /**
     * Obtiene los detalles de una sucursal específica por su ID. Disponible solo en entorno de producción.
     * @param id ID de la sucursal a visualizar.
     * @return ResponseEntity con los detalles de la sucursal si se encuentra, o un mensaje de error si no existe.
     */
    @Profile("prod")
    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewBranch(@PathVariable Long id) {
        Branches branch = branchesService.getBranchById(id);
        if (branch == null) {
            return ResponseEntity.status(404).body("Branch not found.");
        }
        return ResponseEntity.ok(branch); // Retorna los detalles de la sucursal como JSON
    }

    /**
     * Crea una nueva sucursal si se proporciona la ubicación.
     * @param branch Objeto Branches que contiene la información de la nueva sucursal.
     * @return ResponseEntity con mensaje de éxito si la creación es exitosa o mensaje de error en caso de datos incompletos.
     * @throws Exception en caso de error durante el proceso de creación.
     */
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Branches branch) throws Exception {
        if(branch.getLocation() == null || branch.getLocation().isEmpty()) {
            return ResponseEntity.status(400).body("Incomplete set of data.");
        }
        branchesService.createBranch(branch.getLocation());
        return ResponseEntity.ok("Branch created successfully.");
    }

    /**
     * Actualiza los datos de una sucursal existente por su ID.
     * @param id ID de la sucursal a actualizar.
     * @param updatedBranch Objeto Branches con los nuevos datos de la sucursal.
     * @return ResponseEntity con mensaje de éxito si la actualización es exitosa o mensaje de error si la sucursal no existe.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBranch(@PathVariable Long id, @RequestBody Branches updatedBranch) {
        // Obtiene la sucursal existente
        Branches branch = branchesService.getBranchById(id);
        if (branch == null) {
            return ResponseEntity.status(400).body("Branch not found.");
        }

        // Actualiza los detalles de la sucursal
        branch.setLocation(updatedBranch.getLocation());

        // Guarda la sucursal actualizada
        branchesService.saveBranch(branch);

        return ResponseEntity.ok("Branch updated successfully.");
    }

    /**
     * Elimina una sucursal existente por su ID.
     * @param id ID de la sucursal a eliminar.
     * @return ResponseEntity con mensaje de éxito si la eliminación es exitosa o mensaje de error si la sucursal no existe.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long id) {
        // Verifica si la sucursal existe
        if (branchesService.getBranchById(id) == null) {
            return ResponseEntity.status(400).body("Branch not found.");
        }

        branchesService.deleteBranch(id);

        return ResponseEntity.ok("Branch deleted successfully.");
    }
}
