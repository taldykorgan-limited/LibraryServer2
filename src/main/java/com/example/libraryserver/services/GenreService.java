package com.example.libraryserver.services;

import com.example.libraryserver.entities.GenreEntity;
import com.example.libraryserver.repositories.GenreRepository;
import com.example.libraryserver.requests.genres.CreateGenreRequest;
import com.example.libraryserver.requests.genres.UpdateGenreRequest;
import com.example.libraryserver.responses.genres.GetGenreResponse;
import com.example.libraryserver.responses.genres.GetGenresResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;


    public String createGenre(CreateGenreRequest createGenreRequest) {
        GenreEntity genreEntity = GenreEntity.builder()
                .name(createGenreRequest.getName())
                .info(createGenreRequest.getInfo())
                .build();
        genreRepository.save(genreEntity);
        return "Genre created: " + createGenreRequest.getName();
    }

    public GetGenreResponse getGenreByIdBrief(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Genre with id " + id + " not found"));

        GetGenreResponse getGenreResponse = GetGenreResponse.builder()
                .id(genreEntity.getId())
                .name(genreEntity.getName())
                .build();
        return getGenreResponse;
    }

    public GetGenreResponse getGenreByIdFull(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Genre with id " + id + " not found"));

        GetGenreResponse getGenreResponse = GetGenreResponse.builder()
                .id(genreEntity.getId())
                .name(genreEntity.getName())
                .info(genreEntity.getInfo())
                .books(genreEntity.getBooks())
                .build();
        return getGenreResponse;
    }

    public GetGenresResponse getAllGenresBrief() {
        List<GenreEntity> genreEntityList = genreRepository.findAll();
        List<GetGenreResponse> getGenreResponseList = genreEntityList.stream()
                .map(genreEntity -> GetGenreResponse.builder()
                        .id(genreEntity.getId())
                        .name(genreEntity.getName())
                        .build())
                .toList();
        return new GetGenresResponse(getGenreResponseList);
    }

    public GetGenresResponse getAllGenresFull() {
        List<GenreEntity> genreEntityList = genreRepository.findAll();
        List<GetGenreResponse> getGenreResponseList = genreEntityList.stream()
                .map(genreEntity -> GetGenreResponse.builder()
                        .id(genreEntity.getId())
                        .name(genreEntity.getName())
                        .info(genreEntity.getInfo())
                        .books(genreEntity.getBooks())
                        .build())
                .toList();
        return new GetGenresResponse(getGenreResponseList);
    }

    public String updateGenre(UpdateGenreRequest updateGenreRequest) {
        GenreEntity genreEntity = genreRepository.findById(updateGenreRequest.getId())
                .orElseThrow(() -> new NoSuchElementException("Genre with id " + updateGenreRequest.getId() + " not found"));

        genreEntity.setName(updateGenreRequest.getName());
        genreEntity.setInfo(updateGenreRequest.getInfo());
        genreEntity.setBooks(updateGenreRequest.getBooks());
        genreRepository.save(genreEntity);

        return "Genre with id " + updateGenreRequest.getId() + " has been updated.";
    }

    public String deleteGenre(Long id) {
        genreRepository.deleteById(id);
        return "Genre with id " + id + " has been deleted.";
    }


}
