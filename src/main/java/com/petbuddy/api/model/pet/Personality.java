/*
package com.petbuddy.api.model.pet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petbuddy.api.model.listener.RegisterPetListener;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(RegisterPetListener.class)
public class Personality  {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pet_id")
  @JsonBackReference
  private Pet pet;

  @Enumerated(value = EnumType.STRING)
  private PersonalityType personalityType;


  @CreatedDate
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Builder
  public Personality(Pet pet, PersonalityType personalityType){
   this.pet = pet;
   this.personalityType =personalityType;
  }

}*/
