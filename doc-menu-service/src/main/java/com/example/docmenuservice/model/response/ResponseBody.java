package com.example.docmenuservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseBody<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private LocalDateTime time;
    private Integer status;
}
