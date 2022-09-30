package com.vmo.chatapp2.relationship.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRelaForm {
    private Long id;

    private String note;
}
