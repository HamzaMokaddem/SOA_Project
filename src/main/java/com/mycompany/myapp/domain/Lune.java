package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Lune.
 */
@Entity
@Table(name = "lune")
public class Lune implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    @Column(name = "name", length = 15, nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @NotNull
    @Column(name = "taille", nullable = false)
    private Float taille;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "lunes", allowSetters = true)
    private Planet planet;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Lune name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Lune latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Lune longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getTaille() {
        return taille;
    }

    public Lune taille(Float taille) {
        this.taille = taille;
        return this;
    }

    public void setTaille(Float taille) {
        this.taille = taille;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Lune planet(Planet planet) {
        this.planet = planet;
        return this;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lune)) {
            return false;
        }
        return id != null && id.equals(((Lune) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lune{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", taille=" + getTaille() +
            "}";
    }
}
