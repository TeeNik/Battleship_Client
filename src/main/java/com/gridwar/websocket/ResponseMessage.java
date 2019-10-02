package com.gridwar.websocket;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage extends Message {

    @Getter @Setter
    Integer result;

    public ResponseMessage(String header, int result){
        super(header);
        this.result = result;
    }

}
