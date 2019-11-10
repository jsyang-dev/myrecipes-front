package link.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Material implements Serializable {
    private Integer id;

    private String name;

    private String unitName;

    @Builder
    public Material(Integer id, String name, String unitName) {
        this.id = id;
        this.name = name;
        this.unitName = unitName;
    }
}
