package br.com.projeto.descarteResiduo.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.descarteResiduo.model.Point;
import br.com.projeto.descarteResiduo.service.PointService;

@RestController
@RequestMapping("/descarteResiduo")
public class DecarteResiduoController {
	
	@Autowired
    private PointService pointService;
	
	@PostMapping
    public ResponseEntity<Point> pointCreate(@RequestBody Point point) throws SQLException {
		try {
            Point createdPonto = pointService.pointCreate(point);
            return new ResponseEntity<>(createdPonto, HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    @GetMapping
    public ResponseEntity<Iterable<Point>> pointRead() throws SQLException {
        return ResponseEntity.ok(pointService.pointRead());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Point> pointUpdate(@PathVariable Long id, @RequestBody Point pointDetails) throws SQLException {
        Point updatedPonto = pointService.pointUpdate(id, pointDetails);

        if (updatedPonto != null) {
            return ResponseEntity.ok(updatedPonto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> pointDelete(@PathVariable Long id) throws SQLException {
    	pointService.pointDelete(id);
        return ResponseEntity.noContent().build();
    }
       
    @GetMapping("/search")
    public ResponseEntity<Iterable<Point>> pointSearch(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String endereco,
            @RequestParam(required = false) String zona,
            @RequestParam(required = false) String contatos,
            @RequestParam(required = false) String horarioExpediente) throws SQLException {
        return ResponseEntity.ok(pointService.pointSearch(nome, endereco, zona, contatos, horarioExpediente));
    }
}
