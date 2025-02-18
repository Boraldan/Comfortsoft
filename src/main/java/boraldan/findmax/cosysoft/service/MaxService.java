package boraldan.findmax.cosysoft.service;

import boraldan.findmax.cosysoft.controller.exception.MyDataProcessingException;
import boraldan.findmax.cosysoft.controller.exception.FileProcessingException;
import boraldan.findmax.cosysoft.controller.exception.InvalidParameterException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;


@Service
public class MaxService {

    /**
     *
     * @param N количество чисел участвующих в выборке
     * @param filePath путь к файлу
     * @return максимальное число из выборки
     */

    public CompletableFuture<ResponseEntity<Long>> findMax(Long N, String filePath) {
        return CompletableFuture.supplyAsync(() -> {

            if (filePath == null || filePath.isBlank()) {
                throw new FileProcessingException("Путь к файлу не указан.");
            }

            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileProcessingException("Файл не найден: " + filePath);
            }

            if (N == null || N <= 0) {
                throw new InvalidParameterException("Число N должно быть больше 0.");
            }

            List<Long> longMass = uploadExcel(N, file);
            if ( longMass.size() < N) {
                throw new MyDataProcessingException("Недостаточно чисел в файле для поиска максимального.");
            }

            OptionalLong max = findMaxArray(longMass);
            return max.isPresent() ? ResponseEntity.ok(max.getAsLong())
                    : ResponseEntity.noContent().build();
        });
    }

    /**
     *
     * @param N количество чисел участвующих в выборке
     * @param file файл с набором чисел
     * @return List с числами типа long
     */
    private List<Long> uploadExcel(Long N, File file) {
        List<Long> numbers = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    numbers.add((long) cell.getNumericCellValue());
                }
                if (numbers.size() >= N) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обработке файла", e);
        }

        return numbers;
    }

    /**
     * Находит максимальное значение
     *
     * @param numbers List с числами типа long, для которых нужно найти максимальное значение.
     * @return OptionalLong, содержащий максимальное значение из List, или пустой OptionalLong, если List пуст.
     */
    private OptionalLong findMaxArray(List<Long> numbers) {
        if (numbers.isEmpty()) return OptionalLong.empty();

        return OptionalLong.of(Collections.max(numbers));
    }
}
