package com.kagimbz.cloud_nine.dto.GeoCoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodingResponse implements Serializable {

    @JsonProperty("country")
    private String country;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lon")
    private Object lon;

    @JsonProperty("state")
    private String state;

    @JsonProperty("lat")
    private Object lat;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("local_names")
//    private Map<String, String> localNames;
}