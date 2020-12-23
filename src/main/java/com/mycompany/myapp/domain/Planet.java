package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Planet.
 */
@Entity
@Table(name = "planet")
public class Planet implements Serializable {

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
    @Column(name = "etat", nullable = false)
    private String etat;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "planets", allowSetters = true)
    private Etoile etoile;

    @OneToMany(mappedBy = "planet")
    private Set<Lune> lunes = new HashSet<>();

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

    public Planet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Planet latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Planet longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getEtat() {
        return etat;
    }

    public Planet etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Etoile getEtoile() {
        return etoile;
    }

    public Planet etoile(Etoile etoile) {
        this.etoile = etoile;
        return this;
    }

    public void setEtoile(Etoile etoile) {
        this.etoile = etoile;
    }

    public Set<Lune> getLunes() {
        return lunes;
    }

    public Planet lunes(Set<Lune> lunes) {
        this.lunes = lunes;
        return this;
    }

    public Planet addLune(Lune lune) {
        this.lunes.add(lune);
        lune.setPlanet(this);
        return this;
    }

    public Planet removeLune(Lune lune) {
        this.lunes.remove(lune);
        lune.setPlanet(null);
        return this;
    }

    public void setLunes(Set<Lune> lunes) {
        this.lunes = lunes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Planet)) {
            return false;
        }
        return id != null && id.equals(((Planet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Planet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
