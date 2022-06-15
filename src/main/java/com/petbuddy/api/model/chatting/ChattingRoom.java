package com.petbuddy.api.model.chatting;


import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petbuddy.api.model.commons.BaseEntity;
import com.petbuddy.api.model.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE chatting_room SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(callSuper = true)
public class ChattingRoom extends BaseEntity {


    @OneToMany
    @JsonManagedReference
    private List<Pet> chatters = Lists.newArrayList();

    public void addMember(Pet pet){
        chatters.add(pet);
    }

    public static ChattingRoom createChattingRoom(Long... pets){
        ChattingRoom chattingRoom = new ChattingRoom();

        for (Long pet : pets) {
            Pet newPet = new Pet();
            newPet.setId(pet);
            chattingRoom.addMember(newPet);
        }

        return chattingRoom;
    }



}
