package com.example.demo.controller;

import com.example.demo.model.Branches;
import com.example.demo.model.Movies;
import com.example.demo.model.Rooms;
import com.example.demo.service.MoviesService;
import com.example.demo.service.ScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
// @CrossOrigin(origins = "http://localhost:3000") // Uncomment for production
public class MoviesController {

    private final MoviesService moviesService;
    private final ScreeningService screeningService;

    public MoviesController(MoviesService moviesService, ScreeningService screeningService) {
        this.moviesService = moviesService;
        this.screeningService = screeningService;
    }

    /**
     * Obtiene la lista completa de películas.
     * @return ResponseEntity con la lista de todas las películas en formato JSON.
     */
    @GetMapping
    public ResponseEntity<List<Movies>> listMovies() {
        List<Movies> movies = moviesService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    /**
     * Obtiene la lista de películas actualmente disponibles.
     * @return ResponseEntity con la lista de películas disponibles en formato JSON.
     */
    @GetMapping("/currently-available")
    public ResponseEntity<List<Movies>> listAvailableMovies() {
        List<Movies> availableMovies = moviesService.getAllAvailableMovies();
        return ResponseEntity.ok(availableMovies);
    }

    /**
     * Obtiene los detalles de una película específica por su ID.
     * @param id ID de la película a buscar.
     * @return ResponseEntity con los detalles de la película o un mensaje de error si no se encuentra.
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewMovie(@PathVariable Long id) {
        Movies movie = moviesService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * Crea una nueva película si se proporciona el conjunto de datos completo.
     * @param movie Objeto Movies con los datos de la película.
     * @return ResponseEntity con mensaje de éxito si la creación es exitosa o mensaje de error en caso de datos incompletos.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createMovie(@RequestBody Movies movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty() || movie.getDescription() == null || movie.getDescription().isEmpty() ||
                movie.getGenre() == null || movie.getGenre().isEmpty() || movie.getDuration() == 0 || movie.getDirector() == null ||
                movie.getDirector().isEmpty() || movie.getCasting() == null || movie.getCasting().isEmpty() || movie.getReleaseDate() == null ||
                movie.getLanguagesAvailable() == null || movie.getLanguagesAvailable().isEmpty() || movie.getRating() == null ||
                movie.getRating().isEmpty() || movie.getDistributor() == null || movie.getDistributor().isEmpty() || movie.getDimensionsAvailable() == null ||
                movie.getDimensionsAvailable().isEmpty() || movie.getVerticalPosterBASE64() == null || movie.getVerticalPosterBASE64().isEmpty() ||
                movie.getHorizontalPosterBASE64() == null || movie.getHorizontalPosterBASE64().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete set of data.");
        }

        moviesService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body("Movie created successfully.");
    }

    /**
     * Actualiza los datos de una película existente.
     * @param id ID de la película a actualizar.
     * @param updatedMovie Objeto Movies con los datos actualizados de la película.
     * @return ResponseEntity con mensaje de éxito si la actualización es exitosa o mensaje de error si no se encuentra la película.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestBody Movies updatedMovie) {
        Movies existingMovie = moviesService.getMovieById(id);
        if (existingMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }

        // Actualiza los detalles de la película
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setDescription(updatedMovie.getDescription());
        existingMovie.setGenre(updatedMovie.getGenre());
        existingMovie.setDuration(updatedMovie.getDuration());
        existingMovie.setDirector(updatedMovie.getDirector());
        existingMovie.setCasting(updatedMovie.getCasting());
        existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
        existingMovie.setLanguagesAvailable(updatedMovie.getLanguagesAvailable());
        existingMovie.setRating(updatedMovie.getRating());
        existingMovie.setAvailable(updatedMovie.isAvailable());
        existingMovie.setDistributor(updatedMovie.getDistributor());
        existingMovie.setDimensionsAvailable(updatedMovie.getDimensionsAvailable());
        existingMovie.setVerticalPosterBASE64(updatedMovie.getVerticalPosterBASE64());
        existingMovie.setHorizontalPosterBASE64(updatedMovie.getHorizontalPosterBASE64());

        // Guarda la película actualizada
        moviesService.saveMovie(existingMovie);
        return ResponseEntity.ok("Movie updated successfully.");
    }

    /**
     * Obtiene las salas disponibles para una película en una sucursal, en una fecha y hora específicas.
     * @param idMovie ID de la película.
     * @param idBranch ID de la sucursal.
     * @param date Fecha de la proyección.
     * @param screeningTime Hora de la proyección.
     * @return ResponseEntity con la lista de salas disponibles en formato JSON.
     */
    @GetMapping("/{idMovie}/branches/{idBranch}/dates/{date}/screening-times/{screeningTime}/get-available-rooms")
    public ResponseEntity<List<Rooms>> getAvailableRooms(
            @PathVariable Long idMovie,
            @PathVariable Long idBranch,
            @PathVariable String date,
            @PathVariable String screeningTime) {
        List<Rooms> availableRooms = screeningService.getAvailableRooms(idMovie, idBranch, date, screeningTime);
        return ResponseEntity.ok(availableRooms);
    }

    /**
     * Elimina una película por su ID.
     * @param id ID de la película a eliminar.
     * @return ResponseEntity con mensaje de éxito si la eliminación es exitosa o mensaje de error si no se encuentra la película.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        Movies movie = moviesService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }

        moviesService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully.");
    }

    /**
     * Obtiene las sucursales donde se proyecta una película específica.
     * @param id ID de la película.
     * @return ResponseEntity con la lista de sucursales o mensaje de error si no se encuentran sucursales.
     */
    @GetMapping("/{id}/branches")
    public ResponseEntity<?> getBranchesForMovie(@PathVariable Long id) {
        List<Branches> branches = moviesService.getBranchesForMovie(id);

        if (branches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No branches were found where the movie is being screened.");
        }

        List<BranchLocationDTO> branchLocations = branches.stream()
                .map(branch -> new BranchLocationDTO(branch.getIdBranch(), branch.getLocation())) // Mapea id y ubicación
                .collect(Collectors.toList());

        return ResponseEntity.ok(branchLocations);
    }

    public static class BranchLocationDTO {
        private Long branchId;
        private String location;

        public BranchLocationDTO(Long branchId, String location) {
            this.branchId = branchId;
            this.location = location;
        }

        // Getters y Setters
        public Long getBranchId() {
            return branchId;
        }

        public void setBranchId(Long branchId) {
            this.branchId = branchId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    /**
     * Obtiene todas las fechas de proyección para una película específica en una sucursal específica.
     * @param movieId ID de la película.
     * @param branchId ID de la sucursal.
     * @return ResponseEntity con la lista de fechas de proyección o mensaje de error si no hay fechas.
     */
    @GetMapping("/{movieId}/branches/{branchId}/screening-dates")
    public ResponseEntity<List<String>> getScreeningDatesForMovieAtBranch(
            @PathVariable Long movieId,
            @PathVariable Long branchId) {
        List<String> screeningDates = moviesService.getScreeningDatesForMovieAtBranch(movieId, branchId);

        if (screeningDates.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(screeningDates);
        }

        return ResponseEntity.ok(screeningDates);
    }

    /**
     * Obtiene todas las horas de proyección para una película en una sucursal y fecha específicas.
     * @param movieId ID de la película.
     * @param branchId ID de la sucursal.
     * @param date Fecha de la proyección.
     * @return ResponseEntity con la lista de horarios de proyección o mensaje de error si no hay horarios.
     */
    @GetMapping("/{movieId}/branches/{branchId}/dates/{date}/screening-times")
    public ResponseEntity<List<String>> getScreeningTimesForMovieBranchAndDate(
            @PathVariable Long movieId,
            @PathVariable Long branchId,
            @PathVariable String date) {
        List<String> screeningTimes = moviesService.getScreeningTimesForMovieBranchAndDate(movieId, branchId, date);

        if (screeningTimes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(screeningTimes);
        }

        return ResponseEntity.ok(screeningTimes);
    }

    /**
     * Obtiene el ID único de proyección para una película, fecha, hora y sucursal específicos desde un cuerpo JSON.
     * @param screeningRequest Objeto ScreeningRequestDTO con los datos para obtener el ID de proyección.
     * @return ResponseEntity con el ID de proyección en formato JSON.
     */
    @PostMapping("/get-screening-from-cascade-dropdown")
    public ResponseEntity<?> getScreeningId(@RequestBody ScreeningRequestDTO screeningRequest) {
        Long screeningId = moviesService.getScreeningId(
                screeningRequest.getMovieId(),
                screeningRequest.getDate(),
                screeningRequest.getTime(),
                screeningRequest.getBranchId(),
                screeningRequest.getRoomId()
        );

        ScreeningIdResponseDTO response = new ScreeningIdResponseDTO(screeningId);

        return ResponseEntity.ok(response);
    }

    public static class ScreeningRequestDTO {
        private Long movieId;
        private Long branchId;
        private String date;
        private String time;
        private Long roomId;

        public Long getRoomId() {
            return roomId;
        }

        public void setRoomId(Long roomId) {
            this.roomId = roomId;
        }

        // Getters y Setters
        public Long getMovieId() {
            return movieId;
        }

        public void setMovieId(Long movieId) {
            this.movieId = movieId;
        }

        public Long getBranchId() {
            return branchId;
        }

        public void setBranchId(Long branchId) {
            this.branchId = branchId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class ScreeningIdResponseDTO {
        private Long screeningId;

        public ScreeningIdResponseDTO(Long screeningId) {
            this.screeningId = screeningId;
        }

        public Long getScreeningId() {
            return screeningId;
        }

        public void setScreeningId(Long screeningId) {
            this.screeningId = screeningId;
        }
    }
}

