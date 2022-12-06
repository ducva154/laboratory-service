package vn.edu.fpt.laboratory.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * vn.edu.fpt.laboratory.dto.event
 *
 * @author : Portgas.D.Ace
 * @created : 06/12/2022
 * @contact : 0339850697- congdung2510@gmail.com
 **/
@AllArgsConstructor
@Data
@Builder
public class CreateDocumentEvent implements Serializable {
    private static final long serialVersionUID = -602035947062014869L;

}
