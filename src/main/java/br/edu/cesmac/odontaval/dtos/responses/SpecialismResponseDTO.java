package br.edu.cesmac.odontaval.dtos.responses;

public class SpecialismResponseDTO {
    public Long id;
    public String name;
    public String description;

    public SpecialismResponseDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
