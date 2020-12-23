package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Lune} entity.
 */
public class LuneDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    private String name;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    @NotNull
    private Float taille;


    private Long planetId;

    private String planetName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getTaille() {
        return taille;
    }

    public void setTaille(Float taille) {
        this.taille = taille;
    }

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LuneDTO)) {
            return false;
        }

        return id != null && id.equals(((LuneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LuneDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", taille=" + getTaille() +
            ", planetId=" + getPlanetId() +
            ", planetName='" + getPlanetName() + "'" +
            "}";
    }
}
