package ua.comparus.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.comparus.task.model.User;
import ua.comparus.task.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "User data aggregation from multiple databases")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users with optional filters", description = "Retrieves a list of users, optionally filtered by username, name, or surname.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request (invalid parameters)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<User>> getUsers(
            @Parameter(description = "Filter by username (optional)") @RequestParam(required = false) String username,
            @Parameter(description = "Filter by name (optional)") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by surname (optional)") @RequestParam(required = false) String surname) {
        return ResponseEntity.ok(userService.getAllUsers(username, name, surname));
    }
}
