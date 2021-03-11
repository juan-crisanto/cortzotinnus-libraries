package pe.cima.sample;

import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class TestController {


    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class TestResponseDto {
        private String message = "Hola mundo";
    }

    @PostMapping(value = "/api/test")
    public TestResponseDto testOtherPath(Principal principal) {
        return new TestResponseDto("Este debe pasar por authenticación: " + principal.getName());
    }

    @PostMapping(value = "/api/excluded")
    public TestResponseDto testExcludedPath() {
        return new TestResponseDto("Excluded test, sin authentication");
    }

    @PostMapping(value = "/internal-api/test") // para autenticacion de la web
    public TestResponseDto testBasicAuthPath() {
        return new TestResponseDto("Basic auth internal api ");
    }
}