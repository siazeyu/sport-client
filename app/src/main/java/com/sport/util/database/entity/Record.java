package com.sport.util.database.entity;

public class Record {

    private Integer id;

    private Integer step;

    private Double kll;

    private Double km;

    private Long start;

    private Long end;



    public Record(){

    };

    public Record(Integer step, Double kll, Double km, Long start, Long end) {
        this.step = step;
        this.kll = kll;
        this.km = km;
        this.start = start;
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Double getKll() {
        return kll;
    }

    public void setKll(Double kll) {
        this.kll = kll;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
