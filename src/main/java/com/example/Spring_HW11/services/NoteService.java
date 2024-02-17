package com.example.Spring_HW11.services;

import com.example.Spring_HW11.models.Note;
import com.example.Spring_HW11.repository.NoteRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class NoteService {
    /**
     * Интерфейс взаимодействия с Базой Данных
     */
    private final NoteRepository noteRepository;

    /**
     * Получение всех заметок через БД
     *
     * @return список заметок
     */
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    /**
     * Получение заметки по идентификатору в БД
     *
     * @param id уникальный идентификатор
     * @return заметка
     */
    public Note getById(long id) {
        Note note;
        try {
            note = noteRepository.findById(id).orElseThrow(null);
            return note;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Создание заметки в БД
     *
     * @param note заметка из запроса
     * @return новая заметка
     */
    public Note create(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Обнорвление заметки в БД
     *
     * @param id заметка из запроса
     * @return обновленная заметка
     */
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public Optional<Note> editNote(Long id, Note note) {
        Optional<Note> existingNote = noteRepository.findById(id);
        if (existingNote.isPresent()) {
            note.setId(id);
            return Optional.of(noteRepository.save(note));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Удаление заметки из БД
     *
     * @param id уникальный идентификатор заметки
     */
    public void delete(long id) {
        noteRepository.deleteById(id);
    }
}