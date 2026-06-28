package net.cloudy.sytes.hello_liberty.sftp.processor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonResult {
    public String nachname;
    public String vorname;
    public String idnr;
    public String statusCode; // z.B. OK / NOT_FOUND / INVALID
}