package com.example.Spring_HW11.controllers;

import com.example.Spring_HW11.models.Note;
import com.example.Spring_HW11.services.NoteService;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Data
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    /**
     * Сервис управления заметками
     */
    private final NoteService noteService;
    private final Counter requestCounter = Metrics.counter("request_count");

    /**
     * Получение всех заметок
     *
     * @return список заметок
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        requestCounter.increment();
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }

    /**
     * Создане новой заметки
     *
     * @param note новая заметка из body
     * @return созданная заметка
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.create(note), HttpStatus.CREATED);
    }

    /**
     * Получить заметку по идентификатору
     *
     * @param id уникальный идентификатор заметки
     * @return заметка
     */
    @GetMapping("{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") long id) {
        return new ResponseEntity<>(noteService.getById(id), HttpStatus.OK);
    }

    /**
     * Обновление заметки
     * @param id  уникальный идентификатор нужной заметки
     * @param note заметка из body
     * @return обновленная заметка
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> editNote(@PathVariable Long id, @RequestBody Note note) {
        Optional<Note> updatedNote = noteService.editNote(id, note);
        return updatedNote.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Удаление заметки
     *
     * @param id уникальный идентификатор нужной заметки
     * @return возвращаем статус выполнения
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") long id) {
        noteService.delete(id);
        return ResponseEntity.ok().build();
    }
}
