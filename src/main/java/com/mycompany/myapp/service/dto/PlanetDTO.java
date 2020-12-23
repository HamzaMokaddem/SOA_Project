package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Planet} entity.
 */
public class PlanetDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    private String name;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    @NotNull
    private String etat;


    private Long etoileId;

    private String etoileName;
    
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Long getEtoileId() {
        return etoileId;
    }

    public void setEtoileId(Long etoileId) {
        this.etoileId = etoileId;
    }

    public String getEtoileName() {
        return etoileName;
    }

    public void setEtoileName(String etoileName) {
        this.etoileName = etoileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanetDTO)) {
            return false;
        }

        return id != null && id.equals(((PlanetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", etat='" + getEtat() + "'" +
            ", etoileId=" + getEtoileId() +
            ", etoileName='" + getEtoileName() + "'" +
            "}";
    }
}
