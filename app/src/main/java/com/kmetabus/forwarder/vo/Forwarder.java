package com.kmetabus.forwarder.vo;

import lombok.Data;

@Data
public class Forwarder {
    private String seq;
    private String cont;
    private String juso;
    private String cnm;//업체명
    private String hp;//홈페이지 주소
    private String tel;//


    public void setCont(String scont) {
        this.cont = scont;
    }

    public void setNm(String snm) {
        this.cnm = snm;
    }

    public void setTel(String stel) {
        this.tel = stel;
    }

    public void setAddr(String saddr) {
        this.juso = saddr;
    }
    public void setHp(String shp) {
        this.hp = shp;
    }

}
