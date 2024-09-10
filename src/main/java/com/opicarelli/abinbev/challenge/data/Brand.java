package com.opicarelli.abinbev.challenge.data;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Table(name = "brands", uniqueConstraints = { @UniqueConstraint(columnNames = "name", name = "brands_name_uk") })
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    /**
     * @deprecated JPA only
     */
    @Deprecated
    Brand() { }

    /**
     * Use this constructor to create a brand.
     *
     * @param name must not be blank
     *
     * @throws IllegalArgumentException if any argument is not satisfied
     */
    public Brand(@NotBlank String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

