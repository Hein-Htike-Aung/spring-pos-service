package com.example.posservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance
@DiscriminatorValue("CUSTOMER")
public class Customer extends Member {

    @OneToOne(fetch = FetchType.LAZY)
    private _User user;
}
