package com.wlj.firework.core.modular.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetKeyRequest {

    @NotBlank
    private String key;

    @NotBlank
    private String value;

}
