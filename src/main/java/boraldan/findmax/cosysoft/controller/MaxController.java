package boraldan.findmax.cosysoft.controller;

import boraldan.findmax.cosysoft.controller.exception.MyDataProcessingException;
import boraldan.findmax.cosysoft.controller.exception.FileProcessingException;
import boraldan.findmax.cosysoft.controller.exception.InvalidParameterException;
import boraldan.findmax.cosysoft.service.MaxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RequestMapping("/api")
@Tag(name = "Max Finder Controller", description = "Контроллер поиска максимального числа")
@RestController
public class MaxController {

    private final MaxService maxService;

    @Autowired
    public MaxController(MaxService maxService) {
        this.maxService = maxService;
    }

    @PostMapping("/max")
    @Operation(summary = "Найти максимальное число в Excel-файле",
            description = "Принимает путь к файлу XLSX и число N, возвращает максимальное число")
    public DeferredResult<ResponseEntity<Long>> findMaxFile(
            @RequestParam (required = false) @Schema(description = "Число N") Long N,
            @RequestParam (required = false) @Schema(description = "Путь к файлу на сервере") String filePath) {

        DeferredResult<ResponseEntity<Long>> deferredResult = new DeferredResult<>();
        maxService.findMax(N, filePath)
                .thenApply(deferredResult::setResult)
                .exceptionally(ex -> {
                    deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage()));
                    return null;
                });

        return deferredResult;

    }

}
