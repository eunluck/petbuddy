package com.petbuddy.api.model.pet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petbuddy.api.model.commons.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE pet_image SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class PetImage extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    @JsonBackReference
    private Pet pet;


    @Builder
    public PetImage(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public void setPet(Pet pet) {
        if (Objects.nonNull(this.pet)) {
            this.pet.getPetImages().remove(this);
        }
        this.pet = pet;
    }

}
