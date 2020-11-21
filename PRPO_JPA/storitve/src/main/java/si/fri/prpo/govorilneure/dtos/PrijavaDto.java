package si.fri.prpo.govorilneure.dtos;

import si.fri.prpo.govorilneure.entitete.Termin;

public class PrijavaDto {

    //prijava in student


    public PrijavaDto(Integer id, long time, Boolean potrjena, String email, Integer studentId, Integer terminId) {
        this.id = id;
        this.time = time;
        this.potrjena = potrjena;
        this.email = email;
        this.studentId = studentId;
        this.terminId = terminId;
    }

    public PrijavaDto(long time, String email, Integer studentId, Integer terminId) {
        this.time = time;
        this.potrjena = potrjena;
        this.email = email;
        this.studentId = studentId;
        this.terminId = terminId;
    }

    public PrijavaDto(Integer id) {
        this.id = id;
    }

    private Integer id;
    private long time;
    private Boolean potrjena = false;
    private String email;
    private Integer studentId;
    private Integer terminId;

    public Integer getTerminId() {
        return terminId;
    }

    public void setTerminId(Integer terminId) {
        this.terminId = terminId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) { this.time = time; }

    public Boolean getPotrjena() {
        return potrjena;
    }

    public void setPotrjena(Boolean potrjena) {
        this.potrjena = potrjena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

}
